package com.example.Kawasakii.controller;

import com.example.Kawasakii.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {

    private final UsuarioService usuarioService;

    @Autowired
    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro"; // plantilla registro.html
    }

    @PostMapping("/registro")
    public String procesarRegistro(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            usuarioService.registrarUsuario(username, password);
            model.addAttribute("success", "Registro exitoso. Ahora puedes iniciar sesión.");
            return "login"; // redirigir a la página de login
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }
}
