package com.taller.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.taller.entidades.User;

public interface UserService {
	
	public List<User> listaUsuario();
	
	public Page<User> listaUsuario(Pageable pageable);
	
	public void guardar(User usuario) throws Exception;
	
	public User buscarXid(Long id);
	
	public void actualizar(User usuario);
	
	public void eliminar(Long id);

}
