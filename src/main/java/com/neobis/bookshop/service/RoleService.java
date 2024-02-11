package com.neobis.bookshop.service;

import com.neobis.bookshop.entities.Role;
import com.neobis.bookshop.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public Optional<Role> getRole (){

        return roleRepository.findByName("ROLE_USER");
    }
}
