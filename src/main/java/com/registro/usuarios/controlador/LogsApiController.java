/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.usuarios.controlador;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.registro.usuarios.modelo.Usuario;

import com.registro.usuarios.servicio.LogsServicio;


import java.util.ArrayList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.registro.usuarios.servicio.UsuarioServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author juanr
 */
@RestController
public class LogsApiController {

    private final LogsServicio logsServicio;
    private final UsuarioServicio usuarioServicio;
    private static final Logger logger = LoggerFactory.getLogger(LogsApiController.class);

    public LogsApiController(LogsServicio logsServicio, UsuarioServicio usuarioServicio) {
        this.logsServicio = logsServicio;
        this.usuarioServicio = usuarioServicio;
    }

      @GetMapping("/api/user-logs")
    public List<String> obtenerLogsDelUsuario() {
        List<String> logs = new ArrayList<>();
        try {
            // Obtener el username del usuario logueado
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }

            // Buscar el usuario por su username
            Usuario usuario = usuarioServicio.findByEmail(username);
            
            if (usuario == null) {
                throw new RuntimeException("Usuario no encontrado: " + username);
            }

            
            String userId = String.valueOf(usuario.getId());
            // Inicializar cliente de Google Cloud Storage
            Storage storage = StorageOptions.getDefaultInstance().getService();

            // Listar los archivos en el bucket dentro de la ruta "{userId}/logs/"
            Iterable<Blob> blobs = storage.list("spring-boot-monitor").iterateAll();
            for (Blob blob : blobs) {
                String nombreArchivo = blob.getName();
                // Filtrar archivos que estén en "{userId}/logs/" y tengan extensión .txt
                if (nombreArchivo.startsWith(userId + "/logs/") && nombreArchivo.endsWith(".txt")) {
                    logs.add(nombreArchivo); // Agregar el nombre del archivo a la lista
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs; // Retornar la lista de nombres de archivos
    }
}