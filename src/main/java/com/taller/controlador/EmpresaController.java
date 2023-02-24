package com.taller.controlador;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import com.lowagie.text.DocumentException;
import com.taller.entidades.Empresa;
import com.taller.servicios.EmpresaServicie;
import com.taller.util.paginacion.PageRender;
import com.taller.util.reportes.EmpresaExporterExcel;
import com.taller.util.reportes.EmpresaExporterPDF;

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
	public String guardarEmpresa(@Valid @ModelAttribute("empresa") Empresa empresa) {
		empresaService.guardar(empresa);
		return "redirect:/empresa/listar";
	}
	
	@GetMapping("/editar/{id}") 
	public String mostrarFormularoEditarEmpresa(@PathVariable(value = "id") Long id, Model modelo) {
		modelo.addAttribute("empresa",empresaService.buscarXid(id));
		return "empresa/empresa-editar";
	}
	
	@PostMapping("/guardar/{id}")
	public String actualizarEmpresa(@PathVariable(value = "id") Long id ,@Valid @ModelAttribute("empresa") Empresa empresa) {
		Empresa empresaExistente = empresaService.buscarXid(id);
		empresaExistente.setIdEmpresa(id);
		empresaExistente.setDescripcion(empresa.getDescripcion());
		empresaExistente.setEmail(empresa.getEmail());
		empresaExistente.setDireccion(empresa.getDireccion());
		
		empresaService.actualizar(empresaExistente);
		return "redirect:/empresa/listar";
	}
	
	@GetMapping("eliminar/{id}")
	public String eliminarEmpresa(@PathVariable(value = "id") Long id) {
		if(id > 0) {
			empresaService.eliminar(id);
		}
		return "redirect:/empresa/listar";
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
		
		List<Empresa> empresas = empresaService.listaEmpresa();
		
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
		
		List<Empresa> empresas = empresaService.listaEmpresa();
		
		EmpresaExporterExcel exporter = new EmpresaExporterExcel(empresas);
		exporter.exportar(response);
		
	}

}
