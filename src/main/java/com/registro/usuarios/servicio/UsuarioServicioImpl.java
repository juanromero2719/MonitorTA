package com.registro.usuarios.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.registro.usuarios.controlador.dto.UsuarioRegistroDTO;
import com.registro.usuarios.modelo.Usuario;
import com.registro.usuarios.repositorio.IUsuarioRepositorio;
import java.util.ArrayList;

@Service
public class UsuarioServicioImpl implements IUsuarioServicio{

    private IUsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsuarioServicioImpl(IUsuarioRepositorio usuarioRepositorio) {
        super();
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Usuario guardar(UsuarioRegistroDTO registroDTO) {
      
        Usuario usuario = new Usuario(
                registroDTO.getNombre(),
                registroDTO.getUsuario(), 
                registroDTO.getEmail(),
                passwordEncoder.encode(registroDTO.getPassword())
        );
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioRepositorio.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario o password inválidos");
        }
        
        return new User(usuario.getEmail(), usuario.getPassword(), new ArrayList<>());
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }
    
    public Usuario findByUsername(String username) {
        return usuarioRepositorio.findByUsername(username);
    }
}