package com.taller.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taller.entidades.User;
import com.taller.repositorios.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository usuariorepo;

	@Override
	@Transactional(readOnly = true)
	public List<User> listaUsuario() {
		return (List<User>) usuariorepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<User> listaUsuario(Pageable pageable) {
		return usuariorepo.findAll(pageable);
	}
	
	@Override
	@Transactional
	public void guardar(User usuario) throws Exception {
			usuario = usuariorepo.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public User buscarXid(Long id) {
		return usuariorepo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void actualizar(User usuario) {
		usuariorepo.save(usuario);
	}

	@Override
	@Transactional
	public void eliminar(Long id) {
		usuariorepo.deleteById(id);
	}

}
