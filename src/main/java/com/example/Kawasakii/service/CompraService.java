package com.example.Kawasakii.service;

import com.example.Kawasakii.model.Compra;
import java.util.List;
import java.util.Map;
public interface CompraService {
    Compra guardarCompra(Compra compra);

    Compra obtenerPorId(Long id);

    List<Compra> obtenerTodas();

    Map<String, Double> calcularVentasPorMes();

}