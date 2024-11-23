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
import componentes.VideoCache;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleCloudStorageService {

    private final Storage storage;
    private final VideoCache videoCache;

    public GoogleCloudStorageService(VideoCache videoCache) {
        this.storage = StorageOptions.getDefaultInstance().getService();
        this.videoCache = videoCache;
    }

    public List<String> listarVideos(String bucketName, String folderName) {
        List<String> videoUrls = new ArrayList<>();

        // Verificar si la caché ya tiene datos
        if (!videoCache.getAll().isEmpty()) {
            videoCache.getAll().forEach((key, value) -> videoUrls.add(value));
            return videoUrls;
        }

        // Consultar el bucket si la caché está vacía
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
}
