package com.taller.repositorios;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.taller.entidades.Empresa;

public interface EmpresaRepository extends PagingAndSortingRepository<Empresa, Long>{

}
