package com.taller.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

	// ******INICIO DE SESION****************************

	@GetMapping("/login")
	public String mostrarLogin() {
		return "acceso/login";
	}

	// *********************************************************
	
	@GetMapping("/")
	public String mostrarIndex() {
		return "index";
	}

}
