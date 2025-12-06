package com.example.Kawasakii.controller;

import com.example.Kawasakii.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class EstadisticasController {

    private final CompraService compraService;

    @Autowired
    public EstadisticasController(CompraService compraService) {
        this.compraService = compraService;
    }

    // Mapea la vista para mostrar las estadísticas
    @GetMapping("/estadisticas")
    public String mostrarEstadisticas(Model model) {
        // 🚨 Llamar a un nuevo método en CompraService para obtener datos de ventas.
        // Ejemplo: Ventas totales por mes
        Map<String, Double> ventasPorMes = compraService.calcularVentasPorMes();

        // Pasa los datos al modelo para que el HTML los use en Chart.js
        model.addAttribute("meses", ventasPorMes.keySet());
        model.addAttribute("ventas", ventasPorMes.values());

        return "VistaEstadisticas";
    }
}