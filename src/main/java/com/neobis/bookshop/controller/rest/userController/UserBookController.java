package com.neobis.bookshop.controller.rest.userController;

import com.neobis.bookshop.dtos.BookDto;
import com.neobis.bookshop.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Books", description = "Public endpoints for all users to query products")
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class UserBookController {
    private final BookService bookService;

    @Operation(
            summary = "Get all books",
            description = "Returns List of book dto from database"
    )
    @ApiResponse(responseCode = "200", description = "Success")

    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }


    @Operation(
            summary = "Get books by title and author name"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
    })
    @Parameter(name = "query", description = "Name author or title of the book")
    @GetMapping("/search-by-title-and-author")
    public ResponseEntity<List<BookDto>>  searchBooks(@RequestParam(value = "query") String query) {
        return ResponseEntity.ok(bookService.searchBooks(query));
    }

    @Operation(
            summary = "Get books by genre"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
    })
    @Parameter(name = "query", description = "Genre")
    @GetMapping("/search-by-genre")
    public ResponseEntity<List<BookDto>>  searchBooksByGenre(@RequestParam(value = "query") String query) {
        return ResponseEntity.ok(bookService.searchBooksByGenre(query));
    }
}
