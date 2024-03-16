package com.example.controllers;

import com.example.model.dtos.Request.UserRequestDto;
import com.example.model.dtos.Response.UserLoginResponseDto;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void registerUser(@RequestBody final UserRequestDto dto) {
        userService.registerUser(dto);
    }


    @PostMapping(value = "/login/{email}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public UserLoginResponseDto loginUser(@PathVariable("email") final String email, @PathVariable("password") final String password) {
        return userService.loginUser(email, password);
    }
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUserById(@PathVariable("id") final String id) {
        userService.deleteUser(id);
    }

}
