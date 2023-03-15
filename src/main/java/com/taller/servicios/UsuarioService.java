package com.taller.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.taller.entidades.Usuario;
import com.taller.entidades.DTO.UsuarioRegistroDTO;

public interface UsuarioService  extends UserDetailsService{
	
	public List<Usuario> listaUsuario();
	
	public Page<Usuario> listaUsuario(Pageable pageable);
	
	public Usuario guardarAdmin(UsuarioRegistroDTO registroDTO);
	
	public Usuario guardar(UsuarioRegistroDTO registroDTO);
	
	public Usuario buscarXid(Long id);
	
	public void actualizar(Usuario usuario);
	
	//public void eliminar(Long id);

}
