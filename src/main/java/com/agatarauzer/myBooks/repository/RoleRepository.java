package com.agatarauzer.myBooks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.domain.ERole;
import com.agatarauzer.myBooks.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	public Role findByName(ERole name);
}
