package com.neobis.bookshop.dtos;
import com.neobis.bookshop.enums.AccountStatus;
import lombok.Data;


import java.io.Serial;
import java.io.Serializable;
@Data
public class AccountDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String address;
    private AccountStatus accountStatus;
}
