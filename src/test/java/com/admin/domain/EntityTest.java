package com.admin.domain;

import com.admin.domain.user.RoleGroup;
import com.admin.domain.user.RoleMapping;
import com.admin.domain.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@Profile("test")
@SpringBootTest
@Transactional
public class EntityTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("권한그룹")
    public void createRoleGroup() {
        RoleGroup roleGroup = RoleGroup
                .builder()
                .roleCode("ADMIN")
                .roleNm("관리자")
                .roleDc("관리자")
                .build();

        em.persist(roleGroup);
        em.flush();
        em.clear();

        RoleGroup findRoleGroup = em.find(RoleGroup.class, 1L);
        assertThat(findRoleGroup.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("사용자 등록")
    public void createUser() {
        User user = User.builder()
                .email("admin@test.com")
                .useYn("Y")
                .loginId("admin")
                .username("관리자")
                .password(passwordEncoder.encode("1234"))
                .build();

        em.persist(user);
        em.flush();
        em.clear();

        User findUser = em.find(User.class, 1L);
        assertThat(findUser.getId()).isEqualTo(1L);
    }

    @Test
    void roleMapping_test() {
        RoleGroup roleGroup = RoleGroup
                .builder()
                .roleCode("ADMIN")
                .roleNm("관리자")
                .roleDc("관리자")
                .build();

        em.persist(roleGroup);

        User user = User.builder()
                       .email("admin@test.com")
                       .useYn("Y")
                       .loginId("admin")
                       .username("관리자")
                       .password(passwordEncoder.encode("1234"))
                       .build();
        em.persist(user);

        RoleMapping roleMapping = RoleMapping
                .builder()
                .roleGroup(roleGroup)
                .user(user)
                .build();
        em.persist(roleMapping);

        em.flush();
        em.clear();

        User findUser = em.find(User.class, 1L);
        System.out.println("테스트 : " + findUser.getRoleMappings().get(0));
        assertThat(findUser.getRoleMappings().get(0).getRoleGroup().getRoleCode()).isEqualTo("ADMIN");
    }
}
