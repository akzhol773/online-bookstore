package com.neobis.bookshop.mapper;


import com.neobis.bookshop.dtos.BookDto;
import com.neobis.bookshop.entities.Book;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookMapper {
    private final ModelMapper modelMapper;

    public BookDto convertToDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    public Book convertToEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }

    public List<BookDto> convertToDtoList(List<Book> books) {
        return books.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<Book> convertToEntityList(List<BookDto> bookDtos) {
        return bookDtos.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}
