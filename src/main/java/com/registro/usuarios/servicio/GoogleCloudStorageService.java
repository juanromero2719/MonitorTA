/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.usuarios.servicio;

/**
 *
 * @author juanr
 */


import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.registro.usuarios.controlador.MonitorControlador;
import componentes.VideoCache;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GoogleCloudStorageService {

    private final Storage storage;
    private final VideoCache videoCache;
    private static final Logger logger = LoggerFactory.getLogger(GoogleCloudStorageService.class);

    public GoogleCloudStorageService(VideoCache videoCache) {
        this.storage = StorageOptions.getDefaultInstance().getService();
        this.videoCache = videoCache;
    }
    
    public List<String> listarVideos(String bucketName, String folderName) {
        List<String> videoUrls = new ArrayList<>();

        if (!videoCache.getAll().isEmpty()) {
            videoCache.getAll().forEach((key, value) -> videoUrls.add(value));
            return videoUrls;
        }

        for (Blob blob : storage.list(bucketName).iterateAll()) {
            String blobName = blob.getName();

            if (blobName.startsWith(folderName) && blobName.endsWith(".mp4")) {
                String videoUrl = "https://storage.googleapis.com/" + bucketName + "/" + blobName;

                videoUrls.add(videoUrl);
                videoCache.put(blobName, videoUrl);

            }

            if (videoCache.getAll().size() >= videoCache.getMaxSize()) {
                break;
            }
        }

        return videoUrls;
    }
    
    public String obtenerContenidoArchivo(String nombreArchivo) {
        try {
            // Reemplazar barras invertidas por barras normales
            String rutaNormalizada = nombreArchivo.replace("\\", "/");
                       
            // Obtener el archivo desde el bucket
            Blob blob = storage.get("spring-boot-monitor", rutaNormalizada);
            if (blob != null && blob.exists()) {
                return new String(blob.getContent());
            } else {
                return "Archivo no encontrado.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al obtener el contenido del archivo: " + e.getMessage();
        }
    }
}
