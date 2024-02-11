package com.neobis.bookshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DemoController {

    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }

}
