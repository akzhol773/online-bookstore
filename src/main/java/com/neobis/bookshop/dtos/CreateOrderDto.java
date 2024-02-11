package com.neobis.bookshop.dtos;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class CreateOrderDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<OrderItemDto> items = new ArrayList<>();

}
