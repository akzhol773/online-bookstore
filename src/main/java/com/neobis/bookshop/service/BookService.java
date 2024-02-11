package com.neobis.bookshop.service;


import com.neobis.bookshop.dtos.BookDto;
import com.neobis.bookshop.entities.Book;
import com.neobis.bookshop.enums.BookStatus;
import com.neobis.bookshop.exceptions.BookDoesNotExistException;
import com.neobis.bookshop.mapper.BookMapper;
import com.neobis.bookshop.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;


    public BookDto addBook(BookDto bookDto) {
        Book book = bookMapper.convertToEntity(bookDto);
        book.setStockQuantity(bookDto.getStockQuantity());
        if (book.getStockQuantity() > 0) {
            book.setBookStatus(BookStatus.IN_STOCK);
        } else {
            book.setBookStatus(BookStatus.OUT_OF_STOCK);
        }
        Optional<Book> bookOpt = bookRepository.findBookByIsbn(book.getIsbn());
        if(bookOpt.isPresent()){
            Book existBook = bookOpt.get();
            int updatedStock = existBook.getStockQuantity() + bookDto.getStockQuantity();
            existBook.setStockQuantity(updatedStock);
            return bookMapper.convertToDto(bookRepository.save(existBook));
        } else {

            return bookMapper.convertToDto(bookRepository.save(book));
        }
    }


    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAllActiveBooks();
        return bookMapper.convertToDtoList(books);
    }


    public BookDto getBookDtoById(Long id) {
        Book book =  bookRepository.findActiveBookById(id)
                .orElseThrow(() -> new BookDoesNotExistException("Book with id: " + id + " not found."));
        return bookMapper.convertToDto(book);
    }


    public Book getBookById(Long id) {
        return bookRepository.findActiveBookById(id)
                .orElseThrow(() -> new BookDoesNotExistException("Book with id: " + id + " not found."));
    }


    public BookDto deleteById(Long id) {
        Book book =  bookRepository.findById(id)
                .orElseThrow(() -> new BookDoesNotExistException("Book with id: " + id + " not found."));
        book.setBookStatus(BookStatus.OUT_OF_STOCK);
        book.setDeleted(true);
        bookRepository.save(book);
        return bookMapper.convertToDto(book);
    }


    public BookDto updateBook(BookDto updateRequest, Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookDoesNotExistException("Book does not exist"));
        if (updateRequest.getTitle() != null) book.setTitle(updateRequest.getTitle());
        if (updateRequest.getAuthor() != null) book.setAuthor(updateRequest.getAuthor());
        if (updateRequest.getIsbn() != null) book.setIsbn(updateRequest.getIsbn());
        if (updateRequest.getPublisher() != null) book.setPublisher(updateRequest.getPublisher());
        if (updateRequest.getPublicationYear() != null) book.setPublicationYear(updateRequest.getPublicationYear());
        if (updateRequest.getBookGenre() != null) book.setBookGenre(updateRequest.getBookGenre());
        if (updateRequest.getPrice() != null) book.setPrice(updateRequest.getPrice());
        if (updateRequest.getStockQuantity() != null) {
            book.setStockQuantity(updateRequest.getStockQuantity());
        }
        book.setBookStatus(book.getStockQuantity() > 0 ? BookStatus.IN_STOCK : BookStatus.OUT_OF_STOCK);
        Book updatedBook = bookRepository.save(book);
        return bookMapper.convertToDto(updatedBook);
    }

    public List<BookDto> searchBooks(String query) {
        List<Book> books = bookRepository.findByTitleOrAuthorContaining(query);
        return bookMapper.convertToDtoList(books);
    }

    public List<BookDto> searchBooksByGenre(String query) {
        List<Book> books = bookRepository.searchBooksByGenre(query);
        return bookMapper.convertToDtoList(books);
    }
}