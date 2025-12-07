package com.example.Kawasakii.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class PostLoginController {


    @GetMapping("/postLoginHandler")
    public String handlePostLogin(Principal principal, HttpSession session) {

        // 1. Obtener el nombre del usuario autenticado (Principal ya lo proporciona)
        if (principal != null) {
            String username = principal.getName();

            session.setAttribute("nombreUsuario", username);

            // Opcional: registrar el evento
            System.out.println("Usuario autenticado y guardado en sesión: " + username);

            return "redirect:/Panel.html";
        }

        return "redirect:/";
    }
}