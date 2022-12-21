package com.example.JWTSecure.repo.impl;

import com.example.JWTSecure.DTO.ChangeSlot;
import com.example.JWTSecure.DTO.TimeTableStudentDTO;
import com.example.JWTSecure.DTO.TimeTableTeacherDTO;
import com.example.JWTSecure.domain.Student;
import com.example.JWTSecure.domain.Teacher;
import com.example.JWTSecure.domain.User;
import com.example.JWTSecure.repo.StudentRepo;
import com.example.JWTSecure.repo.TeacherRepo;
import com.example.JWTSecure.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClassScheduleCustomRepo {

    private final UserRepo userRepo;
    private final TeacherRepo teacherRepo;
    private final StudentRepo studentRepo;

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

    public List<TimeTableStudentDTO> getTimeTableForStudent(TimeTableStudentDTO timeTableStudentDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(timeTableStudentDTO.getDate_study(), formatter);
        User user = userRepo.findByUsername(timeTableStudentDTO.getUser_name());
        Student student = studentRepo.findByUserId(user.getId());
        timeTableStudentDTO.setStudent_id(student.getId());
        StringBuilder sql = new StringBuilder()
                .append("  select cs.id as class_schedule_id, cs.class_id as class_id, c.name as class_name,cs.slot_th as slot_th,\n" +
                        "cs.date as date_study, cs.slot_of_date as slot_of_date, r.roomname as room_name\n" +
                        "from class_schedule cs join student_in_class sic on cs.class_id = sic.class_id \n" +
                        "join room r on cs.room_id = r.id join class c on c.id = cs.class_id ");
        sql.append(" WHERE 1 = 1");
        if (timeTableStudentDTO.getStudent_id() != null) {
            sql.append(" AND sic.student_id = :student_id");
        }
        if (localDate != null) {
            sql.append(" AND cs.date = :date ");
        }

        sql.append(" order by slot_of_date ");

        NativeQuery<TimeTableStudentDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (timeTableStudentDTO.getStudent_id() != null) {
            query.setParameter("student_id", timeTableStudentDTO.getStudent_id());
        }
        if (timeTableStudentDTO.getDate_study() != null) {
            query.setParameter("date", localDate);
        }

        query.addScalar("class_schedule_id", new LongType());
        query.addScalar("class_id", new LongType());
        query.addScalar("class_name", new StringType());
        query.addScalar("slot_th", new IntegerType());
        query.addScalar("date_study", new StringType());
        query.addScalar("slot_of_date", new IntegerType());
        query.addScalar("room_name", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(TimeTableStudentDTO.class));
        return query.list();
    }

    public List<ChangeSlot> findSlotEmpty(ChangeSlot changeSlotDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(changeSlotDTO.getDate(), formatter);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        localDate = LocalDate.parse(localDate.toString(), formatter);
        StringBuilder sql = new StringBuilder()
                .append("select cs.id as class_schedule_id from class_schedule cs join class c on cs.class_id = c.id\n ");
        sql.append(" WHERE 1 = 1");

        if (changeSlotDTO.getRoom_id() != null) {
            sql.append(" AND cs.room_id = :room_id ");
        }
        if (changeSlotDTO.getDate() != null) {
            sql.append(" AND cs.date = :date");
        }
        if (changeSlotDTO.getSlot_of_date() != null) {
            sql.append(" AND cs.slot_of_date = :slot_of_date ");
        }
        if (changeSlotDTO.getTeacher_id() != null) {
            sql.append(" AND c.teacher_id = :teacher_id ");
        }

        NativeQuery<ChangeSlot> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (changeSlotDTO.getRoom_id() != null) {
            query.setParameter("room_id", changeSlotDTO.getRoom_id());
        }
        if (changeSlotDTO.getDate() != null) {
            query.setParameter("date", localDate);
        }
        if (changeSlotDTO.getSlot_of_date() != null) {
            query.setParameter("slot_of_date", changeSlotDTO.getSlot_of_date());
        }
        if (changeSlotDTO.getTeacher_id() != null) {
            query.setParameter("teacher_id", changeSlotDTO.getTeacher_id());
        }

        query.addScalar("class_schedule_id", new LongType());

        query.setResultTransformer(Transformers.aliasToBean(ChangeSlot.class));
        return query.list();
    }

}
