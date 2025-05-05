package br.edu.fateczl.AulaSpringData.controller;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.fateczl.AulaSpringData.model.Especialidade;
import br.edu.fateczl.AulaSpringData.repository.EspecialidadeRepository;

@Controller
@RequestMapping("/especialidades")
public class EspecialidadeController {

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    // List all specialties
    @GetMapping
    public ModelAndView listarEspecialidades(ModelMap model) {
        List<Especialidade> especialidades = especialidadeRepository.findAll();
        model.addAttribute("especialidades", especialidades);
        model.addAttribute("pageTitle", "Lista de Especialidades");
        return new ModelAndView("especialidadeList", model); // View name: especialidadeList.jsp
    }

    // Show form for adding a new specialty
    @GetMapping("/novo")
    public ModelAndView novaEspecialidade(ModelMap model) {
        model.addAttribute("especialidade", new Especialidade()); // Bind an empty object
        model.addAttribute("pageTitle", "Nova Especialidade");
        model.addAttribute("formAction", "/especialidades/salvar");
        return new ModelAndView("especialidadeForm", model); // View name: especialidadeForm.jsp
    }

    // Show form for editing an existing specialty
    @GetMapping("/editar/{id}")
    public ModelAndView editarEspecialidade(@PathVariable("id") Long id, ModelMap model) {
        Optional<Especialidade> optionalEspecialidade = especialidadeRepository.findById(id);
        if (optionalEspecialidade.isPresent()) {
            model.addAttribute("especialidade", optionalEspecialidade.get());
            model.addAttribute("pageTitle", "Editar Especialidade");
            model.addAttribute("formAction", "/especialidades/salvar");
            return new ModelAndView("especialidadeForm", model);
        } else {
            return new ModelAndView("redirect:/especialidades");
        }
    }

    // Save (Create or Update) a specialty
    @PostMapping("/salvar")
    public ModelAndView salvarEspecialidade(@ModelAttribute Especialidade especialidade) {
        especialidadeRepository.save(especialidade);
        return new ModelAndView("redirect:/especialidades");
    }

    // Delete a specialty
    @GetMapping("/excluir/{id}")
    public ModelAndView excluirEspecialidade(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            // Check if any Medico uses this specialty before deleting? (Optional advanced check)
            especialidadeRepository.deleteById(id);
        } catch (Exception e) {
            // Handle potential constraint violation errors if a Medico is linked
            System.err.println("Erro ao excluir especialidade: " + e.getMessage());
            // Optionally add a message to the user
            redirectAttributes.addFlashAttribute("errorMessage", "Não foi possível excluir a especialidade. Verifique se ela está sendo usada por algum médico.");
        }
        return new ModelAndView("redirect:/especialidades");
    }
}