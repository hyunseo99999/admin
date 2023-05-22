package com.admin;

import com.admin.domain.user.RoleGroup;
import com.admin.domain.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DummyObject {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RoleGroup newMockRoleGroup(String roleCode, String roleNm) {
        return RoleGroup
                .builder()
                .id(1L)
                .roleCode(roleCode)
                .roleNm(roleNm)
                .roleDc(roleNm)
                .build();
    }

    public RoleGroup newRoleGroup(String roleCode, String roleNm) {
      return RoleGroup
              .builder()
              .roleCode(roleCode)
              .roleNm(roleNm)
              .roleDc(roleNm)
              .build();
    }


    public User newMockUser(String loginId, String username) {
       return User.builder()
                .id(1L)
                .email(loginId + "@test.com")
                .useYn("Y")
                .loginId(loginId)
                .username(username)
                .password(passwordEncoder.encode("1234"))
                .build();
    }

    public User newUser(String loginId, String username) {
        return User.builder()
              .email(loginId + "@test.com")
              .useYn("Y")
              .loginId(loginId)
              .username(username)
              .password(passwordEncoder.encode("1234"))
              .build();
    }

}
