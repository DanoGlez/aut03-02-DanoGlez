package com._dawb.AUT03_02_Diccionario.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // Página Principal
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
