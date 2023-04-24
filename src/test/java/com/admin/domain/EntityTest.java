package com.admin.domain;

import com.admin.domain.user.RoleGroup;
import com.admin.domain.user.RoleMapping;
import com.admin.domain.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class EntityTest {
    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("사용자 테스트")
    void user_save_test() {
        User user = User.builder()
                .username("admin")
                .password("1234")
                .useYn("Y")
                .build();

        em.persist(user);
    }

    @Test
    @DisplayName("권한그룹 테스트")
    void roleGroup_save_test() {
        RoleGroup roleGroup = RoleGroup.builder()
                .roleNm("ADMIN")
                .roleDc("ADMIN")
                .build();

        em.persist(roleGroup);
    }

    @Test
    @DisplayName("사용자_권한그룹_테스트")
    @Rollback(value = false)
    void user_roleGroup_save_test() {
        RoleGroup roleGroup = RoleGroup.builder()
                .roleCode("ADMIN")
                .roleNm("관리자")
                .roleDc("ADMIN")
                .build();
        em.persist(roleGroup);

        User user = User.builder()
                .username("admin")
                .password("1234")
                .useYn("Y")
                .build();
        em.persist(user);

        RoleMapping roleMapping = RoleMapping
                .builder()
                .roleGroup(roleGroup)
                .user(user)
                .build();
        em.persist(roleMapping);
    }
}