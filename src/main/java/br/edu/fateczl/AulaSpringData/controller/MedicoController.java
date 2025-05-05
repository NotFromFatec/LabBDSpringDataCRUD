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

import br.edu.fateczl.AulaSpringData.model.Especialidade;
import br.edu.fateczl.AulaSpringData.model.Medico;
import br.edu.fateczl.AulaSpringData.repository.EspecialidadeRepository;
import br.edu.fateczl.AulaSpringData.repository.MedicoRepository;

@Controller
@RequestMapping("/medicos") // Base path for all doctor-related actions
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository; // Needed for the dropdown

    // List all doctors or search results
    @GetMapping
    public ModelAndView listarMedicos(ModelMap model,
                                     @RequestParam(value = "searchEspecialidade", required = false) String searchEspecialidade) {
        List<Medico> medicos = new ArrayList<>();
        List<Especialidade> especialidades = especialidadeRepository.findAll(); // For search dropdown

        if (searchEspecialidade != null && !searchEspecialidade.isEmpty()) {
            // Requirement c: Find by specialty name (type)
            medicos = medicoRepository.findByEspecialidadeNome(searchEspecialidade);
            model.addAttribute("searchTerm", searchEspecialidade);
            model.addAttribute("searchType", "especialidade");
        } else {
            // Default: List all
            medicos = medicoRepository.findAll();
        }

        model.addAttribute("medicos", medicos);
        model.addAttribute("especialidades", especialidades); // Pass specialties for search filter
        model.addAttribute("pageTitle", "Lista de Médicos");
        return new ModelAndView("medicoList", model); // View name: medicoList.jsp
    }

    // Show form for adding a new doctor
    @GetMapping("/novo")
    public ModelAndView novoMedico(ModelMap model) {
        List<Especialidade> especialidades = especialidadeRepository.findAll();
        model.addAttribute("medico", new Medico()); // Bind an empty doctor object
        model.addAttribute("especialidades", especialidades); // For the dropdown
        model.addAttribute("pageTitle", "Novo Médico");
        model.addAttribute("formAction", "/medicos/salvar"); // Action URL for the form
        return new ModelAndView("medicoForm", model); // View name: medicoForm.jsp
    }

    // Show form for editing an existing doctor
    @GetMapping("/editar/{codigo}") // Use 'codigo' as defined in Medico model
    public ModelAndView editarMedico(@PathVariable("codigo") Long codigo, ModelMap model) {
        Optional<Medico> optionalMedico = medicoRepository.findById(codigo);
        if (optionalMedico.isPresent()) {
            List<Especialidade> especialidades = especialidadeRepository.findAll();
            model.addAttribute("medico", optionalMedico.get());
            model.addAttribute("especialidades", especialidades); // For the dropdown
            model.addAttribute("pageTitle", "Editar Médico");
            model.addAttribute("formAction", "/medicos/salvar"); // Same action URL
            return new ModelAndView("medicoForm", model); // View name: medicoForm.jsp
        } else {
            // Handle doctor not found
            return new ModelAndView("redirect:/medicos");
        }
    }

    // Save (Create or Update) a doctor
    @PostMapping("/salvar")
    public ModelAndView salvarMedico(@ModelAttribute Medico medico) {
        // Make sure the nested Especialidade object is properly handled if only ID is submitted
        // Typically, Spring binds medico.especialidade.id. Fetch the full Especialidade if needed,
        // or ensure the repository handles saving based on ID reference.
        // If binding fails for nested objects, manual fetching might be required:
        // if (medico.getEspecialidade() != null && medico.getEspecialidade().getId() != null) {
        //    Optional<Especialidade> esp = especialidadeRepository.findById(medico.getEspecialidade().getId());
        //    esp.ifPresent(medico::setEspecialidade);
        // }
        medicoRepository.save(medico);
        return new ModelAndView("redirect:/medicos"); // Redirect to the list view
    }

    // Delete a doctor
    @GetMapping("/excluir/{codigo}") // Use 'codigo'
    public ModelAndView excluirMedico(@PathVariable("codigo") Long codigo) {
        try {
            medicoRepository.deleteById(codigo);
        } catch (Exception e) {
            // Optional: Add error handling/logging
             System.err.println("Erro ao excluir médico: " + e.getMessage());
        }
        return new ModelAndView("redirect:/medicos"); // Redirect to the list view
    }
}