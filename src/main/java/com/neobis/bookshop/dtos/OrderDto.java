package com.neobis.bookshop.dtos;


import com.neobis.bookshop.enums.OrderStatus;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.HashMap;


@Data
public class OrderDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private LocalDateTime orderDate;
    private   BigDecimal totalPrice;
    private OrderStatus status;
    private String orderedPerson;
    private String deliveryAddress;
    HashMap<String, String> orderedBooks = new HashMap<String, String>();


}
