package com.taller.repositorios;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.taller.entidades.Empresa;

@Repository
public interface EmpresaRepository extends PagingAndSortingRepository<Empresa, Long>{

}
