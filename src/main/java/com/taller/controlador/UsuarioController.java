package com.taller.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String guardarUsuario(@Validated @ModelAttribute("usuario") UsuarioRegistroDTO usuarioDTO, BindingResult bindingResult, RedirectAttributes redirect, Model modelo) {
		if(bindingResult.hasErrors()) {
			modelo.addAttribute("usuario", usuarioDTO);
			return "usuario/usuario-nuevo";
		}
		userService.guardarAdmin(usuarioDTO);
		redirect.addFlashAttribute("msgExito", "El usuario se ha agregado correctamente");
		return "redirect:/usuario/listar";
	}
	
	@GetMapping("/editar/{id}") 
	public String mostrarFormularoEditarUsuario(@PathVariable(value = "id") Long id, Model modelo) {
		modelo.addAttribute("usuario",userService.buscarXid(id));
		modelo.addAttribute("roles", rolrepo.findAll());
		
		return "usuario/usuario-editar";
	}
	
	@PostMapping("/editar/{id}")
	public String actualizarUsuario(@PathVariable(value = "id") Long id ,@Validated @ModelAttribute("usuario") UsuarioRegistroDTO usuarioDTO, BindingResult bindingResult, RedirectAttributes redirect, Model modelo) {
		
		Usuario usuarioExistente = userService.buscarXid(id);
		if(bindingResult.hasErrors()) {
			modelo.addAttribute("usuario", usuarioDTO);
			return "usuario/usuario-editar";
		}
		usuarioExistente.setId(id);
		usuarioExistente.setNombre(usuarioDTO.getNombre());
		usuarioExistente.setApellido(usuarioDTO.getApellido());
		usuarioExistente.setEmail(usuarioDTO.getEmail());
		usuarioExistente.setUsername(usuarioDTO.getUsername());
		//usuarioExistente.setRoles(usuarioDTO.getRoles());
		
		userService.actualizar(usuarioExistente);
		redirect.addFlashAttribute("msgExito", "El usuario se ha modificado correctamente");
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
