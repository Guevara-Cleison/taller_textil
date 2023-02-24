package com.taller.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.taller.entidades.Corte;

public interface CorteService {
	
public List<Corte> listaCorte();
	
	public Page<Corte> listaCorte(Pageable pageable);
	
	public void guardar(Corte corte);
	
	public Corte buscarXid(int id);
	
	public void actualizar(Corte corte);
	
	public void eliminar(int id);

}
