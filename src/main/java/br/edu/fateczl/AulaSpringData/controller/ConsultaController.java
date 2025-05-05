package br.edu.fateczl.AulaSpringData.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.fateczl.AulaSpringData.model.Consulta;
import br.edu.fateczl.AulaSpringData.model.Medico;
import br.edu.fateczl.AulaSpringData.model.Paciente;
import br.edu.fateczl.AulaSpringData.repository.ConsultaRepository;
import br.edu.fateczl.AulaSpringData.repository.MedicoRepository;
import br.edu.fateczl.AulaSpringData.repository.PacienteRepository;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    // List all consultations or search by date
    @GetMapping
    public ModelAndView listarConsultas(ModelMap model,
                                        @RequestParam(value = "searchDate", required = false) String searchDateStr) {

        List<Consulta> consultas;
        long countConsultasDia = 0; // Initialize count

        if (searchDateStr != null && !searchDateStr.isEmpty()) {
            try {
                LocalDate searchDate = LocalDate.parse(searchDateStr);
                LocalDateTime startDateTime = searchDate.atStartOfDay(); // Start of the day
                LocalDateTime endDateTime = searchDate.plusDays(1).atStartOfDay(); // Start of the next day

                // Requirement d: Find consultations for the day
                consultas = consultaRepository.findConsultasPorDia(startDateTime, endDateTime);
                // Requirement e: Count consultations for the day
                countConsultasDia = consultaRepository.countConsultasPorDiaNativa(startDateTime, endDateTime);

                model.addAttribute("searchDate", searchDateStr);
                model.addAttribute("countConsultasDia", countConsultasDia);

            } catch (DateTimeParseException e) {
                // Handle invalid date format
                System.err.println("Invalid date format received: " + searchDateStr);
                consultas = consultaRepository.findAll(); // Fallback to all
                model.addAttribute("dateError", "Formato de data inválido. Use AAAA-MM-DD.");
            }
        } else {
            consultas = consultaRepository.findAll();
        }

        model.addAttribute("consultas", consultas);
        model.addAttribute("pageTitle", "Lista de Consultas");
        return new ModelAndView("consultaList", model); // View name: consultaList.jsp
    }

    // Show form for adding a new consultation
    @GetMapping("/novo")
    public ModelAndView novaConsulta(ModelMap model) {
        List<Paciente> pacientes = pacienteRepository.findAll();
        List<Medico> medicos = medicoRepository.findAll();

        model.addAttribute("consulta", new Consulta());
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("medicos", medicos);
        model.addAttribute("pageTitle", "Nova Consulta");
        model.addAttribute("formAction", "/consultas/salvar");
        return new ModelAndView("consultaForm", model); // View name: consultaForm.jsp
    }

    // Show form for editing an existing consultation
    @GetMapping("/editar/{id}")
    public ModelAndView editarConsulta(@PathVariable("id") Long id, ModelMap model) {
        Optional<Consulta> optionalConsulta = consultaRepository.findById(id);
        if (optionalConsulta.isPresent()) {
            List<Paciente> pacientes = pacienteRepository.findAll();
            List<Medico> medicos = medicoRepository.findAll();

            model.addAttribute("consulta", optionalConsulta.get());
            model.addAttribute("pacientes", pacientes);
            model.addAttribute("medicos", medicos);
            model.addAttribute("pageTitle", "Editar Consulta");
            model.addAttribute("formAction", "/consultas/salvar");
            return new ModelAndView("consultaForm", model);
        } else {
            return new ModelAndView("redirect:/consultas");
        }
    }

    // Save (Create or Update) a consultation
    @PostMapping("/salvar")
    public ModelAndView salvarConsulta(@ModelAttribute Consulta consulta, RedirectAttributes redirectAttributes) {
        // Fetch full Paciente and Medico objects to ensure they exist and attach managed instances
        Optional<Paciente> optPaciente = Optional.empty();
        if (consulta.getPaciente() != null && consulta.getPaciente().getId() != null) {
            optPaciente = pacienteRepository.findById(consulta.getPaciente().getId());
        }

        Optional<Medico> optMedico = Optional.empty();
        if (consulta.getMedico() != null && consulta.getMedico().getCodigo() != null) {
             optMedico = medicoRepository.findById(consulta.getMedico().getCodigo());
        }

        if (optPaciente.isPresent() && optMedico.isPresent()) {
            consulta.setPaciente(optPaciente.get());
            consulta.setMedico(optMedico.get());
            consultaRepository.save(consulta);
            return new ModelAndView("redirect:/consultas");
        } else {
             // Handle case where Patient or Doctor ID is invalid/not found
             redirectAttributes.addFlashAttribute("errorMessage", "Paciente ou Médico inválido selecionado.");
             // Redirect back to the form, potentially preserving some data if needed (more complex)
             // For simplicity, redirecting to list here. Consider redirecting back to /novo or /editar/{id}
             return new ModelAndView("redirect:/consultas");
        }
    }

    // Delete a consultation
    @GetMapping("/excluir/{id}")
    public ModelAndView excluirConsulta(@PathVariable("id") Long id) {
        try {
            consultaRepository.deleteById(id);
        } catch (Exception e) {
             System.err.println("Erro ao excluir consulta: " + e.getMessage());
             // Optionally add redirect attribute for error message
        }
        return new ModelAndView("redirect:/consultas");
    }
}