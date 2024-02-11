package com.neobis.bookshop.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neobis.bookshop.enums.BookStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String title;
    private String author;
    @Column(unique = true, nullable = false)
    private String isbn;
    private String publisher;
    private LocalDate publicationYear;
    private String bookGenre;
    private BigDecimal price;
    private Integer stockQuantity;
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    private LocalDateTime createdOn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    private boolean isDeleted;
}
