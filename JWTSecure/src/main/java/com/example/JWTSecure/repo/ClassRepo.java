package com.example.JWTSecure.repo;

import com.example.JWTSecure.domain.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository

public interface ClassRepo extends JpaRepository<Classes, Long> {

    Classes findTopByOrderByIdDesc();
    Classes findByName(String name);
    void deleteByCourseId(Long id);
    @Transactional
    @Modifying
    @Query("UPDATE Classes c SET c.active = false WHERE c.id = ?1")
    int deActive(Long id);
    List<Classes> findAllByTeacherId(Long id);
    Optional<Classes> findById(Long id);
    Optional<List<Classes>> findByCourseId(Long id);
    List<Classes> findAllByOrderByIdAsc();

}
