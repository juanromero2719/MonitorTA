/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.usuarios.servicio;

import com.registro.usuarios.modelo.Logs;
import com.registro.usuarios.repositorio.LogsRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author juanr
 */
@Service
public class LogsServicio {
    
    @Autowired
    private LogsRepositorio logsRepositorio;

    public List<Logs> obtenerTodosLosRegistros() {
        return logsRepositorio.findAll();
    }
    
    public Logs obtenerRegistroPorId(Long id) {
        return logsRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Registro no encontrado"));
    }
}
