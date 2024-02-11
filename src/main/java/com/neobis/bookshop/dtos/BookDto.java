package com.neobis.bookshop.dtos;


import com.neobis.bookshop.enums.BookStatus;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto  implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private LocalDate publicationYear;
    private String bookGenre;
    private BigDecimal price;
    private Integer stockQuantity;
    private BookStatus bookStatus;
    private boolean isDeleted;
}
