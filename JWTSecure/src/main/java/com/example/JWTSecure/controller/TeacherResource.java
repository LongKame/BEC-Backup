package com.example.JWTSecure.controller;
import com.example.JWTSecure.DTO.*;
import com.example.JWTSecure.DTO.ResponseStatus;
import com.example.JWTSecure.domain.Classes;
import com.example.JWTSecure.domain.Course;
import com.example.JWTSecure.domain.Room;
import com.example.JWTSecure.domain.Slot;
import com.example.JWTSecure.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherResource {

    private final TeacherService teacherService;
    private final SlotService slotService;
    private final AcademicAdminService academicAdminService;
    private final StudentService studentService;
    private final ClassService classService;

    @PostMapping("/search_student")
    public ResponseEntity<SearchResultDTO<StudentDTO>> searchStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok().body(studentService.getAllStudent(studentDTO));
    }

    @PostMapping("/view_teacher")
    public ResponseEntity<SearchResultDTO<TeacherDTO>> getTeachers(@RequestBody TeacherDTO teacherDTO) {
        return ResponseEntity.ok().body(teacherService.getAllTeacher(teacherDTO));
    }

    @PostMapping("/view_student_class")
    public ResponseEntity<List<StudentInClassDTO>> getListStudentClass(@RequestBody StudentInClassDTO studentInClassDTO) {
        return ResponseEntity.ok().body(studentService.getListStudentByIdClass(studentInClassDTO.getClass_id()));
    }

    @PostMapping("/view_acad")
    public ResponseEntity<SearchResultDTO<AcademicAdminDTO>> getAcad(@RequestBody AcademicAdminDTO academicAdminDTO) {
        return ResponseEntity.ok().body(academicAdminService.getAllAcad(academicAdminDTO));
    }

    @PostMapping("/get_all_class")
    public ResponseEntity<SearchResultDTO<ClassDTO>> getClasses(@RequestBody ClassDTO classDTO) {
        return ResponseEntity.ok().body(classService.getAllClass(classDTO));
    }

    @GetMapping("/get_room")
    public ResponseEntity<List<Room>> getRooms() {
        return ResponseEntity.ok().body(academicAdminService.getRooms());
    }

    @GetMapping("/get_slot")
    public ResponseEntity<List<Slot>> getSlot() {
        return ResponseEntity.ok().body(slotService.getSlot());
    }

    @GetMapping("/get_teacher")
    public ResponseEntity<List<TeacherDTO>> getTeachers() {
        return ResponseEntity.ok().body(teacherService.list());
    }

    @GetMapping("/get_course")
    public ResponseEntity<List<Course>> getCourse() {
        return ResponseEntity.ok().body(academicAdminService.getCourse());
    }

    @PutMapping("/edit_profile_teacher")
    public ResponseEntity<ResponseStatus> editTeacher(@RequestBody AddTeacherDTO addTeacherDTO) {
        return ResponseEntity.ok().body(teacherService.editTeacherByTeacher(addTeacherDTO));
    }

    @PostMapping("/get_profile_teacher")
    public ResponseEntity<TeacherDTO> getProfile(@RequestBody TeacherDTO teacherDTO) {
        return ResponseEntity.ok().body(teacherService.getProfileTeacher(teacherDTO));
    }

    @GetMapping("/view_profile")
    public ResponseEntity<TeacherDTO> getTeacher(@RequestBody TeacherDTO teacherDTO) {
        return ResponseEntity.ok().body(teacherService.viewProfile(teacherDTO));
    }

    @PostMapping("/get_class_teacher")
    public ResponseEntity<List<Classes>> getClassTeacher(@RequestBody TeacherDTO teacherDTO) {
        return ResponseEntity.ok().body(teacherService.getClasses(teacherDTO.getUser_name()));
    }

    @PostMapping("/get_time_table_class")
    public ResponseEntity<TimeTableDTO> getTimetable(@RequestBody Classes classes) {
        return ResponseEntity.ok().body(teacherService.getTimetableByClasses(classes.getId()));
    }

    @PostMapping("/get_all_time_table_by_teacher")
    public ResponseEntity<TimeTableDTO> getAllTimetableByTeacher(@RequestBody Classes classes) {
        return ResponseEntity.ok().body(teacherService.getAllTimetableByTeacher(classes.getId()));
    }

//    @PostMapping("/get_all_time_table_by_teacher")
//    public ResponseEntity<TimeTableDTO> getAllTimetableByTeacher(@RequestBody Classes classes) {
//        return ResponseEntity.ok().body(teacherService.getAllTimetableByTeacher(classes.getId()));
//    }
}
