package com.jwt.example.JWTExample3.security;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtRequest
{

    private String email;
    private String password;
}