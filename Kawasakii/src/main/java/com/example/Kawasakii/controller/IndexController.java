package com.example.Kawasakii.controller;

import com.example.Kawasakii.model.Producto;
import com.example.Kawasakii.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class IndexController {

    private final ProductoService productoService;

    @Autowired
    public IndexController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Producto> listaProductos = productoService.obtenerTodos();
        model.addAttribute("productos", listaProductos);

        return "index"; // Devuelve index.html
    }
}