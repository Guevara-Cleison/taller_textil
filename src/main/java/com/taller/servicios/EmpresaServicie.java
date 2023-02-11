package com.taller.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.taller.entidades.Empresa;

public interface EmpresaServicie {
	
	public List<Empresa> findAll();
	
	public Page<Empresa> findAll(Pageable pageable);
	
	public void save(Empresa empresa);
	
	public Empresa findOne(Long id);
	
	public void delete(Long id);

}
