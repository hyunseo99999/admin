package com.admin.core.jwt;

import com.admin.DummyObject;
import com.admin.core.service.LoginUser;
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
        user = createRoleUser();
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
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHVkeSIsInJvbGVzIjpbIkFETUlOIl0sImlkIjoxLCJleHAiOjE2ODI0NzI0ODB9.kFVzQNoSrr-kv0GlQO6nieacvQXNk97xs8hV75gNIXnHOc8KIIKcwlCh7c25Mx9GVySutlbwThGzlQ9X7BmoFw";
        LoginUser loginUser = JwtProcess.verify(jwtToken);
        assertThat(loginUser.getUser().getId()).isEqualTo(1L);
        assertThat(loginUser.getAuthorities()).isNotNull();
    }


    @Test
    @DisplayName("토큰 검증 실패")
    void jwtVerify_fail_test() {
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHVkeSIsInJvbGVzIjpbIkFETUlOIl0sImlkIjoxLCJleHAiOjE2ODI0NzI0ODB9.kFVzQNoSrr-kv0GlQO6nieacvQXNk97xs8hV75gNIXnHOc8KIIKcwlCh7c25Mx9GVySutlbwThGzlQ9X7Bmo";
        Assertions.assertThrows(SignatureVerificationException.class, () -> {
            JwtProcess.verify(jwtToken);
        });
    }

}