package com.example.Kawasakii.service;

import com.example.Kawasakii.model.Compra;
import com.example.Kawasakii.model.ItemCarrito; // Necesitas esta clase para los detalles
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public byte[] generarTicketPdf(Compra compra) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Usamos A6 para un formato de ticket pequeño
        Document document = new Document(PageSize.A6);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DecimalFormat currencyFormat = new DecimalFormat("#,##0.00");

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            // --- 1. Estilos Base ---
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(0, 128, 0)); // Verde Oscuro
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            Font contentFont = new Font(Font.FontFamily.HELVETICA, 9);
            Font totalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.RED);

            // --- 2. Encabezado del Ticket ---
            Paragraph title = new Paragraph("TICKET KAWASAKI SERVICE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(15);
            document.add(title);

            // --- 3. Información de la Compra ---
            document.add(new Paragraph("Fecha: " + dateFormat.format(compra.getFechaCompra()), contentFont));
            document.add(new Paragraph("Pedido ID: " + compra.getId(), contentFont));
            document.add(new Paragraph("Método de Pago: " + compra.getMetodoPago(), contentFont));
            document.add(Chunk.NEWLINE);

            // --- 4. Detalles de los Productos (Tabla) ---
            document.add(new Paragraph("Detalles del Pedido:", headerFont));
            document.add(Chunk.NEWLINE);

            // Crear tabla con 4 columnas
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{30, 15, 20, 25}); // Distribución de ancho

            // Encabezados de la Tabla
            addTableHeader(table, "Producto", "Cant.", "Precio Unit.", "Subtotal", headerFont);

            // Iterar sobre los Items del Carrito (Requiere que Compra tenga getItems())
            List<ItemCarrito> items = compra.getItems();
            if (items != null) {
                for (ItemCarrito item : items) {
                    addContentRow(table,
                            item.getProducto().getNombre(),
                            String.valueOf(item.getCantidad()),
                            "$" + currencyFormat.format(item.getProducto().getPrecio()),
                            "$" + currencyFormat.format(item.getSubtotal()),
                            contentFont);
                }
            }

            document.add(table);
            document.add(Chunk.NEWLINE);

            // --- 5. Total ---
            Paragraph total = new Paragraph("TOTAL PAGADO: $" + currencyFormat.format(compra.getTotal()), totalFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
            return new byte[0];
        } catch (Exception e) {
            // Manejar errores si items es null o si falta ProductoService para ItemCarrito
            e.printStackTrace();
            return new byte[0];
        }

        return baos.toByteArray();
    }

    // --- Métodos de Utilidad para la Tabla PDF ---

    private void addTableHeader(PdfPTable table, String col1, String col2, String col3, String col4, Font font) {
        PdfPCell cell;

        cell = new PdfPCell(new Phrase(col1, font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBackgroundColor(new BaseColor(230, 230, 230)); // Fondo gris claro
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(col2, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(230, 230, 230));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(col3, font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBackgroundColor(new BaseColor(230, 230, 230));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(col4, font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBackgroundColor(new BaseColor(230, 230, 230));
        table.addCell(cell);
    }

    private void addContentRow(PdfPTable table, String col1, String col2, String col3, String col4, Font font) {
        PdfPCell cell;

        // Producto
        cell = new PdfPCell(new Phrase(col1, font));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);

        // Cantidad
        cell = new PdfPCell(new Phrase(col2, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        // Precio Unitario
        cell = new PdfPCell(new Phrase(col3, font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);

        // Subtotal
        cell = new PdfPCell(new Phrase(col4, font));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
    }
}