package com._dawb.AUT03_02_Diccionario.controllers;

import com._dawb.AUT03_02_Diccionario.interfaces.IRoleService;
import com._dawb.AUT03_02_Diccionario.interfaces.IUserService;
import com._dawb.AUT03_02_Diccionario.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private IRoleService IRS;
    @Autowired
    private IUserService IUS;

    // Vista Premium
    @GetMapping("/premium")
    @PreAuthorize("!hasAnyRole('PREMIUM', 'ADMIN')")
    public String premium(){
        return "premium";
    }

    // Añadir Rol de Premium
    @PostMapping("/premium")
    @PreAuthorize("!hasAnyRole('PREMIUM', 'ADMIN')")
    public String newPremium(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nickName = auth.getName();

        User user = IUS.findBynickName(nickName).orElseThrow();

        user.addRole(IRS.findByName("ROLE_PREMIUM").orElseThrow());

        IUS.updateUserRoles(user);

        return "redirect:/";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String users(Model model){
        model.addAttribute("users", IUS.getAll());
        return "users";
    }
}
