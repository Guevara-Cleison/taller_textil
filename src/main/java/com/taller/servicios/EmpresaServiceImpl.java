package com.taller.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taller.entidades.Empresa;
import com.taller.repositorios.EmpresaRepository;

@Service
public class EmpresaServiceImpl implements EmpresaServicie{
	
	@Autowired
	private EmpresaRepository empresarepo;

	@Override
	@Transactional(readOnly = true)
	public List<Empresa> listaEmpresa() {
		return (List<Empresa>) empresarepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Empresa> listaEmpresa(Pageable pageable) {
		return empresarepo.findAll(pageable);
	}

	@Override
	@Transactional
	public void guardar(Empresa empresa) {
		empresarepo.save(empresa);
	}

	@Override
	@Transactional(readOnly = true)
	public Empresa buscarXid(Long id) {
		return empresarepo.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void actualizar(Empresa empresa) {
		empresarepo.save(empresa);
	}
	
	@Override
	@Transactional
	public void eliminar(Long id) {
		empresarepo.deleteById(id);
	}

}
