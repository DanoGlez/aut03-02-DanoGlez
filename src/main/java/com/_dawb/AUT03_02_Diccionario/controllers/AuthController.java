package com._dawb.AUT03_02_Diccionario.controllers;

import com._dawb.AUT03_02_Diccionario.interfaces.IUserService;
import com._dawb.AUT03_02_Diccionario.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    @Autowired
    private IUserService IUS;

    // Vista Login
    @GetMapping("/login")
    @PreAuthorize("!isAuthenticated()")
    public String loginForm() {
        return "auth/login";
    }

    // Vista Registro
    @GetMapping("/register")
    @PreAuthorize("!isAuthenticated()")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    // Registrar a un Usuario
    @PostMapping("/register")
    @PreAuthorize("!isAuthenticated()")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               Model model) {

        // Revisar que no haya dos usuarios con el mismo nickName
        if (IUS.findBynickName(user.getNickName()).isPresent()) {
            result.rejectValue("nickName", "error.user", "Ya existe una cuenta con ese nombre de usuario.");
        }

        // Revisar que no haya dos usuarios con el mismo Email
        if (IUS.findByEmail(user.getEmail()).isPresent()) {
            result.rejectValue("email", "error.user", "Ya existe una cuenta con ese correo electrónico.");
        }

        if (result.hasErrors()) {
            return "auth/register";
        }

        IUS.addUser(user);

        return "redirect:/login"; // Redirige al login tras registrar
    }
}