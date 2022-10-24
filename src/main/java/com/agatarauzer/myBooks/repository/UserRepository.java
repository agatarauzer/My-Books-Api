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
	
	@Query(value = "SELECT cast(registration_date as CHAR(7)) as REG_MTH, count(1) as SUM "
			+ "FROM (select * from USERS WHERE registration_date is not null) U2 GROUP BY REG_MTH "
			+ "order by REG_MTH desc", nativeQuery = true)
	Map<String, Long> findByRegistrationMonth();

}

