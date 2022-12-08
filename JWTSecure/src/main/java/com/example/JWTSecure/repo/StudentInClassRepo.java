package com.example.JWTSecure.repo;

import com.example.JWTSecure.domain.Student;
import com.example.JWTSecure.domain.StudentInClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInClassRepo extends JpaRepository<StudentInClass, Long> {

    int countStudentInClassByClassId(Long id);
    StudentInClass findStudentInClassByClassIdAndStudentId(Long classId, Long studentId);
    StudentInClass findStudentInClassByStudentId(Long studentId);

    @Modifying
    @Query("UPDATE StudentInClass s SET s.isPaid = true WHERE s.studentId = ?1")
    int updatePending(Long id);
}