package com.example.JWTSecure.repo.impl;


import com.example.JWTSecure.DTO.RoomDTO;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoomCustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RoomDTO> doSearch(RoomDTO roomDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select id, roomname, capacity," +
                        "created_at as createdAt, updated_at as updatedAt, active " +
                        "from room ");
        sql.append(" WHERE 1 = 1 ");
        if (roomDTO.getKey_search()!=null) {
            sql.append(" AND (UPPER(roomname) LIKE CONCAT('%', UPPER(:roomname), '%') ESCAPE '&') ");
        }

        sql.append(" order by id");

        NativeQuery<RoomDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (roomDTO.getKey_search()!=null) {
            query.setParameter("roomname", "%"+roomDTO.getKey_search()+"%");
        }

        query.addScalar("id", new LongType());
        query.addScalar("roomname", new StringType());
        query.addScalar("capacity", new IntegerType());
        query.addScalar("createdat", new StringType());
        query.addScalar("updatedat", new StringType());
        query.addScalar("active", new BooleanType());


        query.setResultTransformer(Transformers.aliasToBean(RoomDTO.class));
        if (null != String.valueOf(roomDTO.getPage())) {
            query.setMaxResults(roomDTO.getPageSize());
            query.setFirstResult(((roomDTO.getPage() - 1) * roomDTO.getPageSize()));
        }
        return query.list();
    }

    public List<RoomDTO> getTotal(RoomDTO roomDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select id, roomname, capacity," +
                        "created_at as createdAt, updated_at as updatedAt, active " +
                        "from room ");
        sql.append(" WHERE 1 = 1 ");
        if (roomDTO.getKey_search()!=null) {
            sql.append(" AND (UPPER(roomname) LIKE CONCAT('%', UPPER(:roomname), '%') ESCAPE '&') ");
        }

        sql.append(" order by id");

        NativeQuery<RoomDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (roomDTO.getKey_search()!=null) {
            query.setParameter("roomname", "%"+roomDTO.getKey_search()+"%");
        }

        query.addScalar("id", new LongType());
        query.addScalar("roomname", new StringType());
        query.addScalar("capacity", new IntegerType());
        query.addScalar("createdat", new StringType());
        query.addScalar("updatedat", new StringType());
        query.addScalar("active", new BooleanType());


        query.setResultTransformer(Transformers.aliasToBean(RoomDTO.class));
        return query.list();
    }
}
