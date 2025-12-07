package com.example.Kawasakii.controller;

import com.example.Kawasakii.model.Producto;
import com.example.Kawasakii.service.ProductoService;
import com.example.Kawasakii.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/favoritos")
public class FavoritosController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    @Autowired
    public FavoritosController(ProductoService productoService, UsuarioService usuarioService) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    // --- 1. VER LISTA DE FAVORITOS (Mapeo: /favoritos/ver) ---
    @GetMapping("/ver")
    public String verFavoritos(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        List<Producto> favoritos = usuarioService.obtenerFavoritosDeUsuario(username);

        model.addAttribute("listaFavoritos", favoritos);
        return "VistaFavoritos";
    }

    // --- 2. AGREGAR A FAVORITOS (Mapeo: /favoritos/agregar/{id}) ---
    @GetMapping("/agregar/{id}")
    public String agregarAFavoritos(@PathVariable Long id, Principal principal, RedirectAttributes ra) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        try {
            usuarioService.agregarFavorito(username, id);
            ra.addFlashAttribute("alerta", "success");
            ra.addFlashAttribute("msg", "¡Producto agregado a tus favoritos!");
        } catch (Exception e) {
            ra.addFlashAttribute("alerta", "error");
            ra.addFlashAttribute("msg", "Error al agregar el producto.");
        }
        return "redirect:/";
    }

    // --- QUITAR FAVORITOS por usuario ---
    @GetMapping("/quitar/{id}")
    public String quitarDeFavoritos(@PathVariable Long id, RedirectAttributes ra, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        try {
            usuarioService.quitarFavorito(username, id);
            ra.addFlashAttribute("alerta", "info");
            ra.addFlashAttribute("msg", "Producto eliminado de tus favoritos.");
        } catch (Exception e) {
            ra.addFlashAttribute("alerta", "error");
            ra.addFlashAttribute("msg", "Error al eliminar el producto.");
        }
        return "redirect:/favoritos/ver";
    }
}