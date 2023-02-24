package com.taller.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.taller.entidades.Empresa;

public interface EmpresaServicie {
	
	public List<Empresa> listaEmpresa();
	
	public Page<Empresa> listaEmpresa(Pageable pageable);
	
	public void guardar(Empresa empresa);
	
	public Empresa buscarXid(Long id);
	
	public void actualizar(Empresa empresa);
	
	public void eliminar(Long id);

}
