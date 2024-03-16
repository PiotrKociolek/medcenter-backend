package com.example.service.impl;

import com.example.components.PasswordEncoder;
import com.example.exception.PasswordDoesNotMatchException;
import com.example.exception.UserAlreadyExistException;
import com.example.exception.UserNotFoundException;
import com.example.model.dtos.Request.UserRequestDto;
import com.example.model.dtos.Response.UserJWT;
import com.example.model.dtos.Response.UserLoginResponseDto;
import com.example.repository.UserRepository;
import com.example.repository.entity.UserEntity;
import com.example.service.UserService;
import com.example.utilities.jwt.JwtTokenEncoder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenEncoder tokenEncoder;

    @Override
    public void registerUser(@Valid final UserRequestDto userRequestDto) {
        final UserEntity userEntity = modelMapper.map(userRequestDto, UserEntity.class);
        userEntity.setId(null);
        userEntity.setPassword(passwordEncoder.encryptPassword(userRequestDto.getPassword()));
        userRepository.findByEmail(userRequestDto.getEmail()).ifPresentOrElse(x -> {
            throw new UserAlreadyExistException();
        }, () -> userRepository.save(userEntity));
    }


    @Override
    public UserLoginResponseDto loginUser(String email, String password) {
        return userRepository.findByEmail(email).map(x -> loginUserAndReturnBearerToken(x, password)).orElseThrow(UserNotFoundException::new);
    }


    @Override
    public void deleteUser(String id) {
        userRepository.findById(id).ifPresentOrElse(userRepository::delete, UserNotFoundException::new);
    }

    private UserLoginResponseDto loginUserAndReturnBearerToken(final UserEntity entity, final String password) {
        if (!passwordEncoder.matchPassword(entity.getPassword(), password)) throw new PasswordDoesNotMatchException();

        final UserJWT jwt = new UserJWT();
        jwt.setId(entity.getId());
        jwt.setEmail(entity.getEmail());

        final String encodedJwt = tokenEncoder.generateBearerJwtTokenFromModel(jwt);
        final UserLoginResponseDto responseObject = new UserLoginResponseDto();
        responseObject.setEmail(entity.getEmail());
        responseObject.setToken(encodedJwt);

        return responseObject;
    }
}
