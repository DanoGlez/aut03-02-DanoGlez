package com._dawb.AUT03_02_Diccionario.controllers;

import com._dawb.AUT03_02_Diccionario.interfaces.IExampleService;
import com._dawb.AUT03_02_Diccionario.interfaces.ITermService;
import com._dawb.AUT03_02_Diccionario.models.Term;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class TermsController {
    @Autowired
    private ITermService TS;
    @Autowired
    private IExampleService ES;


    // Mostrar los primeros 30 Términos
    @GetMapping("/terms")
    public String index(Model model) {
        model.addAttribute("terms", TS.findFirst30ByOrderByIdAsc());
        return "terms/index";
    }

    // Mostrar los primeros 30 Términos
    @GetMapping("/terms/{id}")
    @PreAuthorize("hasAnyRole('PREMIUM','ADMIN')")
    public String index(@PathVariable int id, Model model) {
        model.addAttribute("term", TS.getById(id));
        model.addAttribute("examples", ES.findByTerm(id));
        return "terms/details";
    }

    // Mostrar formulario de edición
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/terms/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Term term = TS.getById(id);
        model.addAttribute("term", term);
        return "terms/edit";
    }

    // Procesar actualización (solo ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/terms/edit")
    public String edit(@Valid @ModelAttribute("term") Term updatedTerm,
                       BindingResult bindingResult,
                       Model model) {
        if (bindingResult.hasErrors()) {
            return "terms/edit";
        }

        // Recuperamos el original
        Term existingTerm = TS.getById(updatedTerm.getId());
        if (existingTerm == null) {
            throw new RuntimeException("Término no encontrado con ID: " + updatedTerm.getId());
        }

        // Actualizamos solo los campos editables
        existingTerm.setEsp(updatedTerm.getEsp());
        existingTerm.setEng(updatedTerm.getEng());

        TS.update(existingTerm);

        return "redirect:/terms";
    }

    // Mostrar confirmación de eliminación
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/terms/delete/{id}")
    public String showDeleteForm(@PathVariable int id, Model model) {
        Term term = TS.getById(id);
        model.addAttribute("term", term);
        return "terms/delete";
    }

    // Procesar eliminación
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/terms/delete")
    public String delete(@RequestParam("id") int id) {
        Term term = TS.getById(id);
        TS.delete(id);
        return "redirect:/terms";


    }

}
