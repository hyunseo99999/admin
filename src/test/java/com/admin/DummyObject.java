package com.admin;

import com.admin.domain.RoleGroup;
import com.admin.domain.RoleMapping;
import com.admin.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;

public class DummyObject {

    @PersistenceContext
    private EntityManager em;

    public User createRoleUser() {
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

        user.addRoleMapping(roleMapping);
        return user;
    }

    public RoleGroup createRoleGroup() {
        RoleGroup roleGroup = RoleGroup.builder()
                .roleCode("ADMIN")
                .roleNm("관리자")
                .roleDc("ADMIN")
                .build();
        em.persist(roleGroup);
        return roleGroup;
    }
}
