package com.example.JWTSecure.repo.impl;
import com.example.JWTSecure.DTO.StudentDTO;
import com.example.JWTSecure.DTO.StudentInClassDTO;
import com.example.JWTSecure.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class StudentCustomRepo {
    @PersistenceContext
    private EntityManager entityManager;

    private final UserRepo userRepo;

    public List<StudentDTO> doSearchPending(StudentDTO studentDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select s.id as student_Id, s.user_id as user_Id,\n" +
                        "u.username as user_name, u.fullname as full_name, sic.is_paid as isPaid,\n" +
                        "u.email as email, u.phone as phone, u.address as address,\n" +
                        "u.active as active, c.name as class_name, co.name as course_name, c.start_date as start_date\n" +
                        "from student s join users u on s.user_id = u.id\n" +
                        "join student_in_class sic on s.id = sic.student_id\n" +
                        "join class c on sic.class_id = c.id\n" +
                        "join course co on c.course_id = co.id");
        sql.append(" WHERE 1 = 1 AND sic.is_paid = false");
        if (studentDTO.getKey_search()!=null) {
            sql.append(" AND (UPPER(u.fullname) LIKE CONCAT('%', UPPER(:full_name), '%') ESCAPE '&') ");
        }

        sql.append(" order by s.id");

        NativeQuery<StudentDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (studentDTO.getKey_search()!=null) {
            query.setParameter("full_name", "%"+studentDTO.getKey_search()+"%");
        }

        query.addScalar("student_Id", new LongType());
        query.addScalar("user_Id", new LongType());
        query.addScalar("user_name", new StringType());
        query.addScalar("full_name", new StringType());
        query.addScalar("isPaid", new BooleanType());
        query.addScalar("email", new StringType());
        query.addScalar("phone", new StringType());
        query.addScalar("address", new StringType());
        query.addScalar("active", new BooleanType());
        query.addScalar("class_name", new StringType());
        query.addScalar("course_name", new StringType());
        query.addScalar("start_date", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(StudentDTO.class));
        if (null != String.valueOf(studentDTO.getPage())) {
            query.setMaxResults(studentDTO.getPageSize());
            query.setFirstResult(((studentDTO.getPage() - 1) * studentDTO.getPageSize()));
        }
        return query.list();
    }

    public List<StudentDTO> getTotalPending(StudentDTO studentDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select s.id as student_Id, s.user_id as user_Id,\n" +
                        "u.username as user_name, u.fullname as full_name, sic.is_paid as isPaid,\n" +
                        "u.email as email, u.phone as phone, u.address as address,\n" +
                        "u.active as active, c.name as class_name, co.name as course_name, c.start_date as start_date\n" +
                        "from student s join users u on s.user_id = u.id\n" +
                        "join student_in_class sic on s.id = sic.student_id\n" +
                        "join class c on sic.class_id = c.id\n" +
                        "join course co on c.course_id = co.id");
        sql.append(" WHERE 1 = 1 AND sic.is_paid = false");
        if (studentDTO.getKey_search()!=null) {
            sql.append(" AND (UPPER(u.fullname) LIKE CONCAT('%', UPPER(:full_name), '%') ESCAPE '&') ");
        }

        sql.append(" order by s.id");

        NativeQuery<StudentDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (studentDTO.getKey_search()!=null) {
            query.setParameter("full_name", "%"+studentDTO.getKey_search()+"%");
        }

        query.addScalar("student_Id", new LongType());
        query.addScalar("user_Id", new LongType());
        query.addScalar("user_name", new StringType());
        query.addScalar("full_name", new StringType());
        query.addScalar("isPaid", new BooleanType());
        query.addScalar("email", new StringType());
        query.addScalar("phone", new StringType());
        query.addScalar("address", new StringType());
        query.addScalar("active", new BooleanType());
        query.addScalar("class_name", new StringType());
        query.addScalar("course_name", new StringType());
        query.addScalar("start_date", new StringType());


        query.setResultTransformer(Transformers.aliasToBean(StudentDTO.class));
        return query.list();
    }

    public List<StudentDTO> doSearch(StudentDTO studentDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select s.id as student_Id, s.user_id as user_Id,\n" +
                        "u.username as user_name, u.fullname as full_name,\n" +
                        "u.email as email, u.phone as phone, u.address as address,\n" +
                        "u.active as active, c.name as class_name, co.name as course_name\n" +
                        "from student s join users u on s.user_id = u.id\n" +
                        "join student_in_class sic on s.id = sic.student_id\n" +
                        "join class c on sic.class_id = c.id\n" +
                        "join course co on c.course_id = co.id");
        sql.append(" WHERE 1 = 1 ");
        if (studentDTO.getKey_search()!=null) {
            sql.append(" AND (UPPER(u.fullname) LIKE CONCAT('%', UPPER(:full_name), '%') ESCAPE '&') ");
        }

        sql.append(" order by s.id");

        NativeQuery<StudentDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (studentDTO.getKey_search()!=null) {
            query.setParameter("full_name", "%"+studentDTO.getKey_search()+"%");
        }

        query.addScalar("student_Id", new LongType());
        query.addScalar("user_Id", new LongType());
        query.addScalar("user_name", new StringType());
        query.addScalar("full_name", new StringType());
        query.addScalar("email", new StringType());
        query.addScalar("phone", new StringType());
        query.addScalar("address", new StringType());
        query.addScalar("active", new BooleanType());
        query.addScalar("class_name", new StringType());
        query.addScalar("course_name", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(StudentDTO.class));
        if (null != String.valueOf(studentDTO.getPage())) {
            query.setMaxResults(studentDTO.getPageSize());
            query.setFirstResult(((studentDTO.getPage() - 1) * studentDTO.getPageSize()));
        }
        return query.list();
    }

    public List<StudentDTO> getTotal(StudentDTO studentDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select s.id as student_Id, s.user_id as user_Id,\n" +
                        "u.username as user_name, u.fullname as full_name,\n" +
                        "u.email as email, u.phone as phone, u.address as address,\n" +
                        "u.active as active, c.name as class_name, co.name as course_name\n" +
                        "from student s join users u on s.user_id = u.id\n" +
                        "join student_in_class sic on s.id = sic.student_id\n" +
                        "join class c on sic.class_id = c.id\n" +
                        "join course co on c.course_id = co.id");
        sql.append(" WHERE 1 = 1 ");
        if (studentDTO.getKey_search()!=null) {
            sql.append(" AND (UPPER(u.fullname) LIKE CONCAT('%', UPPER(:full_name), '%') ESCAPE '&') ");
        }

        sql.append(" order by s.id");

        NativeQuery<StudentDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (studentDTO.getKey_search()!=null) {
            query.setParameter("full_name", "%"+studentDTO.getKey_search()+"%");
        }

        query.addScalar("student_Id", new LongType());
        query.addScalar("user_Id", new LongType());
        query.addScalar("user_name", new StringType());
        query.addScalar("full_name", new StringType());
        query.addScalar("email", new StringType());
        query.addScalar("phone", new StringType());
        query.addScalar("address", new StringType());
        query.addScalar("active", new BooleanType());
        query.addScalar("class_name", new StringType());
        query.addScalar("course_name", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(StudentDTO.class));
        query.list();
        return query.list();
    }


    public StudentDTO getStudent(StudentDTO studentDTO) {

        if(studentDTO.getUser_name()!=null){
            studentDTO.setUser_Id(userRepo.findByUsername(studentDTO.getUser_name()).getId());
        }

        StringBuilder sql = new StringBuilder()
                .append("select s.id as student_Id, s.user_id as user_Id, s.role_id as role_Id, \n" +
                        "u.username as user_name, u.fullname as full_name, u.email as email, u.phone as phone, u.address as address, u.active as active\n" +
                        "from student s join users u on s.user_id = u.id ");
        sql.append("WHERE 1 = 1 ");

        if (studentDTO.getUser_Id() != null) {
            sql.append(" AND s.user_id = :user_Id ");
        }

        NativeQuery<StudentDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (studentDTO.getUser_Id() != null) {
            query.setParameter("user_Id", studentDTO.getUser_Id());
        }

        query.addScalar("student_Id", new LongType());
        query.addScalar("user_Id", new LongType());
        query.addScalar("role_Id", new LongType());
        query.addScalar("user_name", new StringType());
        query.addScalar("full_name", new StringType());
        query.addScalar("email", new StringType());
        query.addScalar("phone", new StringType());
        query.addScalar("address", new StringType());
        query.addScalar("active", new BooleanType());

        query.setResultTransformer(Transformers.aliasToBean(StudentDTO.class));
        if (null != String.valueOf(studentDTO.getPage())) {
            query.setMaxResults(studentDTO.getPageSize());
            query.setFirstResult(((studentDTO.getPage() - 1) * studentDTO.getPageSize()));
        }
        return (StudentDTO) query.getSingleResult();
    }

    //bug
    public List<StudentInClassDTO> getListStudentByIdClass(Long id) {

        StringBuilder sql = new StringBuilder()
                .append("select sic.id as student_in_class_id, student_id as student_id, class_id as class_id, user_id as user_id, fullname as full_name, email as email,\n" +
                        "phone as phone, address as address from student_in_class sic join student s on sic.student_id = s.id join users u on s.user_id = u.id ");
        sql.append("WHERE 1 = 1 ");
        if(id!=null){
            sql.append(" AND class_id = :class_id ");
        }

        sql.append(" and sic.is_paid=true order by sic.id");

        NativeQuery<StudentInClassDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (id != null) {
            query.setParameter("class_id", id);
        }

        query.addScalar("student_in_class_id", new LongType());
        query.addScalar("student_id", new LongType());
        query.addScalar("class_id", new LongType());
        query.addScalar("user_id", new LongType());
        query.addScalar("full_name", new StringType());
        query.addScalar("email", new StringType());
        query.addScalar("phone", new StringType());
        query.addScalar("address", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(StudentInClassDTO.class));
        return query.list();
    }

    public List<StudentDTO> detailStudentClass(Long id) {
        StringBuilder sql = new StringBuilder()
                .append("select s.id as student_Id, s.user_id as user_Id, c.name as class_name,\n" +
                        "u.username as user_name, u.fullname as full_name, u.email as email, u.phone as phone, u.address as address, u.active as active,\n" +
                        "sic.from_date as from_date, sic.to_date as to_date\n" +
                        "from student s join users u on s.user_id = u.id join student_in_class sic on s.id = sic.student_id\n" +
                        "join class c on sic.class_id = c.id ");
        sql.append("WHERE 1 = 1 ");
        if(id != null){
            sql.append(" AND s.id = :id ");
        }

        sql.append(" order by s.id ");

        NativeQuery<StudentDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (id != null) {
            query.setParameter("id", id);
        }

        query.addScalar("student_Id", new LongType());
        query.addScalar("user_Id", new LongType());
        query.addScalar("class_name", new StringType());
        query.addScalar("user_name", new StringType());
        query.addScalar("full_name", new StringType());
        query.addScalar("email", new StringType());
        query.addScalar("phone", new StringType());
        query.addScalar("address", new StringType());
        query.addScalar("active", new BooleanType());
        query.addScalar("from_date", new LocalDateTimeType());
        query.addScalar("to_date", new LocalDateTimeType());

        query.setResultTransformer(Transformers.aliasToBean(StudentDTO.class));

        return query.list();
    }
}