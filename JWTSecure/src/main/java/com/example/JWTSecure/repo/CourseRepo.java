package com.example.JWTSecure.repo;

import com.example.JWTSecure.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
    public List<Course> findAllByOrderByIdAsc();
}
