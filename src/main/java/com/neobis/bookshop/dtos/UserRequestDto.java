package com.neobis.bookshop.dtos;

import lombok.Data;

@Data
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    private String emailAddress;
    private String address;
}