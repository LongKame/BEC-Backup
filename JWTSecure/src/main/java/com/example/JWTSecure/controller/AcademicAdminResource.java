package com.example.JWTSecure.controller;
import com.example.JWTSecure.DTO.*;
import com.example.JWTSecure.DTO.ResponseStatus;
import com.example.JWTSecure.domain.*;
import com.example.JWTSecure.service.AcademicAdminService;
import com.example.JWTSecure.service.RoomService;
import com.example.JWTSecure.service.SlotService;
import com.example.JWTSecure.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/aca")
@RequiredArgsConstructor
public class AcademicAdminResource {

    private final AcademicAdminService academicAdminService;
    private final RoomService roomService;
    private final SlotService slotService;
    private final TeacherService teacherService;

    @GetMapping("/get_slot")
    public ResponseEntity<List<Slot>> getSlot() {
        return ResponseEntity.ok().body(slotService.getSlot());
    }

    @GetMapping("/get_teacher")
    public ResponseEntity<List<TeacherDTO>> getTeachers() {
        return ResponseEntity.ok().body(teacherService.list());
    }


    @PostMapping("/add_quiz")
    public ResponseEntity<ResponseStatus> addQuiz(@RequestBody Quiz quiz) {
        return ResponseEntity.ok().body(academicAdminService.addQuiz(quiz));
    }

    @PostMapping("/get_quiz/{id}")
    public ResponseEntity<List<Quiz>> getQuiz(@PathVariable Long id) {
        return ResponseEntity.ok().body(academicAdminService.getQuiz(id));
    }

    @PostMapping("/get_quiz_paging")
    public ResponseEntity<SearchResultDTO<QuizDTO>> getQuizPaging(@RequestBody QuizDTO quizDTO) {
        return ResponseEntity.ok().body(academicAdminService.getQuizPaging(quizDTO));
    }

    @DeleteMapping("/delete_quiz/{id}")
    public ResponseEntity<ResponseStatus> deleteQuiz(@PathVariable Long id) {
        return ResponseEntity.ok().body(academicAdminService.deleteQuiz(id));
    }

    @PutMapping("/edit_quiz")
    public ResponseEntity<ResponseStatus> updateQuiz(@RequestBody Quiz quiz) {
        return ResponseEntity.ok().body(academicAdminService.editQuiz(quiz));
    }

    @PostMapping("/add_course")
    public ResponseEntity<ResponseStatus> addCourse(@RequestBody Course course) {
        return ResponseEntity.ok().body(academicAdminService.addCourse(course));
    }

    @GetMapping("/get_course")
    public ResponseEntity<List<Course>> getCourse() {
        return ResponseEntity.ok().body(academicAdminService.getCourse());
    }

    @PostMapping("/get_course_paging")
    public ResponseEntity<SearchResultDTO<CourseDTO>> getCoursePaging(@RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok().body(academicAdminService.getCoursePaging(courseDTO));
    }

    @PutMapping("/edit_course")
    public ResponseEntity<ResponseStatus> updateCourse(@RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok().body(academicAdminService.editCourse(courseDTO));
    }

    @PostMapping("/get_room_paging")
    public ResponseEntity<SearchResultDTO<RoomDTO>> getRoomPaging(@RequestBody RoomDTO roomDTO) {
        return ResponseEntity.ok().body(academicAdminService.getRoom(roomDTO));
    }

    @GetMapping("/get_room")
    public ResponseEntity<List<Room>> getRooms() {
        return ResponseEntity.ok().body(academicAdminService.getRooms());
    }

    @PostMapping("/add_room")
    public ResponseEntity<ResponseStatus> addRoom(@RequestBody Room room) {
        return ResponseEntity.ok().body(roomService.addRoom(room));
    }

    @PutMapping("/edit_room")
    public ResponseEntity<ResponseStatus> editRoom(@RequestBody Room room) {
        return ResponseEntity.ok().body(roomService.editRoom(room));
    }

    @DeleteMapping("/delete_room")
    public ResponseEntity<ResponseStatus> deleteRoom(@PathVariable Long id) {
        return ResponseEntity.ok().body(roomService.updateActiveRoom(id));
    }

    @PostMapping("/add_curriculum")
    public ResponseEntity<ResponseStatus> addRoom(@RequestBody Curriculum curriculum) {
        return ResponseEntity.ok().body(academicAdminService.addCurriculum(curriculum));
    }

    @PutMapping("/edit_curriculum")
    public ResponseEntity<ResponseStatus> editRoom(@RequestBody Curriculum curriculum) {
        return ResponseEntity.ok().body(academicAdminService.editCurriculum(curriculum));
    }

    @DeleteMapping("/delete_curriculum/{id}")
    public ResponseEntity<ResponseStatus> deleteCurriculum(@PathVariable Long id) {
        return ResponseEntity.ok().body(academicAdminService.deleteCurriculum(id));
    }

    @PostMapping("/get_curriculum_paging")
    public ResponseEntity<SearchResultDTO<CurriculumDTO>> getCurriculumPaging(@RequestBody CurriculumDTO curriculumDTO) {
        return ResponseEntity.ok().body(academicAdminService.getCurriculum(curriculumDTO));
    }
}
