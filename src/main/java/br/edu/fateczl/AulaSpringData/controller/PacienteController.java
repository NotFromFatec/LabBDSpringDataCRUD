package br.edu.fateczl.AulaSpringData.controller;

import java.util.ArrayList;
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

import br.edu.fateczl.AulaSpringData.model.Paciente;
import br.edu.fateczl.AulaSpringData.repository.PacienteRepository;

@Controller
@RequestMapping("/pacientes") // Base path for all patient-related actions
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    // List all patients or search results
    @GetMapping
    public ModelAndView listarPacientes(ModelMap model,
                                        @RequestParam(value = "searchNome", required = false) String searchNome,
                                        @RequestParam(value = "searchTelefone", required = false) String searchTelefone) {
        List<Paciente> pacientes = new ArrayList<>();

        if (searchNome != null && !searchNome.isEmpty()) {
            // Requirement a: Find by name, ordered ascending
            pacientes = pacienteRepository.findByNomeContainingIgnoreCaseOrderByNomeAsc(searchNome);
            model.addAttribute("searchTerm", searchNome);
            model.addAttribute("searchType", "nome");
        } else if (searchTelefone != null && !searchTelefone.isEmpty()) {
            // Requirement b: Find first by phone number
            Optional<Paciente> optPaciente = pacienteRepository.findFirstByTelefone(searchTelefone);
            optPaciente.ifPresent(pacientes::add); // Add if found
             model.addAttribute("searchTerm", searchTelefone);
             model.addAttribute("searchType", "telefone");
        } else {
            // Default: List all
            pacientes = pacienteRepository.findAll();
        }

        model.addAttribute("pacientes", pacientes);
        model.addAttribute("pageTitle", "Lista de Pacientes");
        return new ModelAndView("pacienteList", model); // View name: pacienteList.jsp
    }

    // Show form for adding a new patient
    @GetMapping("/novo")
    public ModelAndView novoPaciente(ModelMap model) {
        model.addAttribute("paciente", new Paciente()); // Bind an empty patient object
        model.addAttribute("pageTitle", "Novo Paciente");
        model.addAttribute("formAction", "/pacientes/salvar"); // Action URL for the form
        return new ModelAndView("pacienteForm", model); // View name: pacienteForm.jsp
    }

    // Show form for editing an existing patient
    @GetMapping("/editar/{id}")
    public ModelAndView editarPaciente(@PathVariable("id") Long id, ModelMap model) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);
        if (optionalPaciente.isPresent()) {
            model.addAttribute("paciente", optionalPaciente.get());
            model.addAttribute("pageTitle", "Editar Paciente");
            model.addAttribute("formAction", "/pacientes/salvar"); // Same action URL
            return new ModelAndView("pacienteForm", model); // View name: pacienteForm.jsp
        } else {
            // Handle patient not found - redirect back to list with an error maybe
            return new ModelAndView("redirect:/pacientes");
        }
    }

    // Save (Create or Update) a patient
    @PostMapping("/salvar")
    public ModelAndView salvarPaciente(@ModelAttribute Paciente paciente) {
        // If paciente has an ID, it's an update, otherwise it's a create
        pacienteRepository.save(paciente);
        return new ModelAndView("redirect:/pacientes"); // Redirect to the list view
    }

    // Delete a patient
    @GetMapping("/excluir/{id}")
    public ModelAndView excluirPaciente(@PathVariable("id") Long id) {
        try {
            pacienteRepository.deleteById(id);
        } catch (Exception e) {
            // Optional: Add error handling/logging if deletion fails (e.g., due to constraints)
            System.err.println("Erro ao excluir paciente: " + e.getMessage());
        }
        return new ModelAndView("redirect:/pacientes"); // Redirect to the list view
    }
}