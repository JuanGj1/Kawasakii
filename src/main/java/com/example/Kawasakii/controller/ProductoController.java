package com.example.Kawasakii.controller;

import com.example.Kawasakii.model.Producto;
import com.example.Kawasakii.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/productos") // Base URL: /productos
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Mapea la URL: GET /productos/listar
    @GetMapping("/listar")
    public String listarProductos(Model model) {

        List<Producto> productos = productoService.obtenerTodos();

        model.addAttribute("productos", productos);

        // Devuelve el nombre de la plantilla HTML (gestionproductos.html)
        return "gestionproductos";
    }

    // -------------------------------------------------------------


    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes ra) {

        productoService.eliminar(id); // Llama al servicio para eliminar

        // Añade un mensaje de éxito para mostrarlo después de la redirección
        ra.addFlashAttribute("alerta", "success");
        ra.addFlashAttribute("msg", "Producto ID " + id + " eliminado con éxito.");

        // Redirige de vuelta a la lista actualizada
        return "redirect:/productos/listar";
    }

    // -------------------------------------------------------------

    // --- 3. MODIFICAR (Botón: Modificar) ---
    // Mapea la URL: GET /productos/editar/{id}
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {

        Producto producto = productoService.obtenerPorId(id);

        if (producto != null) {
            model.addAttribute("producto", producto);
            // Asumiendo que usas una única plantilla para crear y editar, por ejemplo:
            return "formulario_producto"; // Nombre de tu plantilla de edición
        }

        // Si el producto no se encuentra, regresa a la lista
        return "redirect:/productos/listar";
    }

    // --- 4. NUEVO (Botón: Agregar Producto) ---
    // Mapea la URL: GET /productos/nuevo
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {

        // Crea una instancia vacía para el formulario
        model.addAttribute("producto", new Producto());

        // Devuelve la misma plantilla que se usa para editar, pero sin datos precargados
        return "formulario_producto";
    }

    // -------------------------------------------------------------

    // --- 5. GUARDAR / ACTUALIZAR (Maneja POST de Nuevo o Editar) ---
    // Mapea la URL: POST /productos/guardar
    // (Asume que el formulario de edición/nuevo apunta a esta URL)
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto, RedirectAttributes ra) {

        boolean esNuevo = (producto.getId() == null);
        productoService.guardar(producto); // Guarda o actualiza (si tiene ID)

        // Mensaje de éxito
        ra.addFlashAttribute("alerta", "success");
        if (esNuevo) {
            ra.addFlashAttribute("msg", "Producto agregado exitosamente.");
        } else {
            ra.addFlashAttribute("msg", "Producto modificado exitosamente.");
        }

        // Vuelve a la lista de productos
        return "redirect:/productos/listar";
    }
}