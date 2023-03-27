package com.codeup.codeupspringblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class sayHello {
    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }
}
