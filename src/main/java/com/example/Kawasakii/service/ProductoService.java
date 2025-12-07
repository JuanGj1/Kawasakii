package com.example.Kawasakii.service;

import com.example.Kawasakii.model.Producto;
import java.util.List;

public interface ProductoService {

    Producto guardar(Producto producto);

    void eliminar(Long id);

    // ✅ Necesario para agregar al carrito
    Producto obtenerPorId(Long id);

    List<Producto> obtenerTodos();

    // Asumimos que también existe este método para la vista de favoritos
    List<Producto> obtenerFavoritos();

    // 🆕 Método requerido para el FavoritosController:
    /**
     * Marca o desmarca un producto específico como favorito.
     * @param id El ID del producto.
     * @param esFavorito true para marcar como favorito, false para quitar.
     */
    void marcarComoFavorito(Long id, boolean esFavorito);

}