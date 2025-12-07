package com.example.Kawasakii.controller;

import com.example.Kawasakii.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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
        // Obtener ventas por mes (puede ser un LinkedHashMap para mantener orden)
        Map<String, Double> ventasPorMes = compraService.calcularVentasPorMes();

        // Convertir a listas para que Thymeleaf las renderice como arrays JS
        List<String> meses = new ArrayList<>(ventasPorMes.keySet());
        List<Double> ventas = new ArrayList<>(ventasPorMes.values());

        model.addAttribute("meses", meses);
        model.addAttribute("ventas", ventas);

        return "VistaEstadisticas";
    }
}