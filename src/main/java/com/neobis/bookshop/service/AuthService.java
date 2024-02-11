package com.neobis.bookshop.service;



import com.neobis.bookshop.config.CustomUserDetails;
import com.neobis.bookshop.dtos.JwtRequestDto;
import com.neobis.bookshop.dtos.JwtResponseDto;
import com.neobis.bookshop.dtos.UserDto;
import com.neobis.bookshop.dtos.UserRequestDto;
import com.neobis.bookshop.entities.User;
import com.neobis.bookshop.exceptions.InvalidCredentialException;
import com.neobis.bookshop.exceptions.UsernameAlreadyTakenException;
import com.neobis.bookshop.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetails customUserDetails;

    public ResponseEntity<JwtResponseDto> authentication(JwtRequestDto authRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new InvalidCredentialException("Invalid username or password");

        }
        UserDetails userDetails = customUserDetails.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDto(token));
    }

    public ResponseEntity<UserDto> createNewUser(UserRequestDto registrationUserDto){

        if(userService.findByUsername(registrationUserDto.getUsername()).isPresent()){
            throw new UsernameAlreadyTakenException("Username is already taken. Please, try to use another one.");
        }

        User user =   userService.registerCustomer(registrationUserDto);
        return  ResponseEntity.ok(new UserDto(user.getFirstName(), user.getLastName()));


    }

}