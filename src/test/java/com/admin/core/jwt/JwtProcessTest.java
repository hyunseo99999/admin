package com.admin.core.jwt;

import com.admin.DummyObject;
import com.admin.core.service.LoginUser;
import com.admin.domain.user.RoleGroup;
import com.admin.domain.user.RoleMapping;
import com.admin.domain.user.User;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class JwtProcessTest extends DummyObject {

    private User user;

    @PersistenceContext
    private EntityManager em;


    @BeforeEach
    void createUser() {
        RoleGroup roleGroup = createRoleGroup();
        user = createRoleUser();
        RoleMapping roleMapping = RoleMapping.createRoleMapping(roleGroup);
        List<RoleMapping> roles = new ArrayList<>();
        roles.add(roleMapping);
        user.setRoleMappings(roles);
    }

    public String createToken() {
        List<GrantedAuthority> roles = user.getRoleMappings()
                .stream().map(item -> new SimpleGrantedAuthority(item.getRoleGroup().getRoleCode()))
                .collect(Collectors.toList());

        LoginUser loginUser = new LoginUser(user, roles);
        String jwtToken = JwtProcess.create(loginUser);
        return jwtToken;
    }

    @Test
    @DisplayName("토큰 생성")
    void jwtCreate_test() {
        List<GrantedAuthority> roles = user.getRoleMappings()
                .stream().map(item -> new SimpleGrantedAuthority(item.getRoleGroup().getRoleCode()))
                .collect(Collectors.toList());

        LoginUser loginUser = new LoginUser(user, roles);
        String jwtToken = JwtProcess.create(loginUser);
        System.out.println("테스트 =>" + jwtToken);
        assertThat(jwtToken.startsWith(JwtVO.TOKEN_PREFIX)).isTrue();
    }

    @Test
    @DisplayName("토큰 검증")
    void jwtVerify_test() {
        String jwtToken = createToken();
        String finalJwtToken = jwtToken.replace(JwtVO.TOKEN_PREFIX, "");
        LoginUser loginUser = JwtProcess.verify(finalJwtToken);
        assertThat(loginUser.getAuthorities()).isNotNull();
    }


    @Test
    @DisplayName("토큰 검증 실패")
    void jwtVerify_fail_test() {
        String jwtToken = createToken() + "aaa";
        String finalJwtToken = jwtToken.replace(JwtVO.TOKEN_PREFIX, "");
        Assertions.assertThrows(SignatureVerificationException.class, () -> {
            JwtProcess.verify(finalJwtToken);
        });
    }

}