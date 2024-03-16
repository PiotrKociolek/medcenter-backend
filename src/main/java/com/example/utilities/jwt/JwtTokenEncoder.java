package com.example.utilities.jwt;

import com.example.model.dtos.Response.UserJWT;

public interface JwtTokenEncoder {
    boolean isTokenValid(String bearerTokenString);

    UserJWT getTokenModel(final String bearerTokenString);

    String generateBearerJwtTokenFromModel(UserJWT jwtToken);
}
