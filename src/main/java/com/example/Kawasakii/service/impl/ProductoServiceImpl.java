package com.example.Kawasakii.service.impl;

import com.example.Kawasakii.model.Producto;
import com.example.Kawasakii.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductoServiceImpl implements com.example.Kawasakii.service.ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // --- MÉTODOS REQUERIDOS POR LA APLICACIÓN ---

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    // FAVORITOS: ahora gestionados por UsuarioService (por usuario). Mantengo métodos vacíos/obsoletos para compatibilidad.

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerFavoritos() {
        // Deprecated: Favoritos ahora son por usuario. Usa UsuarioService.obtenerFavoritosDeUsuario(username).
        return List.of();
    }

    @Override
    @Transactional
    public void marcarComoFavorito(Long id, boolean esFavorito) {
        // Deprecated: No marcar productos globalmente como favoritos. Favoritos son por usuario.
        throw new UnsupportedOperationException("Favoritos globales no soportados. Usa UsuarioService para favoritos por usuario.");
    }
}