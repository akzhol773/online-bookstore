package com.neobis.bookshop.mapper;


import com.neobis.bookshop.dtos.AccountDto;
import com.neobis.bookshop.dtos.UserRequestDto;
import com.neobis.bookshop.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {
    private final ModelMapper modelMapper;
    @Autowired
    public AccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AccountDto convertToDto(User user) {
        return modelMapper.map(user, AccountDto.class);
    }

    public User convertToEntity(AccountDto accountDto) {
        return modelMapper.map(accountDto, User.class);
    }
    public User convertToEntity(UserRequestDto userRequestDto) {
        return modelMapper.map(userRequestDto, User.class);
    }

    public List<AccountDto> convertToDtoList(List<User> users) {
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public List<User> convertToEntityList(List<AccountDto> accountDtos) {
        return accountDtos.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}
