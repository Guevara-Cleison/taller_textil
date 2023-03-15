package com.taller.controlador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taller.entidades.Usuario;
import com.taller.entidades.DTO.UsuarioRegistroDTO;
import com.taller.repositorios.RolRepository;
import com.taller.servicios.UsuarioService;
import com.taller.util.paginacion.PageRender;

@Controller
@RequestMapping(value = "/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService userService;
	
	@Autowired
	private RolRepository rolrepo;
	
	
	
	
	@GetMapping({"/listar",""})
	public String listarUsuarios(@RequestParam(name = "page", defaultValue = "0")int page,Model modelo) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Usuario> usuarios = userService.listaUsuario(pageRequest);
		PageRender<Usuario> pageRender = new PageRender<>("/usuario/listar", usuarios);
		
		modelo.addAttribute("usuarios", usuarios);
		modelo.addAttribute("page", pageRender);
		
		return "usuario/usuario-lista";
	}
	
	@GetMapping("/nuevo")
	public String mostrarFormularoRegistroUsuario(Model modelo) {
		Usuario usuario = new Usuario();
		modelo.addAttribute("usuario", usuario);
		modelo.addAttribute("roles", rolrepo.findAll());
		return "usuario/usuario-nuevo";
	}
	
	@PostMapping("/guardar")
	public String guardarUsuario(@ModelAttribute("usuario") UsuarioRegistroDTO usuarioDTO) {
		
		userService.guardarAdmin(usuarioDTO);
		
		return "redirect:/usuario/listar";
	}
	
	@GetMapping("/editar/{id}") 
	public String mostrarFormularoEditarUsuario(@PathVariable(value = "id") Long id, Model modelo) {
		modelo.addAttribute("usuario",userService.buscarXid(id));
		modelo.addAttribute("roles", rolrepo.findAll());
		
		return "usuario/usuario-editar";
	}
	
	@PostMapping("/editar/{id}")
	public String actualizarUsuario(@PathVariable(value = "id") Long id ,@ModelAttribute("usuario") Usuario usuario) {
		
		Usuario usuarioExistente = userService.buscarXid(id);
		usuarioExistente.setId(id);
		usuarioExistente.setNombre(usuario.getNombre());
		usuarioExistente.setApellido(usuario.getApellido());
		usuarioExistente.setEmail(usuario.getEmail());
		usuarioExistente.setUsername(usuario.getUsername());
		usuarioExistente.setRoles(usuario.getRoles());
		
		userService.actualizar(usuarioExistente);
		return "redirect:/usuario/listar";
	}
	
	/*@GetMapping("eliminar/{id}")
	public String eliminarUsuario(@PathVariable(value = "id") Long id) {
		if(id > 0) {
			userService.eliminar(id);
		}
		return "redirect:/usuario/listar";
	} */
 
}
