/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro.usuarios.servicio;

/**
 *
 * @author juanr
 */
import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.repositorio.IUsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {

    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;

    public Usuario findByUsername(String username) {
        return usuarioRepositorio.findByUsername(username);
    }
    
    public Usuario findByEmail(String username) {
        return usuarioRepositorio.findByEmail(username);
    }
}
