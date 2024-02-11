package com.neobis.bookshop.controller.rest.adminController;


import com.neobis.bookshop.dtos.BookDto;
import com.neobis.bookshop.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(
        name = "Admin Book",
        description = "Admin control panel for books. ADMIN has full access")
@ApiResponses({
        @ApiResponse(responseCode = "401", description = "If user has not signed in", content = @Content),
        @ApiResponse(responseCode = "403", description = "If signed user has role CUSTOMER", content = @Content)
})
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/books")
public class AdminBookController {
    private final BookService bookService;
    @Operation(
            summary = "Create book",
            description = "Creates a new book object if does not exist in used " +
                    "database or refills amount of existing book in db"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "New Beer object created successfully " +
                    "or stored amount is fulfilled"),
            @ApiResponse(responseCode = "403", description = "Only ADMIN users has access for POST method")
    })
    @PostMapping("/create")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok().body(bookService.addBook(bookDto));
    }

    @Operation(
            summary = "Retrieve all books",
            description = "Get all books dto in list from database"
    )
    @ApiResponse(responseCode = "200", description = "Success")

    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }


    @Operation(
            summary = "Retrieve a Book by id",
            description = "Get one book dto from database specifying it's id. Response is a Book dto with full info"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Book with id: {bookId} not found.", content = @Content)
    })
    @Parameter(name = "bookId", description = "Unique book object identifier")
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookDtoById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookDtoById(id));
    }


    @Operation(
            summary = "Update book object",
            description = "Update Book info by id specified in RequestBody"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book object info successfully updated"),
            @ApiResponse(responseCode = "403", description = "Only ADMIN users has access for PUT method", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book you are trying to update not found.", content = @Content)
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto, @PathVariable Long id) {
        return ResponseEntity.ok(bookService.updateBook(bookDto, id));
    }


    @Operation(
            summary = "Delete Book object",
            description = "Only changes the status of the Book object to delete"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully removed"),
            @ApiResponse(responseCode = "403", description = "Only ADMIN users has access for DELETE method"),
            @ApiResponse(responseCode = "404", description = "Beer you are trying to delete not found.", content = @Content)
    })
    @Parameter(name = "beerId", description = "Unique beer object identifier")
    @PutMapping("/delete/{id}")
    public ResponseEntity<BookDto> delete(@PathVariable Long id) {
        return ResponseEntity.ok().body(bookService.deleteById(id));
    }



}
