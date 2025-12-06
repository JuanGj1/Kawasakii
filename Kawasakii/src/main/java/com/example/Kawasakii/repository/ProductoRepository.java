package com.example.Kawasakii.repository;

import com.example.Kawasakii.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Búsqueda personalizada: Obtiene todos los productos donde esFavorito = true
    List<Producto> findByEsFavoritoTrue();
}