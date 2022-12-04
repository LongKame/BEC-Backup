package com.example.JWTSecure.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RegisterClass implements Serializable {
    private Long id;
    private String username;
    private Long studentId;
    private Long classId;
}
