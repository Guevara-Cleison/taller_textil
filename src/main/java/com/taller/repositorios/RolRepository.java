package com.taller.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.taller.entidades.Role;

@Repository
public interface RolRepository extends CrudRepository<Role, Long>{

}
