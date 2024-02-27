package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberSubRepositoryTest {

    @Autowired
    MemberRepository_sub memberRepositorySub;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testMember() throws Exception {
        // given
        Member_sub member = new Member_sub();
        member.setUsername("memberA");

        //when
        Long savedId = memberRepositorySub.save(member);
        Member_sub findmember = memberRepositorySub.find(savedId);

        //then
        Assertions.assertThat(findmember.getId()).isEqualTo(member.getId());

        Assertions.assertThat(findmember.getUsername()).isEqualTo(member.getUsername());
    }

}