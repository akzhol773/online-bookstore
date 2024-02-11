package com.neobis.bookshop.repository;


import com.neobis.bookshop.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT a FROM Book a WHERE a.isDeleted = false ")
    List<Book> findAllActiveBooks();


    @Query("SELECT c FROM Book c WHERE c.title LIKE CONCAT('%', :query, '%') OR c.author LIKE CONCAT('%', :query, '%')")
    List<Book> findByTitleOrAuthorContaining(@Param("query") String query);



    @Query("SELECT c FROM Book c WHERE c.bookGenre LIKE CONCAT('%', :genre, '%')")
    List<Book> searchBooksByGenre(@Param("genre") String genre);


    Optional<Book> findBookByIsbn(String isbn);


    @Query("SELECT a FROM Book a WHERE a.id = :id AND a.isDeleted = false ")
    Optional<Book> findActiveBookById(@Param("id") Long id);
}
