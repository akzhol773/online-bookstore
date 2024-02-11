package com.neobis.bookshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class JwtResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
