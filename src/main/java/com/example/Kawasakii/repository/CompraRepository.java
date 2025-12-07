package com.example.Kawasakii.repository;

import com.example.Kawasakii.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    // No necesita contenido si solo usas los métodos CRUD estándar.
}