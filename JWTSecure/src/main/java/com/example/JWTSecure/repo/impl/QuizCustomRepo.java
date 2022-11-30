package com.example.JWTSecure.repo.impl;


import com.example.JWTSecure.DTO.QuizDTO;
import com.example.JWTSecure.domain.Quiz;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository

public class QuizCustomRepo {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Quiz> multipleChoice(Long levelId) {

        StringBuilder sql = new StringBuilder()
                .append("SELECT id, aca_id as acaId, level_id as levelId, " +
                        "question, answer_a as answerA, answer_b as answerB, answer_c as answerC, answer_d as answerD, correct \n" +
                        "FROM quiz");
        sql.append(" WHERE 1 = 1 ");
        if(levelId!=null){
            sql.append(" AND level_id = :level_id ORDER BY RANDOM()  LIMIT 40");
        }

        NativeQuery<Quiz> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (levelId != null) {
            query.setParameter("level_id", levelId);
        }

        query.addScalar("id", new LongType());
        query.addScalar("acaId", new LongType());
        query.addScalar("levelId", new LongType());
        query.addScalar("question", new StringType());
        query.addScalar("answerA", new StringType());
        query.addScalar("answerB", new StringType());
        query.addScalar("answerC", new StringType());
        query.addScalar("answerD", new StringType());
        query.addScalar("correct", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(Quiz.class));
        return query.list();
    }

    public List<QuizDTO> doSearch(QuizDTO quizDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select q.id, q.question, q.answer_a as answerA, q.answer_b as answerB,\n" +
                        "q.answer_c as answerC, q.answer_d as answerD, q.correct,\n" +
                        "q.created_at as createdAt, q.updated_at as updatedAt\n" +
                        "from quiz q  ");
        sql.append(" WHERE 1 = 1 ");
        if(quizDTO.getKey_search()!=null){
            sql.append(" AND (UPPER(q.question) LIKE CONCAT('%', UPPER(:question), '%') ESCAPE '&') ");
        }

        sql.append(" order by q.id");

        NativeQuery<QuizDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (quizDTO.getKey_search() != null) {
            query.setParameter("question", "%"+quizDTO.getKey_search()+"%");
        }

        query.addScalar("id", new LongType());
        query.addScalar("question", new StringType());
        query.addScalar("answerA", new StringType());
        query.addScalar("answerB", new StringType());
        query.addScalar("answerC", new StringType());
        query.addScalar("answerD", new StringType());
        query.addScalar("correct", new StringType());
        query.addScalar("createdAt", new StringType());
        query.addScalar("updatedAt", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(QuizDTO.class));
        if (null != String.valueOf(quizDTO.getPage())) {
            query.setMaxResults(quizDTO.getPageSize());
            query.setFirstResult(((quizDTO.getPage() - 1) * quizDTO.getPageSize()));
        }
        return query.list();
    }

    public List<QuizDTO> getTotal(QuizDTO quizDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select q.id, q.question, q.answer_a as answerA, q.answer_b as answerB,\n" +
                        "q.answer_c as answerC, q.answer_d as answerD, q.correct,\n" +
                        "q.created_at as createdAt, q.updated_at as updatedAt\n" +
                        "from quiz q  ");
        sql.append(" WHERE 1 = 1 ");
        if(quizDTO.getKey_search()!=null){
            sql.append("   AND (UPPER(q.question) LIKE CONCAT('%', UPPER(:question), '%') ESCAPE '&') ");
        }

        sql.append(" order by q.id");

        NativeQuery<QuizDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (quizDTO.getKey_search() != null) {
            query.setParameter("question", "%"+quizDTO.getKey_search()+"%");
        }

        query.addScalar("id", new LongType());
        query.addScalar("question", new StringType());
        query.addScalar("answerA", new StringType());
        query.addScalar("answerB", new StringType());
        query.addScalar("answerC", new StringType());
        query.addScalar("answerD", new StringType());
        query.addScalar("correct", new StringType());
        query.addScalar("createdAt", new StringType());
        query.addScalar("updatedAt", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(QuizDTO.class));
        return query.list();
    }

}
