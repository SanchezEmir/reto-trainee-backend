package com.emirsanchez.traineebackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.emirsanchez.traineebackend.model.Usuario;
import com.emirsanchez.traineebackend.model.UsuarioDTO;
import com.emirsanchez.traineebackend.security.JWTUtil;
import com.emirsanchez.traineebackend.service.IUsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	@Autowired
	private IUsuarioService service;
	
	@Autowired
	private JWTUtil jwt;
	
	@GetMapping(path = "/listar")
	public @ResponseBody ResponseEntity<List<UsuarioDTO>> listarUsuario(@RequestHeader(value = "Authorization") String token) {
		
		if (!validarToken(token)) { return null; }
		
		return new ResponseEntity<List<UsuarioDTO>>(service.listarUsuarios(), HttpStatus.OK);
	}
	
	@PostMapping(path = "/crear")
	public ResponseEntity<Void> crearUsuario(@RequestBody @Validated Usuario usuario){
		service.crearUsuario(usuario);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/actualizar")
	public ResponseEntity<Void> actualizarUsuario(@RequestHeader(value = "Authorization") String token, 
													@RequestBody @Validated Usuario usuario){
		
		if (!validarToken(token)) { return null; }
		
		Usuario u = service.listarporId(usuario.getId());
		
		if(u != null) {
			service.actualizarUsuario(usuario);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		
	}
	
	@DeleteMapping(path = "/eliminar/{id}")
	public ResponseEntity<Void> eliminarUsuario(@RequestHeader(value = "Authorization") String token,
												@PathVariable Integer id){
		
		if (!validarToken(token)) { return null; }
		
		Usuario u = service.listarporId(id);
		
		if(u != null) {
			service.eliminarUsuario(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		
	}
	
	private boolean validarToken(String token) {
        String usuarioId = jwt.getKey(token);
        return usuarioId != null;
    }
}
