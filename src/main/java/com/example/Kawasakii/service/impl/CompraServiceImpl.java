package com.example.Kawasakii.service;

import com.example.Kawasakii.model.Compra;
import com.example.Kawasakii.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CompraServiceImpl implements CompraService {

    private final CompraRepository compraRepository;

    @Autowired
    public CompraServiceImpl(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    // --- 1. GUARDAR COMPRA (Usado en CarritoController) ---
    @Override
    @Transactional
    public Compra guardarCompra(Compra compra) {
        return compraRepository.save(compra);
    }

    // --- 2. OBTENER COMPRA POR ID (Necesario para el ticket/PDF) ---
    @Override
    @Transactional(readOnly = true)
    public Compra obtenerPorId(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Compra no encontrada con ID: " + id));
    }

    // --- 3. OBTENER TODAS LAS COMPRAS (Necesario para el historial) ---
    @Override
    @Transactional(readOnly = true)
    public List<Compra> obtenerTodas() {
        return compraRepository.findAll();
    }

    // ------------------------------------------------------------------
    // --- 4. 🚨 IMPLEMENTACIÓN: ESTADÍSTICAS DE VENTAS POR MES 🚨 ---
    // ------------------------------------------------------------------

    @Override
    @Transactional(readOnly = true)
    public Map<String, Double> calcularVentasPorMes() {
        // Obtener todas las compras de la base de datos
        List<Compra> todasLasCompras = compraRepository.findAll();

        // Formato para agrupar las compras por Mes y Año (ej: "Nov 2025")
        // Usamos Locale español para el nombre de los meses.
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy", new Locale("es", "ES"));

        // Agrupar y sumar las ventas usando Streams de Java
        Map<String, Double> ventasAgrupadas = todasLasCompras.stream()
                .collect(Collectors.groupingBy(
                        // Clave de la agrupación: El mes y año de la compra
                        compra -> sdf.format(compra.getFechaCompra()),

                        // Usar LinkedHashMap para intentar mantener el orden de inserción
                        LinkedHashMap::new,

                        // Valor: Sumar el campo 'total' de todas las compras en ese mes
                        Collectors.summingDouble(Compra::getTotal)
                ));

        // Nota: Si se requiere orden cronológico estricto, se necesita un paso
        // adicional de ordenamiento basado en la fecha original.

        return ventasAgrupadas;
    }
}