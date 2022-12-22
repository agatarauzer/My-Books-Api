package com.agatarauzer.myBooks.security.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.security.ERole;
import com.agatarauzer.myBooks.security.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	public Optional<Role> findByName(ERole name);
}
