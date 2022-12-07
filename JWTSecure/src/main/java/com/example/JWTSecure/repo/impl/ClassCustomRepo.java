package com.example.JWTSecure.repo.impl;
import com.example.JWTSecure.DTO.ClassDTO;
import com.example.JWTSecure.DTO.ClassScheduleDTO;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ClassCustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ClassDTO> getClasses(Long courseId) {

        StringBuilder sql = new StringBuilder()
                .append("select c.id as class_id, c.name as class_name, c.room_id as room_id, r.roomname as room_name,\n" +
                        "u.id as user_id, t.id as teacher_Id, u.fullname as full_name, u.email as email, l.name as level,\n" +
                        "r.capacity as capacity, c.start_date, c.end_date\n" +
                        "from class c\n" +
                        "join room r on r.id = c.room_id\n" +
                        "join teacher t on c.teacher_id = t.id\n" +
                        "join users u on t.user_id = u.id\n" +
                        "join course co on co.id = c.course_id\n" +
                        "join level l on co.level_id = l.id  ");
        sql.append(" WHERE 1 = 1 ");
        if(courseId!=null){
            sql.append(" AND c.course_id = :courseId");
        }

        sql.append(" order by c.id ");

        NativeQuery<ClassDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if(courseId!=null){
            query.setParameter("courseId", courseId);
        }

        query.addScalar("class_id", new LongType());
        query.addScalar("class_name", new StringType());
        query.addScalar("room_id", new LongType());
        query.addScalar("room_name", new StringType());
        query.addScalar("user_id", new LongType());
        query.addScalar("teacher_id", new LongType());
        query.addScalar("full_name", new StringType());
        query.addScalar("email", new StringType());
        query.addScalar("level", new StringType());
        query.addScalar("capacity", new IntegerType());
        query.addScalar("start_date", new StringType());
        query.addScalar("end_date", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(ClassDTO.class));
        return query.list();
    }

    public List<ClassDTO> doSearch(ClassDTO classDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select c.id as class_id, c.name as class_name, c.room_id as room_id, r.roomname as room_name,\n" +
                        "u.id as user_id, t.id as teacher_Id, u.fullname as full_name, u.email as email, l.name as level,\n" +
                        "r.capacity as capacity, c.start_date, c.end_date\n" +
                        "from class c\n" +
                        "join room r on r.id = c.room_id\n" +
                        "join teacher t on c.teacher_id = t.id\n" +
                        "join users u on t.user_id = u.id\n" +
                        "join course co on co.id = c.course_id\n" +
                        "join level l on co.level_id = l.id  ");
        sql.append(" WHERE 1 = 1 ");
        if(classDTO.isActive()){
            sql.append(" AND c.active = :active ");
        }
        if(classDTO.getKey_search()!=null){
            sql.append(" AND (UPPER(c.name) LIKE CONCAT('%', UPPER(:class_name), '%') ESCAPE '&')");
        }

        sql.append(" order by c.id ");

        NativeQuery<ClassDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if(classDTO.isActive()){
            query.setParameter("active", classDTO.isActive());
        }
        if (classDTO.getKey_search() != null) {
            query.setParameter("class_name", "%"+classDTO.getKey_search()+"%");
        }

        query.addScalar("class_id", new LongType());
        query.addScalar("class_name", new StringType());
        query.addScalar("room_id", new LongType());
        query.addScalar("room_name", new StringType());
        query.addScalar("user_id", new LongType());
        query.addScalar("teacher_id", new LongType());
        query.addScalar("full_name", new StringType());
        query.addScalar("email", new StringType());
        query.addScalar("level", new StringType());
        query.addScalar("capacity", new IntegerType());
        query.addScalar("start_date", new StringType());
        query.addScalar("end_date", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(ClassDTO.class));
        if (null != String.valueOf(classDTO.getPage())) {
            query.setMaxResults(classDTO.getPageSize());
            query.setFirstResult(((classDTO.getPage() - 1) * classDTO.getPageSize()));
        }
        return query.list();
    }

    public List<ClassDTO> getTotal(ClassDTO classDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select c.id as class_id, c.name as class_name, c.room_id as room_id, r.roomname as room_name,\n" +
                        "u.id as user_id, t.id as teacher_Id, u.fullname as full_name, u.email as email, l.name as level,\n" +
                        "r.capacity as capacity, c.start_date, c.end_date\n" +
                        "from class c\n" +
                        "join room r on r.id = c.room_id\n" +
                        "join teacher t on c.teacher_id = t.id\n" +
                        "join users u on t.user_id = u.id\n" +
                        "join course co on co.id = c.course_id\n" +
                        "join level l on co.level_id = l.id  ");
        sql.append(" WHERE 1 = 1 ");
        if(classDTO.isActive()){
            sql.append(" AND c.active = :active ");
        }
        if(classDTO.getKey_search()!=null){
            sql.append(" AND (UPPER(c.name) LIKE CONCAT('%', UPPER(:class_name), '%') ESCAPE '&') ");
        }

        sql.append(" order by c.id ");

        NativeQuery<ClassDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if(classDTO.isActive()){
            query.setParameter("active", classDTO.isActive());
        }
        if (classDTO.getKey_search() != null) {
            query.setParameter("class_name", "%"+classDTO.getKey_search()+"%");
        }

        query.addScalar("class_id", new LongType());
        query.addScalar("class_name", new StringType());
        query.addScalar("room_id", new LongType());
        query.addScalar("room_name", new StringType());
        query.addScalar("user_id", new LongType());
        query.addScalar("teacher_id", new LongType());
        query.addScalar("full_name", new StringType());
        query.addScalar("email", new StringType());
        query.addScalar("level", new StringType());
        query.addScalar("capacity", new IntegerType());
        query.addScalar("start_date", new StringType());
        query.addScalar("end_date", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(ClassDTO.class));
        return query.list();
    }

    public List<ClassDTO> getTotalClass() {

        StringBuilder sql = new StringBuilder()
                .append("select c.id as class_id, c.name as class_name, c.room_id as room_id, r.roomname as room_name,\n" +
                        "u.id as user_id, t.id as teacher_Id, u.fullname as full_name, u.email as email, l.name as level,\n" +
                        "r.capacity as capacity, c.start_date, c.end_date\n" +
                        "from class c\n" +
                        "join room r on r.id = c.room_id\n" +
                        "join teacher t on c.teacher_id = t.id\n" +
                        "join users u on t.user_id = u.id\n" +
                        "join course co on co.id = c.course_id\n" +
                        "join level l on co.level_id = l.id  ");
        sql.append(" WHERE 1 = 1 AND c.active = true order by c.id");

        NativeQuery<ClassDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        query.addScalar("class_id", new LongType());
        query.addScalar("class_name", new StringType());
        query.addScalar("room_id", new LongType());
        query.addScalar("room_name", new StringType());
        query.addScalar("user_id", new LongType());
        query.addScalar("teacher_id", new LongType());
        query.addScalar("full_name", new StringType());
        query.addScalar("email", new StringType());
        query.addScalar("level", new StringType());
        query.addScalar("capacity", new IntegerType());
        query.addScalar("start_date", new StringType());
        query.addScalar("end_date", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(ClassDTO.class));
        return query.list();
    }

    public List<ClassScheduleDTO> getScheduleById(Long class_id) {

        StringBuilder sql = new StringBuilder()
                .append("select cs.id as class_schedule_id, cs.slot_th as slot_th, cs.date as date_study, cs.slot_of_date as slot_of_date,\n" +
                        "r.id as room_id,r.roomname as room_name, t.id as teacher_id, u.fullname as teacher_name\n" +
                        "from class_schedule cs join class c on cs.class_id = c.id join room r on c.room_id = r.id\n" +
                        "join teacher t on c.teacher_id = t.id join users u on t.user_id = u.id ");
        sql.append(" WHERE 1 = 1");

        if(class_id!=null){
            sql.append(" AND c.id = :class_id ");
        }

        sql.append(" order by cs.id ");

        NativeQuery<ClassScheduleDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if(class_id!=null){
            query.setParameter("class_id", class_id);
        }

        query.addScalar("class_schedule_id", new LongType());
        query.addScalar("slot_th", new IntegerType());
        query.addScalar("date_study", new StringType());
        query.addScalar("slot_of_date", new IntegerType());
        query.addScalar("room_id", new LongType());
        query.addScalar("room_name", new StringType());
        query.addScalar("teacher_name", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(ClassScheduleDTO.class));
        return query.list();
    }

    public ClassDTO checkRoomActive(ClassDTO classDTO) {
        try {
            StringBuilder sql = new StringBuilder()
                    .append("select c.name as class_name, c.start_date as start_date, c.end_date as end_date, c.active as active\n" +
                            "from class c join class_slot cs on c.id = cs.class_id ");
            sql.append("WHERE 1 = 1 ");
            if(classDTO.getSlot_id() != null){
                sql.append(" AND cs.slot_id = :slot_id ");
            }
            if(classDTO.getFirstOnWeek() != null){
                sql.append(" AND cs.first_of_week = :first_of_week ");
            }
//            if(classDTO.getSecondOnWeek() != null){
//                sql.append(" AND cs.second_of_week = :second_of_week ");
//            }
            if(classDTO.isActive()){
                sql.append(" AND c.active = :active ");
            }
            if(classDTO.getRoom_id()!=null){
                sql.append(" AND c.room_id = :room_id ");
            }

            NativeQuery<ClassDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

            if(classDTO.getSlot_id() != null){
                query.setParameter("slot_id", classDTO.getSlot_id());
            }
            if(classDTO.getFirstOnWeek() != null){
                query.setParameter("first_of_week", classDTO.getFirstOnWeek().getValue());
            }
//            if(classDTO.getSecondOnWeek() != null){
//                query.setParameter("second_of_week", classDTO.getSecondOnWeek().getValue());
//            }
            if(classDTO.isActive()){
                query.setParameter("active", classDTO.isActive());
            }
            if(classDTO.getRoom_id()!=null){
                query.setParameter("room_id", classDTO.getRoom_id());
            }

            query.addScalar("class_name", new StringType());
            query.addScalar("start_date", new StringType());
            query.addScalar("end_date", new StringType());
            query.addScalar("active", new BooleanType());

            query.setResultTransformer(Transformers.aliasToBean(ClassDTO.class));
            return (ClassDTO) query.getSingleResult();
        }catch (RuntimeException re){
            return null;
        }
    }

    public ClassDTO checkTeacherTeaching(ClassDTO classDTO) {

        try{
            StringBuilder sql = new StringBuilder()
                    .append("select c.name as class_name, c.start_date as start_date, c.end_date as end_date, c.active as active\n" +
                            "from class c join class_slot cs on c.id = cs.class_id ");
            sql.append("WHERE 1 = 1 ");
            if(classDTO.getSlot_id() != null){
                sql.append(" AND cs.slot_id = :slot_id ");
            }
            if(classDTO.getFirstOnWeek() != null){
                sql.append(" AND cs.first_of_week = :first_of_week ");
            }
//            if(classDTO.getSecondOnWeek() != null){
//                sql.append(" AND cs.second_of_week = :second_of_week ");
//            }
            if(classDTO.isActive()){
                sql.append(" AND c.active = :active ");
            }
            if(classDTO.getTeacher_id()!=null){
                sql.append(" AND c.teacher_id = :teacher_id ");
            }

            NativeQuery<ClassDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

            if(classDTO.getSlot_id() != null){
                query.setParameter("slot_id", classDTO.getSlot_id());
            }
            if(classDTO.getFirstOnWeek() != null){
                query.setParameter("first_of_week", classDTO.getFirstOnWeek().getValue());
            }
//            if(classDTO.getSecondOnWeek() != null){
//                query.setParameter("second_of_week", classDTO.getSecondOnWeek().getValue());
//            }
            if(classDTO.isActive()){
                query.setParameter("active", classDTO.isActive());
            }
            if(classDTO.getTeacher_id()!=null){
                query.setParameter("teacher_id", classDTO.getTeacher_id());
            }

            query.addScalar("class_name", new StringType());
            query.addScalar("start_date", new StringType());
            query.addScalar("end_date", new StringType());
            query.addScalar("active", new BooleanType());

            query.setResultTransformer(Transformers.aliasToBean(ClassDTO.class));
            return (ClassDTO) query.getSingleResult();
        }catch (RuntimeException re){
            return null;
        }
    }

}