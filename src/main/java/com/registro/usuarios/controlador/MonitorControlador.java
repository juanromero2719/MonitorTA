/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.usuarios.controlador;

import com.registro.usuarios.modelo.Logs;
import com.registro.usuarios.servicio.LogsServicio;
import componentes.VideoCache;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired
    private VideoCache videoCache;

    
    @Autowired
    private LogsServicio logsServicio;

    @GetMapping("/monitor")
    public String mostrarLogs(Model model) {
        return "monitor"; 
    }
    
    @GetMapping("/video")
        public String mostrarVideos(Model model) {
        return "videos"; 
    }
    
    @GetMapping("/log-detalle")
    public String mostrarDetalle(@RequestParam("id") Long id, Model model) {
        Logs log = logsServicio.obtenerRegistroPorId(id);
        model.addAttribute("log", log);
        return "log-detalle";
    }
    
    @GetMapping("/cache/data")
    public Map<String, String> getCacheData() {
        return videoCache.getAll();
    }

    
    
}
