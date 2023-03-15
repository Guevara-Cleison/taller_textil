package com.taller.servicios;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taller.entidades.Role;
import com.taller.entidades.Usuario;
import com.taller.entidades.DTO.UsuarioRegistroDTO;
import com.taller.repositorios.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuariorepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> listaUsuario() {
		return (List<Usuario>) usuariorepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> listaUsuario(Pageable pageable) {
		return usuariorepo.findAll(pageable);
	}
	
	@Override
	@Transactional
	public Usuario guardarAdmin(UsuarioRegistroDTO registroDTO){
		Usuario usuario = new Usuario(registroDTO.getNombre(), registroDTO.getApellido(), 
				registroDTO.getEmail(), registroDTO.getUsername(), 
				passwordEncoder.encode(registroDTO.getPassword()), Arrays.asList(new Role("ROLE_ADMIN")));
		return usuariorepo.save(usuario);
	}
	
	@Override
	@Transactional
	public Usuario guardar(UsuarioRegistroDTO registroDTO) {
		Usuario usuario = new Usuario(registroDTO.getNombre(), registroDTO.getApellido(), 
				registroDTO.getEmail(), registroDTO.getUsername(), 
				passwordEncoder.encode(registroDTO.getPassword()), Arrays.asList(new Role("ROLE_USER")));
		return usuariorepo.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario buscarXid(Long id) {
		return usuariorepo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void actualizar(Usuario usuario) {
		usuariorepo.save(usuario);
	}

	/*
	@Override
	@Transactional
	public void eliminar(Long id) {
		usuariorepo.deleteById(id);
	}
	*/
	
	//*******************SEGURIDAD*********************

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuariorepo.findByUsername(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario o contraseña invalidos");
		}
		
		return new User(usuario.getUsername(), usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
	}

}
