package com.example.Kawasakii.service;

import com.example.Kawasakii.model.Compra; // Asegúrate de que Compra está importada

public interface PdfService {


    byte[] generarTicketPdf(Compra compra);
}