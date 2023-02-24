package com.taller.repositorios;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.taller.entidades.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>{
	
	public Optional<User> findByUsername(String username);

}
