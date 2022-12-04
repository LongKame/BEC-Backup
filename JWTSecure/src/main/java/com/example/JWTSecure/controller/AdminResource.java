package com.example.JWTSecure.controller;
import com.example.JWTSecure.DTO.*;
import com.example.JWTSecure.DTO.ResponseStatus;
import com.example.JWTSecure.domain.*;
import com.example.JWTSecure.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminResource {

    private final TeacherService teacherService;
    private final AcademicAdminService academicAdminService;
    private final ClassService classService;
    private final StudentService studentService;
    private final SlotService slotService;

    @GetMapping("/get_slot")
    public ResponseEntity<List<Slot>> getSlot() {
        return ResponseEntity.ok().body(slotService.getSlot());
    }

    @GetMapping("/get_teacher")
    public ResponseEntity<List<TeacherDTO>> getTeachers() {
        return ResponseEntity.ok().body(teacherService.list());
    }

    @PostMapping("/add_teacher")
    public ResponseEntity<ResponseStatus> addTeacher(@RequestBody AddTeacherDTO addTeacherDTO) {
        return ResponseEntity.ok().body(teacherService.addTeacher(addTeacherDTO));
    }

    @PostMapping("/view_teacher")
    public ResponseEntity<SearchResultDTO<TeacherDTO>> getTeachers(@RequestBody TeacherDTO teacherDTO) {
        return ResponseEntity.ok().body(teacherService.getAllTeacher(teacherDTO));
    }

    @GetMapping("/get_course")
    public ResponseEntity<List<Course>> getCourse() {
        return ResponseEntity.ok().body(academicAdminService.getCourse());
    }

    @GetMapping("/get_room")
    public ResponseEntity<List<Room>> getRooms() {
        return ResponseEntity.ok().body(academicAdminService.getRooms());
    }

    @PutMapping("/delete_teacher")
    public ResponseEntity<ResponseStatus> deleteTeacher(@RequestBody AddTeacherDTO addTeacherDTO) {
        return ResponseEntity.ok().body(teacherService.deleteTeacher(addTeacherDTO.getId()));
    }

    @PutMapping("/edit_teacher")
    public ResponseEntity<ResponseStatus> editTeacher(@RequestBody AddTeacherDTO addTeacherDTO) {
        return ResponseEntity.ok().body(teacherService.editTeacher(addTeacherDTO));
    }

    @PostMapping("/add_acad")
    public ResponseEntity<ResponseStatus> addAcad(@RequestBody AddAcademicAdminDTO addAcademicAdminDTO) {
        return ResponseEntity.ok().body(academicAdminService.addAcad(addAcademicAdminDTO));
    }

    @PostMapping("/view_acad")
    public ResponseEntity<SearchResultDTO<AcademicAdminDTO>> getAcad(@RequestBody AcademicAdminDTO academicAdminDTO) {
        return ResponseEntity.ok().body(academicAdminService.getAllAcad(academicAdminDTO));
    }

    @PutMapping("/edit_acad")
    public ResponseEntity<ResponseStatus> editAcad(@RequestBody AddAcademicAdminDTO addAcademicAdminDTO) {
        return ResponseEntity.ok().body(academicAdminService.editAcad(addAcademicAdminDTO));
    }

    @PutMapping("/delete_acad")
    public ResponseEntity<ResponseStatus> deleteAcad(@RequestBody AddAcademicAdminDTO addAcademicAdminDTO) {
        return ResponseEntity.ok().body(academicAdminService.deleteAcad(addAcademicAdminDTO.getId()));
    }

    @PutMapping("/disable_class")
    public ResponseEntity<ResponseStatus> disableClass(@RequestBody Classes classes) {
        return ResponseEntity.ok().body(classService.disableClass(classes.getId()));
    }

    @PostMapping("/get_all_class")
    public ResponseEntity<SearchResultDTO<ClassDTO>> getClasses(@RequestBody ClassDTO classDTO) {
        return ResponseEntity.ok().body(classService.getAllClass(classDTO));
    }

    @PostMapping("/view_student_class/{id}")
    public ResponseEntity<List<StudentDTO>> getListStudentClass(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentService.getListStudentByIdClass(id));
    }

    @PostMapping("/search_student")
    public ResponseEntity<SearchResultDTO<StudentDTO>> searchStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok().body(studentService.getAllStudent(studentDTO));
    }

    @PostMapping("/detail_student_class/{id}")
    public ResponseEntity<List<StudentDTO>> detailStudentClass(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentService.detailStudentClass(id));
    }

    @PostMapping("/get_student_pending")
    public ResponseEntity<SearchResultDTO<StudentDTO>> searchStudentPending(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok().body(studentService.getStudentPending(studentDTO));
    }

    @PostMapping("/add_curriculum")
    public ResponseEntity<ResponseStatus> addCurriculum(@RequestBody Curriculum curriculum) {
        return ResponseEntity.ok().body(studentService.addCurriculum(curriculum));
    }

    @PutMapping("/delete_student")
    public ResponseEntity<ResponseStatus> deleteStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok().body(studentService.updateActiveStudent(studentDTO.getUser_Id()));
    }

    @PutMapping("/update_pending")
    public ResponseEntity<ResponseStatus> updatePending(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok().body(studentService.updatePending(studentDTO.getStudent_Id()));
    }

    @DeleteMapping("/delete_pending/{id}")
    public ResponseEntity<ResponseStatus> deletePending(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentService.deletePending(id));
    }

    @PostMapping("/add_class")
    public ResponseEntity<ResponseStatus> addClass(@RequestBody ClassDTO classDTO) {
        try{
            return ResponseEntity.ok().body(classService.addClass(classDTO));
        }catch (ParseException ex){
        }
        return null;
    }
}
