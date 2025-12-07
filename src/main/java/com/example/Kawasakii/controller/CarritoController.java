package com.example.Kawasakii.controller;

import com.example.Kawasakii.model.Compra;
import com.example.Kawasakii.model.ItemCarrito;
import com.example.Kawasakii.model.Producto;
import com.example.Kawasakii.service.ProductoService;
import com.example.Kawasakii.service.CompraService; // Importar el servicio de compra
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final ProductoService productoService;
    private final CompraService compraService; // Inyección del servicio de Compra

    @Autowired
    public CarritoController(ProductoService productoService, CompraService compraService) {
        this.productoService = productoService;
        this.compraService = compraService; // Asignación del servicio de Compra
    }

    // Método de utilidad para obtener o inicializar el carrito en la sesión
    private List<ItemCarrito> getOrCreateCarrito(HttpSession session) {
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }
        return carrito;
    }

    @GetMapping("/agregar/{id}")
    public String agregarAlCarrito(@PathVariable("id") Long idProducto, HttpSession session, RedirectAttributes ra) {
        List<ItemCarrito> carrito = getOrCreateCarrito(session);
        try {
            Producto producto = productoService.obtenerPorId(idProducto);
            boolean encontrado = false;
            for (ItemCarrito item : carrito) {
                if (item.getProducto().getId().equals(idProducto)) {
                    item.setCantidad(item.getCantidad() + 1);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                carrito.add(new ItemCarrito(producto, 1));
            }
            ra.addFlashAttribute("alerta", "success");
            ra.addFlashAttribute("msg", producto.getNombre() + " agregado al carrito.");
        } catch (NoSuchElementException e) {
            ra.addFlashAttribute("alerta", "error");
            ra.addFlashAttribute("msg", "Producto no encontrado.");
        }
        return "redirect:/";
    }

    @GetMapping({"/ver", ""})
    public String verCarrito(HttpSession session, Model model) {
        List<ItemCarrito> carrito = getOrCreateCarrito(session);
        model.addAttribute("carrito", carrito);
        return "VistaCarrito";
    }

    @PostMapping("/eliminaritem/{id}")
    public String eliminarItem(@PathVariable("id") Long idProducto, HttpSession session) {
        List<ItemCarrito> carrito = getOrCreateCarrito(session);
        carrito.removeIf(item -> item.getProducto().getId().equals(idProducto));
        return "redirect:/carrito/ver";
    }

    @GetMapping("/resumen")
    public String verResumen(HttpSession session, Model model) {
        List<ItemCarrito> carrito = getOrCreateCarrito(session);
        if (carrito.isEmpty()) {
            return "redirect:/carrito/ver";
        }
        double total = carrito.stream().mapToDouble(ItemCarrito::getSubtotal).sum();
        model.addAttribute("totalCompra", total);
        return "VistaResumen";
    }

    // ✅ MÉTODO FINALIZAR COMPRA: Procesa la orden y carga la VistaTicket
    @PostMapping("/pagar")
    public String finalizarCompra(
            HttpSession session,
            Model model,
            @RequestParam("metodoPago") String metodoPago
    ) {
        List<ItemCarrito> carrito = getOrCreateCarrito(session);

        if (carrito.isEmpty()) {
            return "redirect:/carrito/ver";
        }

        try {
            // 1. Crear la entidad Compra
            Compra nuevaCompra = new Compra();
            nuevaCompra.setMetodoPago(metodoPago);
            nuevaCompra.setTotal(carrito.stream().mapToDouble(ItemCarrito::getSubtotal).sum());
            nuevaCompra.setFechaCompra(new Date());

            // Llama al CompraService
            Compra compraGuardada = compraService.guardarCompra(nuevaCompra);

            //  Limpiar el carrito de la sesión
            session.removeAttribute("carrito");

            //  Pasar la compra guardada a la vista
            model.addAttribute("compraFinalizada", compraGuardada);

            //  Retornar la vista de ticket
            return "VistaTicket";

        } catch (Exception e) {

            model.addAttribute("alerta", "error");
            model.addAttribute("msg", "Error al finalizar la compra: " + e.getMessage());
            return "redirect:/carrito/resumen";
        }
    }
}