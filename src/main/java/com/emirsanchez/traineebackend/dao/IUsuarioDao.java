package com.emirsanchez.traineebackend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.emirsanchez.traineebackend.model.Usuario;

@Repository
public interface IUsuarioDao extends JpaRepository<Usuario, Integer>{
	
    boolean existsByCorreo(String email);
    /*
    @Query("FROM Usuario u WHERE u.correo =:correo")
    Usuario obtenerUsuarioPorCredenciales(@Param("correo") String correo);
	*/
}
