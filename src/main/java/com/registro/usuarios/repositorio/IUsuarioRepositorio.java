package com.registro.usuarios.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.registro.usuarios.modelo.Usuario;

@Repository
public interface IUsuarioRepositorio extends JpaRepository<Usuario, Long>{

	public Usuario findByUsername(String username);
        
        public Usuario findByEmail(String email);
	
}
