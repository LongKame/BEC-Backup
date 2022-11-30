package com.example.JWTSecure.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ClassScheduleDTO implements Serializable {

    private Long class_schedule_id;
    private Integer slot_th;
    private String date_study;
    private Integer slot_of_date;
    private Long room_id;
    private String room_name;
    private Integer teacher_id;
    private String teacher_name;

}
