package com.example.JWTSecure.DTO;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class ChangeSlot implements Serializable {

    private Long class_schedule_id;
    private Long teacher_id;
    private Long class_id;
    private Integer slot_th;
    private String date;
    private Long room_id;
    private Integer slot_of_date;
}
