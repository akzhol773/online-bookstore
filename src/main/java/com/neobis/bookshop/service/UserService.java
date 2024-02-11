package com.neobis.bookshop.service;



import com.neobis.bookshop.dtos.AccountDto;
import com.neobis.bookshop.dtos.UserRequestDto;
import com.neobis.bookshop.entities.Role;
import com.neobis.bookshop.entities.User;
import com.neobis.bookshop.enums.AccountStatus;
import com.neobis.bookshop.exceptions.AccountDoesNotExistException;
import com.neobis.bookshop.exceptions.UsernameAlreadyTakenException;
import com.neobis.bookshop.mapper.AccountMapper;
import com.neobis.bookshop.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;
    private final RoleService roleService;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AccountMapper accountMapper, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountMapper = accountMapper;
        this.roleService = roleService;
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }


    public AccountDto getCustomerDtoById(Long userId) {
        User user =  userRepository.findActiveCustomersById(userId)
                .orElseThrow(() -> new AccountDoesNotExistException("User with id: " + userId + " not found."));
        return accountMapper.convertToDto(user);
    }


    public User getCustomerById(Long userId) {
        return userRepository.findActiveCustomersById(userId)
                .orElseThrow(() -> new AccountDoesNotExistException("User with id: " + userId + " not found."));
    }






    public void deleteCustomerById(Long userId) {
        User user =  userRepository.findActiveCustomersById(userId)
                .orElseThrow(() -> new AccountDoesNotExistException("User with id: " + userId + " not found."));
        user.setAccountStatus(AccountStatus.DELETED);
        userRepository.save(user);
    }

    public List<AccountDto> getAllActiveCustomers() {
        List<User> users = userRepository.findAllActiveCustomers();
        return accountMapper.convertToDtoList(users);
    }



    @Transactional
    public User registerCustomer(UserRequestDto dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UsernameAlreadyTakenException("User already exists with username: " + dto.getUsername());
        }

        User user = accountMapper.convertToEntity(dto);
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Role userRole = roleService.getRole()
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        user.setRoles(Collections.singletonList(userRole));
        userRepository.save(user);
        return user;
    }

    public Long getUserIdFromAuthToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return getUserIdByUsername(userDetails.getUsername());
    }

    public User findCustomerByEmail(String email) {
        return userRepository.findActiveCustomerByEmail(email).orElseThrow(()->new UsernameNotFoundException("Username not found"));
    }

    public Long getUserIdByUsername(String username) {
        return userRepository.findUserIdByUsername(username).orElseThrow(()->new UsernameNotFoundException("Username not found"));
    }
}