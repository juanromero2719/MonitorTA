/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package componentes;

/**
 *
 * @author juanr
 */

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

public class VideoCache {

    private final Map<String, String> cache;
    private final int maxSize;

    public VideoCache(int maxSize) {
        this.maxSize = maxSize;

        // Configurar una LinkedHashMap con eliminación basada en capacidad
        this.cache = new LinkedHashMap<String, String>(maxSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > maxSize;
            }
        };
    }

    public void put(String videoId, String videoUrl) {
        cache.put(videoId, videoUrl);
    }

    public String get(String videoId) {
        return cache.get(videoId);
    }

    public Map<String, String> getAll() {
        return cache;
    }

    public boolean contains(String videoId) {
        return cache.containsKey(videoId);
    }

    public int getMaxSize() {
        return maxSize;
    }
    
    
}