package com.example.Kawasakii.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /**
     * Mapea la URL /login a la vista login.html.
     * * Spring Security interceptará esta petición. Si el usuario ya está logueado,
     * Security lo redirigirá a la página de éxito. Si no, Security permite que
     * se cargue esta vista para que el usuario pueda introducir credenciales.
     */
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // Nombre del archivo de plantilla (login.html)
    }
}