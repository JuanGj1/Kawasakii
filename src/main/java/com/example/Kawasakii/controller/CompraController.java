package com.example.Kawasakii.controller;

import com.example.Kawasakii.model.Compra;
import com.example.Kawasakii.service.CompraService;
import com.example.Kawasakii.service.PdfService; // 🚨 AÑADIDO: Importar el servicio PDF
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders; // Necesario para la descarga
import org.springframework.http.MediaType;  // Necesario para la descarga
import org.springframework.http.ResponseEntity; // Necesario para la descarga
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // Necesario para el ID del ticket
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.NoSuchElementException; // Necesario para manejar errores

@Controller
@RequestMapping("/compras")
public class CompraController {

    private final CompraService compraService;
    private final PdfService pdfService; // 🚨 AÑADIDO: Inyección de PdfService

    @Autowired
    public CompraController(CompraService compraService, PdfService pdfService) { // 🚨 MODIFICADO: Añadir PdfService al constructor
        this.compraService = compraService;
        this.pdfService = pdfService;
    }


    @GetMapping("/historial")
    public String verHistorial(Model model) {
        List<Compra> historial = compraService.obtenerTodas();
        model.addAttribute("historial", historial);
        return "VistaHistorial"; // Retorna la plantilla Thymeleaf
    }


    @GetMapping("/ticket/{id}")
    public ResponseEntity<byte[]> descargarTicket(@PathVariable Long id) {
        try {
            // Buscar la compra
            Compra compra = compraService.obtenerPorId(id);

            // Generar el PDF
            byte[] pdfBytes = pdfService.generarTicketPdf(compra);

            String nombreArchivo = "ticket_kawasaki_" + id + ".pdf";

            // Retornar la respuesta HTTP con el archivo PDF
            return ResponseEntity.ok()
                    // Indica al navegador que descargue el archivo
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreArchivo + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (NoSuchElementException e) {
            // 404 si la compra no existe
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Manejar errores de generación de PDF o I/O
            System.err.println("Error al generar o servir el PDF: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}