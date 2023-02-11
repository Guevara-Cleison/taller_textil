package com.taller.controlador;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;
import com.taller.entidades.Empresa;
import com.taller.servicios.EmpresaServicie;
import com.taller.util.paginacion.PageRender;
import com.taller.util.reportes.EmpresaExporterExcel;
import com.taller.util.reportes.EmpresaExporterPDF;

@Controller
public class EmpresaController {
	
	@Autowired
	private EmpresaServicie empresaService;
	
	@GetMapping({"/","/listar",""})
	public String listarEmpresa(@RequestParam(name = "page", defaultValue = "0")int page,Model modelo) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Empresa> empresas = empresaService.findAll(pageRequest);
		PageRender<Empresa> pageRender = new PageRender<>("/listar", empresas);
		
		modelo.addAttribute("titulo", "Listado de Empresas");
		modelo.addAttribute("empresas", empresas);
		modelo.addAttribute("page", pageRender);
		
		return "listarEmpresa";
	}
	
	@GetMapping({"/form"})
	public String mostrarFormularoRegistroEmpresa(Map<String, Object> modelo) {
		Empresa empresa = new Empresa();
		modelo.put("empresa", empresa);
		modelo.put("titulo", "Registro de empsesas");
		return "RegistrarEmpresa";
	}
	
	@PostMapping("/form")
	public String guardarEmpresa(@Valid Empresa empresa, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de empresa");
			return "RegistrarEmpresa";
		}
		
		String mensaje = (empresa.getIdEmpresa() != null) ? "La empresa ha sido editado con exito" : "Empresa registrada con exito";
		empresaService.save(empresa);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/listar";
	}
	
	@GetMapping("/form/{id}")
	public String editarEmpresa(@PathVariable(value = "id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
		Empresa empresa = null;
		if(id > 0) {
			empresa = empresaService.findOne(id);
			if(empresa == null) {
				flash.addFlashAttribute("error", "La empresa no existe en la base de datos");
				return "redirect:/listar";
			}
		}else {
			flash.addAttribute("error", "El ID de la empresa no puede ser cero");
		}
		
		modelo.put("empresa", empresa);
		modelo.put("titulo", "Edicion de empresa");
		return "RegistrarEmpresa";
	}
	
	@GetMapping("eliminar/{id}")
	public String eliminarEmpresa(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if(id > 0) {
			empresaService.delete(id);
			flash.addFlashAttribute("succes", "Empresa eliminada con exito");
		}
		return "redirect:/listar";
	}
	
	//exportar en pdf
	
	@GetMapping("/exportarPDF")
	public void exportarListadoDeEmpresasEnPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("aplication/pdf");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormat.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empresa_"+ fechaActual+".pdf";
		
		response.setHeader(cabecera, valor);
		
		List<Empresa> empresas = empresaService.findAll();
		
		EmpresaExporterPDF exporter = new EmpresaExporterPDF(empresas);
		exporter.exportar(response);
		
	}
	
	//exportar_en_excel
	
	@GetMapping("/exportarExcel")
	public void exportarListadoDeEmpresasEnExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("aplication/octec-stream");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormat.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empresa_"+ fechaActual+".xlsx";
		
		response.setHeader(cabecera, valor);
		
		List<Empresa> empresas = empresaService.findAll();
		
		EmpresaExporterExcel exporter = new EmpresaExporterExcel(empresas);
		exporter.exportar(response);
		
	}

}
