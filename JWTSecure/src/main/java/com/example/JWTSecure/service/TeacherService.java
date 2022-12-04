package com.example.JWTSecure.service;
import com.example.JWTSecure.DTO.*;
import com.example.JWTSecure.domain.Classes;
import java.util.List;

public interface TeacherService {

    SearchResultDTO<TeacherDTO> getAllTeacher(TeacherDTO productDTO);
    ResponseStatus addTeacher(AddTeacherDTO addTeacherDTO);
    TeacherDTO viewProfile(TeacherDTO productDTO);
    ResponseStatus editTeacher(AddTeacherDTO addTeacherDTO);
    ResponseStatus editTeacherByTeacher(AddTeacherDTO addTeacherDTO);
    ResponseStatus deleteTeacher(Long id);
    List<TeacherDTO> list();
    List<Classes> getClasses(String username);
    TimeTableDTO getTimetableByClasses(Long id);
    TimeTableDTO getAllTimetableByTeacher(Long id);
    TeacherDTO getProfileTeacher(TeacherDTO teacherDTO);
}
