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

import com.taller.entidades.Corte;
import com.taller.servicios.CorteService;
import com.taller.servicios.EmpresaServicie;
import com.taller.util.paginacion.PageRender;

@Controller
@RequestMapping("/corte")
public class CorteController {
	
	@Autowired
	private CorteService corteService;
	
	@Autowired
	private EmpresaServicie empresaService;
	
	
	@GetMapping({"/listar",""})
	public String listarCorte(@RequestParam(name = "page", defaultValue = "0")int page,Model modelo) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Corte> cortes = corteService.listaCorte(pageRequest);
		PageRender<Corte> pageRender = new PageRender<>("/corte/listar", cortes);
		
		modelo.addAttribute("cortes", cortes);
		modelo.addAttribute("page", pageRender);
		
		return "corte/corte-lista";
	}
	
	@GetMapping("/nuevo")
	public String mostrarFormularoRegistroCorte(Model modelo) {
		Corte corte = new Corte();
		modelo.addAttribute("corte", corte);
		modelo.addAttribute("empresas", empresaService.listaEmpresa());
		return "corte/corte-nuevo";
	}
	
	@PostMapping("/guardar")
	public String guardarCorte(@Validated @ModelAttribute("corte") Corte corte, BindingResult bindingResult, RedirectAttributes redirect, Model modelo) {
		if(bindingResult.hasErrors()) {
			modelo.addAttribute("corte", corte);
			return "corte/corte-nuevo";
		}
		corteService.guardar(corte);
		redirect.addFlashAttribute("msgExito", "El corte se ha agregado correctamente");
		return "redirect:/corte/listar";
	}
	
	@GetMapping("/editar/{id}") 
	public String mostrarFormularoEditarCorte(@PathVariable(value = "id") int id, Model modelo) {
		modelo.addAttribute("corte", corteService.buscarXid(id));
		modelo.addAttribute("empresas", empresaService.listaEmpresa());
		return "corte/corte-editar";
	}
	
	@PostMapping("/guardar/{id}")
	public String actualizarCorte(@PathVariable(value = "id") int id ,@Validated @ModelAttribute("corte") Corte corte, BindingResult bindingResult, RedirectAttributes redirect, Model modelo) {
		Corte corteExistente = corteService.buscarXid(id);
		if(bindingResult.hasErrors()) {
			modelo.addAttribute("corte", corte);
			return "corte/corte-editar";
		}
		corteExistente.setIdCorte(id);
		corteExistente.setIdEmpresa(corte.getIdEmpresa());
		corteExistente.setOp(corte.getOp());
		corteExistente.setOs(corte.getOs());
		corteExistente.setModelo(corte.getModelo());
		corteExistente.setCantidad(corte.getCantidad());
		corteExistente.setPrecio(corte.getPrecio());
		corteExistente.setNumGui(corte.getNumGui());
		corteExistente.setFecEntrega(corte.getFecEntrega());
		
		corteService.actualizar(corteExistente);
		redirect.addFlashAttribute("msgExito", "El corte se ha modificado correctamente");
		return "redirect:/corte/listar";
	}
	
	@PostMapping("eliminar/{id}")
	public String eliminarEmpresa(@PathVariable(value = "id") int id, RedirectAttributes redirect) {
		if(id > 0) {
			corteService.eliminar(id);
			redirect.addFlashAttribute("msgExito", "El corte se ha eliminado correctamente");
		}
		return "redirect:/corte/listar";
	}

}
