/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.registro.usuarios.repositorio;

import com.registro.usuarios.modelo.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author juanr
 */

@Repository
public interface LogsRepositorio extends JpaRepository<Logs, Long>{
}
