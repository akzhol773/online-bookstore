package com.neobis.bookshop.controller.rest;


import com.neobis.bookshop.dtos.JwtRequestDto;
import com.neobis.bookshop.dtos.JwtResponseDto;
import com.neobis.bookshop.dtos.UserDto;
import com.neobis.bookshop.dtos.UserRequestDto;
import com.neobis.bookshop.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Authentication", description = "Public entry point of the application")
@ApiResponse(responseCode = "403", description = "Only anonymous users have access", content = @Content)
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "Login",
            description = "Endpoint for customer to register a new account. Requires a body"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Customer successfully registered"),
            @ApiResponse(responseCode = "409", description = "The email provided is already taken", content = @Content)
    })
    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponseDto> login(@RequestBody JwtRequestDto authRequest){
        return authService.authentication(authRequest);
    }

    @Operation(
            summary = "Registration",
            description = "Endpoint for getting tokens after login"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully returned a token"),
            @ApiResponse(responseCode = "403", description = "Username or password is invalid", content = @Content)
    })

    @PostMapping("/authorize")
    public ResponseEntity<UserDto> createNewUser(@RequestBody UserRequestDto registrationUserDto){
        return   authService.createNewUser(registrationUserDto);}

}
