package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository_sub {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member_sub member) {
        em.persist(member);
        return member.getId(); // 커멘드랑 쿼리를 구별하기 위해!
    }

    public Member_sub find(Long id) {
        return em.find(Member_sub.class, id);
    }
}
