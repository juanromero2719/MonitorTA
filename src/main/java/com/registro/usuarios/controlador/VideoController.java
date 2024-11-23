/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.usuarios.controlador;

/**
 *
 * @author juanr
 */
import com.registro.usuarios.servicio.GoogleCloudStorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VideoController {

    private final GoogleCloudStorageService storageService;

    public VideoController(GoogleCloudStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/videos")
    public List<String> listarVideos(@RequestParam(defaultValue = "spring-boot-monitor") String bucketName) {
        String folderName = "videos/";
        return storageService.listarVideos(bucketName, folderName);
    }
}