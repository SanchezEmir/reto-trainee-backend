package com.emirsanchez.traineebackend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.emirsanchez.traineebackend.dao.IUsuarioDao;
import com.emirsanchez.traineebackend.exeption.BadRequestException;
import com.emirsanchez.traineebackend.model.Usuario;
import com.emirsanchez.traineebackend.model.UsuarioDTO;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

	@Autowired
	private IUsuarioDao dao;
	
	@PersistenceContext
    EntityManager entityManager;

	@Override
	public List<UsuarioDTO> listarUsuarios() {
		return dao.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
	}

	@Override
	public Usuario listarporId(Integer id) {
		return dao.findById(id).orElse(null);
	}

	@Override
	public void crearUsuario(Usuario u) {
		
		boolean correoYaExiste = dao.existsByCorreo(u.getCorreo());
		if (correoYaExiste) {
			throw new BadRequestException("El correo "+u.getCorreo()+" ya fue registrado por otro usuario.");
		}
		
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		String hash = argon2.hash(1, 1024, 1, u.getPassword());
		u.setPassword(hash);
		
		dao.save(u);
	}

	@Override
	public void actualizarUsuario(Usuario u) {
		
		boolean correoYaExiste = dao.existsByCorreo(u.getCorreo());
		if (correoYaExiste) {
			throw new BadRequestException("El correo "+u.getCorreo()+" ya fue registrado por otro usuario.");
		}
		
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		String hash = argon2.hash(1, 1024, 1, u.getPassword());
		u.setPassword(hash);
		
		dao.saveAndFlush(u);
	}

	@Override
	public void eliminarUsuario(Integer id) {
		dao.deleteById(id);
	}
	
	public UsuarioDTO convertToDto(Usuario usuario) {
		
		UsuarioDTO dto = new UsuarioDTO();
		
		dto.setId(usuario.getId());
		dto.setUsuario(usuario.getUsuario());
		dto.setCorreo(usuario.getCorreo());
		dto.setFechaNacimiento(usuario.getFechaNacimiento());
		dto.setEdad(usuario.getEdad());
		
		return dto;
	}
	
	@Override
	public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) {
        String query = "FROM Usuario WHERE correo = :email";
        List<Usuario> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getCorreo())
                .getResultList();

        if (lista.isEmpty()) {
            return null;
        }

        String passwordHashed = lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHashed, usuario.getPassword())) {
            return lista.get(0);
        }
        return null;
    }
	
}
