package com.example.JWTSecure.repo.impl;

import com.example.JWTSecure.DTO.CurriculumDTO;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository

public class CurriculumCustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CurriculumDTO> doSearch(CurriculumDTO curriculumDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select  cu.id as id, cu.course_id,cu.name as curriculum_name, cu.link_url, cu.description,\n" +
                        "cu.created_at, cu.updated_at, c.level_id, c.name as course_name\n" +
                        "from curriculum cu join course c on c.id = cu.course_id  ");
        sql.append(" WHERE 1 = 1 ");
        if(curriculumDTO.getKey_search()!=null){
            sql.append(" AND (UPPER(cu.name) LIKE CONCAT('%', UPPER(:name), '%') ESCAPE '&') ");
        }
        NativeQuery<CurriculumDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (curriculumDTO.getKey_search() != null) {
            query.setParameter("name", "%"+curriculumDTO.getKey_search()+"%");
        }

        query.addScalar("id", new LongType());
        query.addScalar("course_id", new LongType());
        query.addScalar("curriculum_name", new StringType());
        query.addScalar("link_url", new StringType());
        query.addScalar("description", new StringType());
        query.addScalar("created_at", new StringType());
        query.addScalar("updated_at", new StringType());
        query.addScalar("level_id", new LongType());
        query.addScalar("course_name", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(CurriculumDTO.class));
        if (null != String.valueOf(curriculumDTO.getPage())) {
            query.setMaxResults(curriculumDTO.getPageSize());
            query.setFirstResult(((curriculumDTO.getPage() - 1) * curriculumDTO.getPageSize()));
        }
        return query.list();
    }

    public List<CurriculumDTO> getTotal(CurriculumDTO curriculumDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select  cu.id as id, cu.course_id,cu.name as curriculum_name, cu.link_url, cu.description,\n" +
                        "cu.created_at, cu.updated_at, c.level_id, c.name as course_name\n" +
                        "from curriculum cu join course c on c.id = cu.course_id  ");
        sql.append(" WHERE 1 = 1 ");
        if(curriculumDTO.getKey_search()!=null){
            sql.append(" AND (UPPER(cu.name) LIKE CONCAT('%', UPPER(:name), '%') ESCAPE '&') ");
        }
        NativeQuery<CurriculumDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (curriculumDTO.getKey_search() != null) {
            query.setParameter("name", "%"+curriculumDTO.getKey_search()+"%");
        }

        query.addScalar("id", new LongType());
        query.addScalar("course_id", new LongType());
        query.addScalar("curriculum_name", new StringType());
        query.addScalar("link_url", new StringType());
        query.addScalar("description", new StringType());
        query.addScalar("created_at", new StringType());
        query.addScalar("updated_at", new StringType());
        query.addScalar("level_id", new LongType());
        query.addScalar("course_name", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(CurriculumDTO.class));
        return query.list();
    }
}
