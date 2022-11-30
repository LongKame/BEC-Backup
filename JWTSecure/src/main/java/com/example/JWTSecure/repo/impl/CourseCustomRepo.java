package com.example.JWTSecure.repo.impl;

import com.example.JWTSecure.DTO.CourseDTO;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CourseCustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CourseDTO> doSearch(CourseDTO courseDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select c.id, c.level_id as levelId,\n" +
                        "c.name as course_name, c.fee as fee, l.name as level, c.created_at as createdAt,\n" +
                        "c.updated_at as updatedAt, c.number_slot as numberSlot\n" +
                        "from course c join level l on c.level_id = l.id");
        sql.append(" WHERE 1 = 1 ");
        if (courseDTO.getKey_search()!=null) {
            sql.append(" AND (UPPER(c.name) LIKE CONCAT('%', UPPER(:name), '%') ESCAPE '&') ");
        }

        sql.append(" order by c.id ");

        NativeQuery<CourseDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (courseDTO.getKey_search()!=null) {
            query.setParameter("name", "%"+courseDTO.getKey_search()+"%");
        }

        query.addScalar("id", new LongType());
        query.addScalar("levelId", new LongType());
        query.addScalar("course_name", new StringType());
        query.addScalar("fee", new DoubleType());
        query.addScalar("level", new StringType());
        query.addScalar("createdAt", new StringType());
        query.addScalar("updatedAt", new StringType());
        query.addScalar("numberSlot", new IntegerType());

        query.setResultTransformer(Transformers.aliasToBean(CourseDTO.class));
        if (null != String.valueOf(courseDTO.getPage())) {
            query.setMaxResults(courseDTO.getPageSize());
            query.setFirstResult(((courseDTO.getPage() - 1) * courseDTO.getPageSize()));
        }
        return query.list();
    }

    public List<CourseDTO> getTotal(CourseDTO courseDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select c.id, c.level_id as levelId,\n" +
                        "c.name as course_name, c.fee as fee, l.name as level, c.created_at as createdAt,\n" +
                        "c.updated_at as updatedAt, c.number_slot as numberSlot\n" +
                        "from course c join level l on c.level_id = l.id");
        sql.append(" WHERE 1 = 1 ");
        if (courseDTO.getKey_search()!=null) {
            sql.append(" AND (UPPER(c.name) LIKE CONCAT('%', UPPER(:name), '%') ESCAPE '&') ");
        }

        sql.append(" order by c.id ");

        NativeQuery<CourseDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (courseDTO.getKey_search()!=null) {
            query.setParameter("name", "%"+courseDTO.getKey_search()+"%");
        }

        query.addScalar("id", new LongType());
        query.addScalar("levelId", new LongType());
        query.addScalar("course_name", new StringType());
        query.addScalar("fee", new DoubleType());
        query.addScalar("level", new StringType());
        query.addScalar("createdAt", new StringType());
        query.addScalar("updatedAt", new StringType());
        query.addScalar("numberSlot", new IntegerType());

        query.setResultTransformer(Transformers.aliasToBean(CourseDTO.class));
        return query.list();
    }

}
