package com.emirsanchez.traineebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emirsanchez.traineebackend.dao.IUsuarioDao;
import com.emirsanchez.traineebackend.model.Usuario;
import com.emirsanchez.traineebackend.security.JWTUtil;
import com.emirsanchez.traineebackend.service.IUsuarioService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
    private IUsuarioDao dao;
	
	@Autowired
	private IUsuarioService service;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {

        Usuario usuarioLogueado = service.obtenerUsuarioPorCredenciales(usuario);
        if (usuarioLogueado != null) {
            String tokenJwt = jwtUtil.create(String.valueOf(usuarioLogueado.getId()), usuarioLogueado.getCorreo());
            return new ResponseEntity<String>(tokenJwt,HttpStatus.OK);
        }
        return new ResponseEntity<String>("FALLO_AL_INICIAR",HttpStatus.BAD_REQUEST);
    }
	
	

}
