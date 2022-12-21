package com.example.JWTSecure.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class StudentInClassDTO implements Serializable {
    private Long student_in_class_id;
    private Long student_id;
    private Long class_id;
    private Long user_id;
    private String full_name;
    private String email;
    private String phone;
    private String address;
}
