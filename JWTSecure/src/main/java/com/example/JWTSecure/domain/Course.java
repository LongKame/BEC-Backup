package com.example.JWTSecure.domain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "course")
public class Course {

    @Id
    @SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "course_sequence"
    )
    private Long id;
    @Column(name="level_id")
    private Long levelId;
    @Column(name="name")
    private String name;
    @Column(name="fee")
    private Double fee;
    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
    @Column(name="number_slot")
    private Integer numberSlot;
    @Column(name="image")
    private String image;
    @Column(name="description")
    private String description;

    public Course(String name) {
        this.name = name;
    }


}
