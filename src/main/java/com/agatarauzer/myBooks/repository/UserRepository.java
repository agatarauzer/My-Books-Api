package com.agatarauzer.myBooks.repository;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	
	@Query(value = "SELECT cast(registration_date as CHAR(7)) as Month, count(1) as Sum "
			+ "FROM (select * from USERS WHERE registration_date IS NOT NULL) U2 GROUP BY Month "
			+ "order by Month desc", nativeQuery = true)
	Map<String, Long> findByRegistrationMonth();

	@Query(value = "SELECT cast(registration_date as CHAR(7)) as Month, count(1) as Role"
			+ "FROM (SELECT * FROM users WHERE registration_date IS NOT NULL) u "
			+ "JOIN user_roles ur ON u.user_id = ur.user_id "
			+ "JOIN roles r ON ur.role_id = r.role_id"
			+ "WHERE r.name = :role"
			+ "GROUP BY Month"
			+ "ORDER BY Month desc", nativeQuery = true)
	Map<String, Long> findByRegistrationMonthAndRole(String role);
}

