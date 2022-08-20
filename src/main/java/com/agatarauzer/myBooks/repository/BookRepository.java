package com.agatarauzer.myBooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
