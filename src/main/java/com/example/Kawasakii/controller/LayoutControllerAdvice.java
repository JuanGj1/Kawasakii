package com.example.Kawasakii.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.security.Principal;

/**
 * ControllerAdvice que inyecta atributos globales en el modelo
 * para todas las vistas (plantillas Thymeleaf).
 *
 * Esto permite que la navbar y el footer accedan al nombre del usuario
 * sin necesidad de modificar cada controlador.
 */
@ControllerAdvice
public class LayoutControllerAdvice {

    /**
     * Inyecta el nombre del usuario autenticado en el modelo.
     * Si no hay usuario autenticado, devuelve null.
     *
     * En Thymeleaf se accede con: ${currentUsername}
     */
    @ModelAttribute("currentUsername")
    public String currentUsername(Principal principal) {
        return principal != null ? principal.getName() : null;
    }

    /**
     * Inyecta el nombre de la aplicación en el modelo.
     *
     * En Thymeleaf se accede con: ${appName}
     */
    @ModelAttribute("appName")
    public String appName() {
        return "Kawasaki Services";
    }
}

