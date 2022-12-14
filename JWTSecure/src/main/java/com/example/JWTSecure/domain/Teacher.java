package com.example.JWTSecure.domain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "teacher")
public class Teacher {

    @Id
    @SequenceGenerator(
            name = "teacher_sequence",
            sequenceName = "teacher_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "teacher_sequence"
    )
    private Long id;
    @Column(name="user_id")
    private Long userId;
    @Column(name="role_id")
    private Long roleId;
    @Column(name="image_url")
    private String imageUrl;
}
