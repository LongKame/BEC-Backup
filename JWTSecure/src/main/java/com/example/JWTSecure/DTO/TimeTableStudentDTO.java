package com.example.JWTSecure.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
public class TimeTableStudentDTO implements Serializable {
    private Long class_schedule_id;
    private Long class_id;
    private String class_name;
    private Integer slot_th;
    private String date_study;
    private Integer slot_of_date;
    private Long student_id;
    private String user_name;
    private String student_name;
    private String room_name;
    private String startWeek;
    private String endWeek;
    private int dayNumberOld;
}