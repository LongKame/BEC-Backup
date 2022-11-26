package com.example.JWTSecure.repo.impl;

import com.example.JWTSecure.DTO.TimeTableTeacherDTO;
import com.example.JWTSecure.domain.Teacher;
import com.example.JWTSecure.domain.User;
import com.example.JWTSecure.repo.TeacherRepo;
import com.example.JWTSecure.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class ClassScheduleCustomRepo {

    private final UserRepo userRepo;
    private final TeacherRepo teacherRepo;

    @PersistenceContext
    private EntityManager entityManager;

    public List<TimeTableTeacherDTO> getTimeTable(TimeTableTeacherDTO timeTableTeacherDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(timeTableTeacherDTO.getDate_study(), formatter);
        User user = userRepo.findByUsername(timeTableTeacherDTO.getUser_name());
        Teacher teacher = teacherRepo.findByUserId(user.getId());
        timeTableTeacherDTO.setTeacher_id(teacher.getId());
        StringBuilder sql = new StringBuilder()
                .append("select cs.id as class_schedule_id, cs.class_id as class_id, c.name as class_name,cs.slot_th as slot_th,\n" +
                        "cs.date as date_study, cs.slot_of_date as slot_of_date, c.teacher_id as teacher_id, u.fullname as teacher_name, r.roomname as room_name\n" +
                        "from class_schedule cs join class c on cs.class_id = c.id\n" +
                        "join teacher t on c.teacher_id = t.id join room r on cs.room_id = r.id join users u on t.user_id = u.id  ");
        sql.append(" WHERE 1 = 1");
        if (timeTableTeacherDTO.getTeacher_id() != null) {
            sql.append(" AND c.teacher_id = :teacher_id");
        }
        if (localDate != null) {
            sql.append(" AND cs.date = :date ");
        }

        sql.append(" order by slot_of_date ");

        NativeQuery<TimeTableTeacherDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (timeTableTeacherDTO.getTeacher_id() != null) {
            query.setParameter("teacher_id", timeTableTeacherDTO.getTeacher_id());
        }

        if (timeTableTeacherDTO.getDate_study() != null) {
            query.setParameter("date", localDate);
        }

        query.addScalar("class_schedule_id", new LongType());
        query.addScalar("class_id", new LongType());
        query.addScalar("class_name", new StringType());
        query.addScalar("slot_th", new IntegerType());
        query.addScalar("date_study", new StringType());
        query.addScalar("slot_of_date", new IntegerType());
        query.addScalar("teacher_id", new LongType());
        query.addScalar("teacher_name", new StringType());
        query.addScalar("room_name", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(TimeTableTeacherDTO.class));
        return query.list();
    }
}
