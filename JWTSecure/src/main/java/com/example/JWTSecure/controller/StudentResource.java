package com.example.JWTSecure.controller;
import com.example.JWTSecure.DTO.*;
import com.example.JWTSecure.DTO.ResponseStatus;
import com.example.JWTSecure.service.ClassService;
import com.example.JWTSecure.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentResource {

    private final StudentService studentService;
    private final ClassService classService;

    @PostMapping("/add_student")
    public ResponseEntity<ResponseStatus> addStudent(@RequestBody AddStudentDTO addStudentDTO) {
        return ResponseEntity.ok().body(studentService.addStudent(addStudentDTO));
    }

    @GetMapping("/view_student")
    public ResponseEntity<StudentDTO> getStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok().body(studentService.getStudent(studentDTO));
    }

    @GetMapping("/view_students")
    public ResponseEntity<SearchResultDTO<StudentDTO>> getAllStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok().body(studentService.getAllStudent(studentDTO));
    }

    @PostMapping("/get_profile_student")
    public ResponseEntity<StudentDTO> getProfile(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok().body(studentService.getProfileStudent(studentDTO));
    }

    @PutMapping("/edit_profile_student")
    public ResponseEntity<ResponseStatus> editTeacher(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok().body(studentService.editStudentByStudent(studentDTO));
    }

    @PutMapping("/edit_student")
    public ResponseEntity<ResponseStatus> editStudent(@RequestBody AddStudentDTO addStudentDTO) {
        return ResponseEntity.ok().body(studentService.editStudent(addStudentDTO));
    }

    @PostMapping("/get_all_class")
    public ResponseEntity<SearchResultDTO<ClassDTO>> getClasses(@RequestBody ClassDTO classDTO) {
        return ResponseEntity.ok().body(classService.getAllClass(classDTO));
    }

    @PostMapping("/register_course")
    public ResponseEntity<ResponseStatus> registerCourse(@RequestBody RegisterClass registerClass) {
        return ResponseEntity.ok().body(studentService.registerCourse(registerClass));
    }

}
