package com.example.Kawasakii.service;

import com.example.Kawasakii.model.Producto;
import com.example.Kawasakii.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductoServiceImpl implements ProductoService {

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

    // --- MÉTODOS DE FAVORITOS ---

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerFavoritos() {
        // Usa el método personalizado del repositorio: findByEsFavoritoTrue()
        return productoRepository.findByEsFavoritoTrue();
    }

    /**
     * 🆕 Implementación del método para marcar/desmarcar un producto como favorito.
     * @param id El ID del producto.
     * @param esFavorito true para marcar, false para quitar.
     */
    @Override
    @Transactional
    public void marcarComoFavorito(Long id, boolean esFavorito) {
        // 1. Buscar el producto. Si no existe, lanza la excepción.
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con ID: " + id));

        // 2. Establecer el estado de favorito
        // Asegúrate de que tu clase Producto tiene un método setEsFavorito(boolean)
        producto.setEsFavorito(esFavorito);

        // 3. Guardar el producto actualizado en la base de datos
        productoRepository.save(producto);
    }
}