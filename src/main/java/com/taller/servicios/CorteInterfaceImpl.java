package com.taller.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taller.entidades.Corte;
import com.taller.repositorios.CorteRepository;

@Service
public class CorteInterfaceImpl implements CorteService{

	@Autowired
	private CorteRepository corterepo;
	
	@Override
	@Transactional(readOnly = true)
	public List<Corte> listaCorte() {
		return (List<Corte>) corterepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Corte> listaCorte(Pageable pageable) {
		return corterepo.findAll(pageable);
	}

	@Override
	@Transactional
	public void guardar(Corte corte) {
		corterepo.save(corte);
	}

	@Override
	@Transactional(readOnly = true)
	public Corte buscarXid(int id) {
		return corterepo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void actualizar(Corte corte) {
		corterepo.save(corte);
	}

	@Override
	@Transactional
	public void eliminar(int id) {
		corterepo.deleteById(id);
	}

}
