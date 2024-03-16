package com.example.service;

import com.example.model.dtos.Request.UserRequestDto;
import com.example.model.dtos.Response.UserLoginResponseDto;

public interface UserService {
    void registerUser(UserRequestDto userRequestDto);

    UserLoginResponseDto loginUser(String email, String password);

    void deleteUser(String id);
}
