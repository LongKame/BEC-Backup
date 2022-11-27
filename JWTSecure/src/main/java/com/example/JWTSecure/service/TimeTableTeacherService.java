package com.example.JWTSecure.service;

import com.example.JWTSecure.DTO.TimeTableTeacherDTO;
import com.example.JWTSecure.DTO.Week;
import java.util.List;


public interface TimeTableTeacherService {

    List<TimeTableTeacherDTO> getTimeTableOfTeacher(TimeTableTeacherDTO timeTableTeacherDTO);
    List<Week> getEveryWeek();
}
