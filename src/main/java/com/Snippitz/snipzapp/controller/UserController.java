package com.Snippitz.snipzapp.controller;

import com.Snippitz.snipzapp.entity.ConnectionMessage;
import com.Snippitz.snipzapp.entity.SnipUser;

import com.Snippitz.snipzapp.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/users/register")
    public String registerUser(@RequestBody SnipUser user){
        System.out.println(user);
        userService.registerUser(user);
        return "success";
    }

    @PostMapping("/api/users/login")
    public ConnectionMessage loginUser(@RequestBody SnipUser user){


       return userService.login(user);
    }

}
