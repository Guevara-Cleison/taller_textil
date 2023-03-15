package com.taller.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taller.entidades.DTO.UsuarioRegistroDTO;
import com.taller.servicios.UsuarioService;

@Controller
@RequestMapping(value = "/registro")
public class RegistroController {
	
	@Autowired
	private UsuarioService userService;
	
	@ModelAttribute("usuario")
	public UsuarioRegistroDTO retornarNuevoUsuarioRegistroDTO() {
		return new UsuarioRegistroDTO();
	}
	
	@GetMapping
	public String mostrarFormularoRegistro() {
		return "acceso/registro";
	}
	
	@PostMapping
	public String crearRegistroUsuario(@ModelAttribute("usuario") UsuarioRegistroDTO registroDTO) {
		
		userService.guardar(registroDTO);
		
		return "redirect:/registro?exito";
	}

}
