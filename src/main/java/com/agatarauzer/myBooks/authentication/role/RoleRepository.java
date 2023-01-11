package com.agatarauzer.myBooks.authentication.role;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	public Optional<Role> findByName(ERole name);
}
