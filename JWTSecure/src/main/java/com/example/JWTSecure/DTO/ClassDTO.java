package com.example.JWTSecure.DTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.DayOfWeek;

@Getter
@Setter
@NoArgsConstructor
public class ClassDTO implements Serializable {
    private Long class_id;
    private String class_name;
    private Long room_id;
    private String room_name;
    private Long user_id;
    private Long teacher_id;
    private Long slot_id;
    private String full_name;
    private Long course_id;
    private String email;
    private String level;
    private Integer capacity;
    private String start_date;
    private String end_date;
    private DayOfWeek firstOnWeek;
    private DayOfWeek secondOnWeek;
    private Integer number_slot;
    private boolean active;
    private int page;
    private int pageSize;
    private String key_search;
}