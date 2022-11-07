package com.agatarauzer.myBooks.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.domain.Role;
import com.agatarauzer.myBooks.domain.enums.ERole;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	public Optional<Role> findByName(ERole name);
}
