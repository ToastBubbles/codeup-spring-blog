package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    UserRepository userDAO;

    public UserController(UserRepository userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/register")
    public String registerUserGetForm(){

        return "user/register";
    }
    @PostMapping("/register")
    public String registerUser(@RequestParam(name="username") String username, @RequestParam(name="email") String email, @RequestParam(name="password") String password){
        User user = new User(username,email,password);
        userDAO.save(user);
        return "redirect:/posts";
    }
}
