package com.codeup.codeupspringblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
class HelloController {

    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "This is a londing page";
    }
}
