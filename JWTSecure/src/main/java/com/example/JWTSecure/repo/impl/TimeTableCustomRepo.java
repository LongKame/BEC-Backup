package com.example.JWTSecure.repo.impl;

import com.example.JWTSecure.DTO.SearchTimeTable;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public class TimeTableCustomRepo {
    @PersistenceContext
    private EntityManager entityManager;

    public SearchTimeTable doSearch(Long id) {

        StringBuilder sql = new StringBuilder()
                .append("select c.id as class_id, c.name as class_name, c.start_date, c.end_date, cs.first_of_week, cs.second_of_week,\n" +
                        "r.roomname as room_name, co.name as course_name, co.number_slot,s.from_time, s.to_time, cs.slot_id as slot_id\n" +
                        "from class c join class_slot cs on c.id = cs.class_id join room r on c.room_id = r.id\n" +
                        "join course co on c.course_id = co.id join slot s on cs.slot_id = s.id");
        sql.append(" where 1=1 ");
        if (id!=null) {
            sql.append(" AND c.id = :id ");
        }
        NativeQuery<SearchTimeTable> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());
        if (id!=null) {
            query.setParameter("id", id);
        }
        query.addScalar("class_name", new StringType());
        query.addScalar("start_date", new StringType());
        query.addScalar("end_date", new StringType());
        query.addScalar("first_of_week", new StringType());
        query.addScalar("second_of_week", new StringType());
        query.addScalar("room_name", new StringType());
        query.addScalar("course_name", new StringType());
        query.addScalar("number_slot", new IntegerType());
        query.addScalar("from_time", new StringType());
        query.addScalar("to_time", new StringType());
        query.addScalar("slot_id", new IntegerType());
        query.setResultTransformer(Transformers.aliasToBean(SearchTimeTable.class));
        return (SearchTimeTable) query.getSingleResult();
    }

}

