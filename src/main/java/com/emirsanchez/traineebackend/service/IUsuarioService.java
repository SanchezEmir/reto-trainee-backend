package com.emirsanchez.traineebackend.service;

import java.util.List;

import com.emirsanchez.traineebackend.model.Usuario;
import com.emirsanchez.traineebackend.model.UsuarioDTO;

public interface IUsuarioService {
	
	abstract List<UsuarioDTO> listarUsuarios();
	abstract Usuario listarporId(Integer id);
	abstract void crearUsuario(Usuario u);
	abstract void actualizarUsuario(Usuario u);
	abstract void eliminarUsuario(Integer id);
	//abstract Usuario obtenerUsuarioPorCredenciales(String correo);
	
	abstract Usuario obtenerUsuarioPorCredenciales(Usuario usuario);

}
