package com.example.JWTSecure.controller;

import com.example.JWTSecure.DTO.*;
import com.example.JWTSecure.DTO.ResponseStatus;
import com.example.JWTSecure.domain.Course;
import com.example.JWTSecure.domain.Quiz;
import com.example.JWTSecure.domain.Room;
import com.example.JWTSecure.service.AcademicAdminService;
import com.example.JWTSecure.service.RoomService;
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
    @PostMapping("/add_quiz")
    public ResponseEntity<ResponseStatus> addQuiz(@RequestBody Quiz quiz) {
        return ResponseEntity.ok().body(academicAdminService.addQuiz(quiz));
    }

    @PostMapping("/get_quiz/{id}")
    public ResponseEntity<List<Quiz>> getQuiz(@PathVariable Long id) {
        return ResponseEntity.ok().body(academicAdminService.getQuiz(id));
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

    @DeleteMapping("/delete_course/{id}")
    public ResponseEntity<ResponseStatus> deleteCourse(@PathVariable Long id) {
        return ResponseEntity.ok().body(academicAdminService.deleteCourse(id));
    }

    @PutMapping("/edit_course")
    public ResponseEntity<ResponseStatus> updateCourse(@RequestBody Course course) {
        return ResponseEntity.ok().body(academicAdminService.editCourse(course));
    }

    @GetMapping("/get_room")
    public ResponseEntity<SearchResultDTO<RoomDTO>> getRooms(@RequestBody RoomDTO roomDTO) {
        return ResponseEntity.ok().body(academicAdminService.getRoom(roomDTO));
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
    public ResponseEntity<ResponseStatus> deleteRoom(@RequestBody Room room) {
        return ResponseEntity.ok().body(roomService.deleteRoom(room));
    }

}
