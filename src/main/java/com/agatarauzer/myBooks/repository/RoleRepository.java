package com.agatarauzer.myBooks.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.domain.ERole;
import com.agatarauzer.myBooks.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	public Optional<Role> findByName(ERole name);
}
