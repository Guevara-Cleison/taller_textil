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

import com.taller.entidades.Empresa;
import com.taller.servicios.EmpresaServicie;
import com.taller.util.paginacion.PageRender;

@Controller
@RequestMapping(value = "/empresa")
public class EmpresaController {
	
	@Autowired
	private EmpresaServicie empresaService;
	
	@GetMapping({"/listar",""})
	public String listarEmpresa(@RequestParam(name = "page", defaultValue = "0")int page,Model modelo) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Empresa> empresas = empresaService.listaEmpresa(pageRequest);
		PageRender<Empresa> pageRender = new PageRender<>("/empresa/listar", empresas);
		
		modelo.addAttribute("empresas", empresas);
		modelo.addAttribute("page", pageRender);
		
		return "empresa/empresa-lista";
	}
	
	@GetMapping("/nuevo")
	public String mostrarFormularoRegistroEmpresa(Model modelo) {
		Empresa empresa = new Empresa();
		modelo.addAttribute("empresa", empresa);
		return "empresa/empresa-nuevo";
	}
	
	@PostMapping("/guardar")
	public String guardarEmpresa(@Validated @ModelAttribute("empresa") Empresa empresa, BindingResult bindingResult, RedirectAttributes redirect, Model modelo) {
		if(bindingResult.hasErrors()) {
			modelo.addAttribute("empresa", empresa); 
			return "empresa/empresa-nuevo";
		}
		empresaService.guardar(empresa);
		redirect.addFlashAttribute("msgExito", "La empresa se ha agregado correctamente");
		return "redirect:/empresa/listar";
	}
	
	@GetMapping("/editar/{id}") 
	public String mostrarFormularoEditarEmpresa(@PathVariable(value = "id") Long id, Model modelo) {
		modelo.addAttribute("empresa",empresaService.buscarXid(id));
		return "empresa/empresa-editar";
	}
	
	@PostMapping("/guardar/{id}")
	public String actualizarEmpresa(@PathVariable(value = "id") Long id ,@Validated @ModelAttribute("empresa") Empresa empresa, BindingResult bindingResult, RedirectAttributes redirect, Model modelo) {
		Empresa empresaExistente = empresaService.buscarXid(id);
		if(bindingResult.hasErrors()) {
			modelo.addAttribute("empresa", empresa); 
			return "empresa/empresa-editar";
		}
		empresaExistente.setIdEmpresa(id);
		empresaExistente.setDescripcion(empresa.getDescripcion());
		empresaExistente.setEmail(empresa.getEmail());
		empresaExistente.setDireccion(empresa.getDireccion());
		
		empresaService.actualizar(empresaExistente);
		redirect.addFlashAttribute("msgExito", "La empresa se ha modificado correctamente");
		return "redirect:/empresa/listar";
	}
	
	@PostMapping("eliminar/{id}")
	public String eliminarEmpresa(@PathVariable(value = "id") Long id, RedirectAttributes redirect) {
		if(id > 0) {
			empresaService.eliminar(id);
			redirect.addFlashAttribute("msgExito", "La empresa se ha eliminado correctamente");
		}
		return "redirect:/empresa/listar";
	}
	

}
