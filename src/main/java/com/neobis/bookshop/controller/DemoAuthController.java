package com.neobis.bookshop.controller;


import com.neobis.bookshop.dtos.UserRequestDto;
import com.neobis.bookshop.entities.User;
import com.neobis.bookshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DemoAuthController {
    private final UserService userService;

    @Autowired
    public DemoAuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String showRegisterForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "registration-form";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "access-denied";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRequestDto dto, BindingResult result, Model model) {
        if (userService.findByUsername(dto.getUsername()).isPresent()) {
            result.rejectValue("username", "dto.username", "Username already in use");
            return "registration-form";
        }

        // Proceed with registration if validation passes
        if (!result.hasErrors()) {
            userService.registerCustomer(dto);
            model.addAttribute("success", true);
            return "redirect:/login";
        }

        return "registration-form";
    }





}