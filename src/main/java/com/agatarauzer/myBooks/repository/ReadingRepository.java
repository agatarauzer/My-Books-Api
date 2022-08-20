package com.agatarauzer.myBooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.entity.Reading;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, Long> {

}
