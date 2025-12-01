package com._dawb.AUT03_02_Diccionario.controllers;

import com._dawb.AUT03_02_Diccionario.interfaces.IExampleService;
import com._dawb.AUT03_02_Diccionario.interfaces.ITermService;
import com._dawb.AUT03_02_Diccionario.models.Example;
import com._dawb.AUT03_02_Diccionario.models.Term;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/examples")
public class ExamplesController {
    @Autowired
    private ITermService TS;
    @Autowired
    private IExampleService ES;

    @GetMapping("/create")
    @PreAuthorize("hasAnyRole('PREMIUM','ADMIN')")
    public String create(Model model, @RequestParam("term") int termId) {
        Term term = TS.getById(termId);
        if (term == null) return "redirect:/terms";

        Example example = new Example();
        example.setTerm(term);

        model.addAttribute("term", term);
        model.addAttribute("example", example);
        return "examples/create";
    }

    // Eliminar
    @PostMapping("/delete/{id}")
    public String deleteExample(@PathVariable int id, Principal principal) {
        Example example = ES.getById(id);

        // Usuario autenticado
        String username = principal.getName();

        // Verificamos si el usuario tiene rol ADMIN
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        // Solo permite borrar si es ADMIN o el autor del ejemplo
        if (isAdmin || example.getAuthor().equals(username)) {
            ES.delete(id);
            return "redirect:/terms/" + example.getTerm().getId();
        } else {
            return "redirect:/terms/" + example.getTerm().getId() + "?error=not_authorized";
        }
    }

    // Crear Ejemplo
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('PREMIUM','ADMIN')")
    public String createExample(@Valid @ModelAttribute("example") Example example,
                                BindingResult result,
                                Model model) {

        Term term = TS.getById(example.getTerm().getId());
        example.setTerm(term);

        if (result.hasErrors()) {
            model.addAttribute("term", term);
            return "examples/create";
        }

        ES.add(example);
        return "redirect:/terms/" + term.getId();
    }

    // Mostrar formulario de edición
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditForm(@PathVariable int id, Model model) {
        Example example = ES.getById(id);

        model.addAttribute("example", example);
        model.addAttribute("term", example.getTerm()); // para mostrar el término asociado
        return "examples/edit";
    }

    // Procesar actualización
// Procesar actualización (solo ADMIN)
    @PostMapping("/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String edit(@Valid @ModelAttribute("example") Example updatedExample,
                       BindingResult bindingResult,
                       @RequestParam("term.id") int termId,
                       Model model) {
        Term term = TS.getById(termId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("term", term);
            return "examples/edit";
        }

        // Recuperamos el original para no perder el author
        Example existingExample = ES.getById(updatedExample.getId());
        if (existingExample == null) {
            throw new RuntimeException("Ejemplo no encontrado con ID: " + updatedExample.getId());
        }

        // Actualizamos solo los campos editables
        existingExample.setExEsp(updatedExample.getExEsp());
        existingExample.setExEng(updatedExample.getExEng());
        existingExample.setTerm(term);

        ES.update(existingExample);

        return "redirect:/terms/" + term.getId();
    }
}