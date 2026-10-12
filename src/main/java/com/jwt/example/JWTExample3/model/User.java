package com.jwt.example.JWTExample3.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User
{
    private String userId;
    private String name;
    private String email;

}
