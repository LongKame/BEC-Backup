package com.example.JWTSecure.controller;
import com.example.JWTSecure.DTO.*;
import com.example.JWTSecure.DTO.ResponseStatus;
import com.example.JWTSecure.domain.Classes;
import com.example.JWTSecure.domain.Course;
import com.example.JWTSecure.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/common")
@RequiredArgsConstructor
public class CommonResource {

    private final AcademicAdminService academicAdminService;
    private final TimeTableTeacherService timeTableTeacherService;
    private final ClassService classService;
    private final StudentService studentService;
    private final SlotService slotService;

    @GetMapping("/get_class")
    public ResponseEntity<List<Classes>> getClass1() {
        return ResponseEntity.ok().body(academicAdminService.getClasses());
    }

    @PutMapping("/update_slot")
    public ResponseEntity<ResponseStatus> updateSlot(@RequestBody ChangeSlot changeSlotDTO) {
        return ResponseEntity.ok().body(slotService.updateSlot(changeSlotDTO));
    }

    @GetMapping("/get_course")
    public ResponseEntity<List<Course>> getCourse() {
        return ResponseEntity.ok().body(academicAdminService.getCourse());
    }

    @GetMapping("/view_curriculum")
    public ResponseEntity<List<CurriculumDTO>> getCurriculum() {
        return ResponseEntity.ok().body(academicAdminService.viewCurriculum());
    }

    @PostMapping("/get_course_by_id")
    public ResponseEntity<Course> getCourseById(@RequestBody Course course) {
        return ResponseEntity.ok().body(academicAdminService.getCourseById(course.getId()));
    }

    @PostMapping("/get_class_by_course_id")
    public ResponseEntity<List<ClassDTO>> getClassByCourseId(@RequestBody Classes classes) {
        return ResponseEntity.ok().body(academicAdminService.getClassByCourseId(classes.getCourseId()));
    }

    @PostMapping("/get_time_table")
    public ResponseEntity<List<TimeTableTeacherDTO>> getTimeTableTrue(@RequestBody TimeTableTeacherDTO timeTableTeacherDTO) {
        return ResponseEntity.ok().body(timeTableTeacherService.getTimeTableOfTeacher(timeTableTeacherDTO));
    }

    @PostMapping("/get_time_table_for_student")
    public ResponseEntity<List<TimeTableStudentDTO>> getTimeTableForStudent(@RequestBody TimeTableStudentDTO timeTableStudentDTO) {
        return ResponseEntity.ok().body(timeTableTeacherService.getTimeTableOfStudent(timeTableStudentDTO));
    }

    @GetMapping("/get_every_week")
    public ResponseEntity<List<Week>> getEveryWeek() {
        return ResponseEntity.ok().body(timeTableTeacherService.getEveryWeek());
    }

    @GetMapping("/get_all_class")
    public ResponseEntity<List<ClassDTO>> getClasses() {
        return ResponseEntity.ok().body(classService.getTotalClass());
    }

    @PostMapping("/get_all_class_by_id")
    public ResponseEntity<List<ClassScheduleDTO>> getClassesById(@RequestBody ClassDTO classDTO) {
        return ResponseEntity.ok().body(classService.getClassById(classDTO.getClass_id()));
    }

    @PostMapping("/register_course")
    public ResponseEntity<ResponseStatus> registerCourse(@RequestBody RegisterClass registerClass) {
        return ResponseEntity.ok().body(studentService.registerCourse(registerClass));
    }

    @PostMapping("/get_profile_student")
    public ResponseEntity<StudentDTO> getProfile(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok().body(studentService.getProfileStudent(studentDTO));
    }

    @PutMapping("/edit_profile_student")
    public ResponseEntity<ResponseStatus> editTeacher(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok().body(studentService.editStudentByStudent(studentDTO));
    }
}
