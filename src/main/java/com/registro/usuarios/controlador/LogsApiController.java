/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.usuarios.controlador;

import com.registro.usuarios.modelo.Logs;
import com.registro.usuarios.servicio.LogsServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
/**
 *
 * @author juanr
 */
@RestController
public class LogsApiController {

    private final LogsServicio logsServicio;

    public LogsApiController(LogsServicio logsServicio) {
        this.logsServicio = logsServicio;
    }

    @GetMapping("/api/logs")
    public List<Logs> obtenerLogs() {
        return logsServicio.obtenerTodosLosRegistros(); 
    }
    
    @GetMapping("/api/logs/{id}")
    public Logs obtenerLogPorId(@PathVariable Long id) {
        return logsServicio.obtenerRegistroPorId(id);
    }
}