/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.usuarios.controlador;

/**
 *
 * @author juanr
 */
import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.servicio.GoogleCloudStorageService;
import com.registro.usuarios.servicio.UsuarioServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
public class VideoController {

    private final GoogleCloudStorageService storageService;
    private final UsuarioServicio usuarioServicio;
    
    public VideoController(GoogleCloudStorageService storageService, UsuarioServicio usuarioServicio) {
        this.storageService = storageService;
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping("/videos")
    public List<String> listarVideos(@RequestParam(defaultValue = "spring-boot-monitor") String bucketName) {
        // Obtener el usuario logueado
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

        // Usar el ID del usuario para acceder a su carpeta de videos
        String userId = String.valueOf(usuario.getId());
        String folderName = userId + "/videos/";

        // Listar videos de la carpeta del usuario
        return storageService.listarVideos(bucketName, folderName);
    }
}