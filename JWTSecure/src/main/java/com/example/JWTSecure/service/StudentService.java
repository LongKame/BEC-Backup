package com.example.JWTSecure.service;
import com.example.JWTSecure.DTO.*;
import com.example.JWTSecure.domain.Curriculum;
import java.util.List;

public interface StudentService {
    StudentDTO getStudent(StudentDTO studentDTO);
    SearchResultDTO<StudentDTO> getAllStudent(StudentDTO studentDTO);
    SearchResultDTO<StudentDTO> getStudentPending(StudentDTO studentDTO);
    ResponseStatus addStudent(AddStudentDTO addStudentDTO);
    ResponseStatus editStudent(AddStudentDTO addStudentDTO);
    List<StudentDTO> getListStudentByIdClass(Long id);
    List<StudentDTO>  detailStudentClass(Long id);
    ResponseStatus updatePending(Long id);
    ResponseStatus deletePending(Long id);
    ResponseStatus addCurriculum(Curriculum curriculum);
    ResponseStatus registerCourse(RegisterClass registerClasss);
    ResponseStatus updateActiveStudent(Long user_id);
    StudentDTO getProfileStudent(StudentDTO studentDTO);
    ResponseStatus editStudentByStudent(StudentDTO studentDTO);
}
