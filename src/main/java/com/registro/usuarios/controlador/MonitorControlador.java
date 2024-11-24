/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.usuarios.controlador;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.property.TextAlignment;

        
import com.registro.usuarios.servicio.GoogleCloudStorageService;
import com.registro.usuarios.servicio.LogsServicio;
import componentes.VideoCache;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author juanr
 */

@Controller
public class MonitorControlador {
    
    private static final Logger logger = LoggerFactory.getLogger(MonitorControlador.class);
    
    private final GoogleCloudStorageService storageService;
    private final LogsServicio logsServicio;
    private final VideoCache videoCache;

    public MonitorControlador(GoogleCloudStorageService storageService, LogsServicio logsServicio, VideoCache videoCache) {
        this.storageService = storageService;
        this.logsServicio = logsServicio;
        this.videoCache = videoCache;
    }
    
    @GetMapping("/monitor")
    public String mostrarLogs(Model model) {
        return "monitor"; 
    }
    
    @GetMapping("/video")
        public String mostrarVideos(Model model) {
        return "videos"; 
    }
    
    @GetMapping("/log-detalle")
    public String mostrarDetalle(@RequestParam("archivo") String archivo, Model model) {
        String contenido = storageService.obtenerContenidoArchivo(archivo);
        model.addAttribute("contenido", contenido);
        model.addAttribute("archivo", archivo);
        return "log-detalle";
    }
    
    @GetMapping("/cache/data")
    public Map<String, String> getCacheData() {
        return videoCache.getAll();
    }
    
    @GetMapping("/log-detalle/pdf")
    public void descargarPDF(@RequestParam("archivo") String archivo, HttpServletResponse response) {
        String contenido = storageService.obtenerContenidoArchivo(archivo);

        try {
           // Configurar la respuesta para descargar el archivo PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=detalle-log.pdf");

            // Crear el PDF
            try (PdfWriter writer = new PdfWriter(response.getOutputStream())) {
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                // Agregar título
                document.add(new Paragraph("Detalle del Registro").setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));
                document.add(new Paragraph("Archivo: " + archivo).setFontSize(12).setTextAlignment(TextAlignment.LEFT));

                // Agregar contenido del archivo
                document.add(new Paragraph("Contenido:").setBold().setFontSize(14));
                document.add(new Paragraph(contenido).setFontSize(12));

                document.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el PDF: " + e.getMessage());
        }
    }

    
    
}
