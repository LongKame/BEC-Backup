package com.example.JWTSecure.repo;

import com.example.JWTSecure.domain.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculumRepo extends JpaRepository<Curriculum, Long> {

}