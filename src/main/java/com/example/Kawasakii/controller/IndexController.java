package com.example.Kawasakii.controller;

import com.example.Kawasakii.model.Producto;
import com.example.Kawasakii.service.ProductoService;
import com.example.Kawasakii.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class IndexController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    @Autowired
    public IndexController(ProductoService productoService, UsuarioService usuarioService) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        List<Producto> listaProductos = productoService.obtenerTodos();
        model.addAttribute("productos", listaProductos);

        Set<Long> favoritosIds = new HashSet<>();
        if (principal != null) {
            String username = principal.getName();
            usuarioService.obtenerFavoritosDeUsuario(username).forEach(p -> favoritosIds.add(p.getId()));
        }
        model.addAttribute("favoritosIds", favoritosIds);

        return "index"; // Devuelve index.html
    }
}