package com.example.JWTSecure.controller;

import com.example.JWTSecure.DTO.ClassDTO;
import com.example.JWTSecure.DTO.TimeTableTeacherDTO;
import com.example.JWTSecure.DTO.Week;
import com.example.JWTSecure.domain.Classes;
import com.example.JWTSecure.domain.Course;
import com.example.JWTSecure.service.AcademicAdminService;
import com.example.JWTSecure.service.TimeTableTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/common")
@RequiredArgsConstructor
public class CommonResource {

    private final AcademicAdminService academicAdminService;
    private final TimeTableTeacherService timeTableTeacherService;

    @GetMapping("/get_course")
    public ResponseEntity<List<Course>> getCourse() {
        return ResponseEntity.ok().body(academicAdminService.getCourse());
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

    @GetMapping("/get_every_week")
    public ResponseEntity<List<Week>> getEveryWeek() {
        return ResponseEntity.ok().body(timeTableTeacherService.getEveryWeek());
    }

}
