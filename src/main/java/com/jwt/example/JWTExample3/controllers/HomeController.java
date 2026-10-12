package com.jwt.example.JWTExample3.controllers;

import com.jwt.example.JWTExample3.model.User;
import com.jwt.example.JWTExample3.services.UserServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final UserServices userServices;

    public HomeController(UserServices userServices) {
        this.userServices = userServices;
    }

    // http://localhost:8080/home/users
    @GetMapping("/users")
    public List<User> getUsers() {
        return userServices.getUsers();
    }
//http://localhost:8081/home/whoIam
    @GetMapping("/whoIam")
    public String getLoggedInUser(Principal principal)
    {
        return principal.getName();
    }
}