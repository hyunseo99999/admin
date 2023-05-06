package com.admin;

import com.admin.domain.user.RoleGroup;
import com.admin.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

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

        roleGroup.setCreateId(1L);
        return roleGroup;
    }

}
