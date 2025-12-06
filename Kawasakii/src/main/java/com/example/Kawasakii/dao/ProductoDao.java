package com.example.Kawasakii.dao;

import com.example.Kawasakii.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.List;

@Repository
// 2. Extiende JpaRepository<Entidad, Tipo_de_ID>
// Asumo que el ID de tu entidad Producto es Integer (int).
public interface ProductoDao extends JpaRepository<Producto, Integer> {

}