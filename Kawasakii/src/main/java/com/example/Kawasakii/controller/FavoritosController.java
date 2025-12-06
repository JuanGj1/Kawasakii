package  com.example.Kawasakii.controller;

import com.example.Kawasakii.model.Producto;
import com.example.Kawasakii.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/favoritos")
public class FavoritosController {

    private final ProductoService productoService;

    @Autowired
    public FavoritosController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // --- 1. VER LISTA DE FAVORITOS (Mapeo: /favoritos/ver) ---
    @GetMapping("/ver")
    public String verFavoritos(Model model) {

        // Llama al método de servicio que obtiene los productos marcados como favoritos
        List<Producto> favoritos = productoService.obtenerFavoritos();

        model.addAttribute("listaFavoritos", favoritos);

        return "VistaFavoritos";
    }

    // --- 2. AGREGAR A FAVORITOS (Mapeo: /favoritos/agregar/{id}) ---
    @GetMapping("/agregar/{id}")
    public String agregarAFavoritos(@PathVariable Long id, RedirectAttributes ra) {
        try {
            // Llama al método de servicio para marcar el producto como favorito
            productoService.marcarComoFavorito(id, true);

            ra.addFlashAttribute("alerta", "success");
            ra.addFlashAttribute("msg", "¡Producto agregado a tus favoritos!");

        } catch (Exception e) {
            ra.addFlashAttribute("alerta", "error");
            ra.addFlashAttribute("msg", "Error al agregar el producto.");
        }

        // Redirige de vuelta al catálogo principal (donde se agregó el producto)
        return "redirect:/";
    }

    @GetMapping("/quitar/{id}")
    public String quitarDeFavoritos(@PathVariable Long id, RedirectAttributes ra) {
        try {
            // Llama al método de servicio para desmarcar el producto como favorito
            productoService.marcarComoFavorito(id, false);

            ra.addFlashAttribute("alerta", "info");
            ra.addFlashAttribute("msg", "Producto eliminado de tus favoritos.");

        } catch (Exception e) {
            ra.addFlashAttribute("alerta", "error");
            ra.addFlashAttribute("msg", "Error al eliminar el producto.");
        }

        // Redirige de nuevo a la vista de favoritos para actualizar la lista
        return "redirect:/favoritos/ver";
    }
}