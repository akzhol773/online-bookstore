package com.neobis.bookshop.dtos;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    private Long bookId;
    private Integer quantity;
}
