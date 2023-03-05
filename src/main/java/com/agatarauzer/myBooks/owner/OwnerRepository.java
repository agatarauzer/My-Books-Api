package com.agatarauzer.myBooks.owner;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Long> {
	public Optional<Owner> findById(Long id);
}