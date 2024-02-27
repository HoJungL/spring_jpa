package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 스프링빈으로 자동 등록
@RequiredArgsConstructor
public class MemberRepository {
    // @Required~쓰면 이거 안써도됨.   @PersistenceContext
    private final EntityManager em;

    public void save (Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); //id는 pk임
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member as m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member as m where m.name =:name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
    
}
