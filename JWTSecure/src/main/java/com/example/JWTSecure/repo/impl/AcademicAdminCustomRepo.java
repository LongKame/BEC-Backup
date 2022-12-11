package com.example.JWTSecure.repo.impl;
import com.example.JWTSecure.DTO.AcademicAdminDTO;
import com.example.JWTSecure.DTO.CurriculumDTO;
import com.example.JWTSecure.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class AcademicAdminCustomRepo {
    @PersistenceContext
    private EntityManager entityManager;

    private final UserRepo userRepo;

    public List<AcademicAdminDTO> doSearch(AcademicAdminDTO academicAdminDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select s.id as acad_Id, s.user_id as user_Id, s.role_id as role_Id,\n" +
                        "u.username as user_name, u.fullname as full_name, u.email as email, u.phone as phone, u.address as address, u.active as active\n" +
                        "from academic_admin s join users u on s.user_id = u.id ");
        sql.append(" WHERE 1 = 1 ");
        if(academicAdminDTO.getKey_search()!=null){
            sql.append(" AND (UPPER(u.fullname) LIKE CONCAT('%', UPPER(:full_name), '%') ESCAPE '&')");
        }

        sql.append(" order by s.id ");

        NativeQuery<AcademicAdminDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (academicAdminDTO.getKey_search() != null) {
            query.setParameter("full_name", "%"+academicAdminDTO.getKey_search()+"%");
        }

        query.addScalar("acad_Id", new LongType());
        query.addScalar("user_Id", new LongType());
        query.addScalar("role_Id", new LongType());
        query.addScalar("user_name", new StringType());
        query.addScalar("full_name", new StringType());
        query.addScalar("email", new StringType());
        query.addScalar("phone", new StringType());
        query.addScalar("address", new StringType());
        query.addScalar("active", new BooleanType());

        query.setResultTransformer(Transformers.aliasToBean(AcademicAdminDTO.class));
        if (null != String.valueOf(academicAdminDTO.getPage())) {
            query.setMaxResults(academicAdminDTO.getPageSize());
            query.setFirstResult(((academicAdminDTO.getPage() - 1) * academicAdminDTO.getPageSize()));
        }
        return query.list();
    }

    public List<AcademicAdminDTO> getTotal(AcademicAdminDTO academicAdminDTO) {

        StringBuilder sql = new StringBuilder()
                .append("select s.id as acad_Id, s.user_id as user_Id, s.role_id as role_Id,\n" +
                        "u.username as user_name, u.fullname as full_name, u.email as email, u.phone as phone, u.address as address, u.active as active\n" +
                        "from academic_admin s join users u on s.user_id = u.id ");
        sql.append(" WHERE 1 = 1 ");
        if(academicAdminDTO.getKey_search()!=null){
            sql.append(" AND (UPPER(u.fullname) LIKE CONCAT('%', UPPER(:full_name), '%') ESCAPE '&') ");
        }

        sql.append(" order by s.id ");

        NativeQuery<AcademicAdminDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (academicAdminDTO.getKey_search() != null) {
            query.setParameter("full_name", "%"+academicAdminDTO.getKey_search()+"%");
        }

        query.addScalar("acad_Id", new LongType());
        query.addScalar("user_Id", new LongType());
        query.addScalar("role_Id", new LongType());
        query.addScalar("user_name", new StringType());
        query.addScalar("full_name", new StringType());
        query.addScalar("email", new StringType());
        query.addScalar("phone", new StringType());
        query.addScalar("address", new StringType());
        query.addScalar("active", new BooleanType());

        query.setResultTransformer(Transformers.aliasToBean(AcademicAdminDTO.class));
        return query.list();
    }

    public AcademicAdminDTO getAca(AcademicAdminDTO academicAdminDTO) {
        if(academicAdminDTO.getUser_name()!=null){
            academicAdminDTO.setUser_Id(userRepo.findByUsername(academicAdminDTO.getUser_name()).getId());
        }

        StringBuilder sql = new StringBuilder()
                .append("select s.id as acad_Id, s.user_id as user_Id, s.role_id as role_Id, u.username as user_name, u.fullname as full_name, u.email as email,\n" +
                        "u.phone as phone, u.address as address, u.active as active from academic_admin s join users u on s.user_id = u.id ");
        sql.append("WHERE 1 = 1 ");
        if(academicAdminDTO.getUser_Id()!=null){
            sql.append(" AND s.user_id = :user_Id ");
        }

        NativeQuery<AcademicAdminDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        if (academicAdminDTO.getUser_Id() != null) {
            query.setParameter("user_Id", academicAdminDTO.getUser_Id());
        }

        query.addScalar("acad_Id", new LongType());
        query.addScalar("user_Id", new LongType());
        query.addScalar("role_Id", new LongType());
        query.addScalar("user_name", new StringType());
        query.addScalar("full_name", new StringType());
        query.addScalar("email", new StringType());
        query.addScalar("phone", new StringType());
        query.addScalar("address", new StringType());
        query.addScalar("active", new BooleanType());

        query.setResultTransformer(Transformers.aliasToBean(AcademicAdminDTO.class));
        return (AcademicAdminDTO) query.getSingleResult();

    }

    public List<CurriculumDTO> getCurriculum() {
        StringBuilder sql = new StringBuilder()
                .append("select cu.id as id, cu.course_id as course_id, cu.name as curriculum_name,\n" +
                        "cu.link_url as link_url, cu.description as description, co.name as course_name\n" +
                        "from curriculum cu join course co on co.id = cu.course_id order by cu.id ");

        NativeQuery<CurriculumDTO> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

        query.addScalar("id", new LongType());
        query.addScalar("course_id", new LongType());
        query.addScalar("curriculum_name", new StringType());
        query.addScalar("link_url", new StringType());
        query.addScalar("description", new StringType());
        query.addScalar("course_name", new StringType());

        query.setResultTransformer(Transformers.aliasToBean(CurriculumDTO.class));
        return query.list();
    }
}