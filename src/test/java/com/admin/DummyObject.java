package com.admin;

import com.admin.domain.RoleGroup;
import com.admin.domain.RoleMapping;
import com.admin.domain.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DummyObject {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createRoleUser() {
        User user = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("1234"))
                .useYn("Y")
                .build();

        return user;
    }

    public RoleGroup createRoleGroup() {
        RoleGroup roleGroup = RoleGroup.builder()
                .roleCode("ADMIN")
                .roleNm("관리자")
                .roleDc("ADMIN")
                .build();
        return roleGroup;
    }
}
