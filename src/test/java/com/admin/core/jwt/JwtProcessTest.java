package com.admin.core.jwt;

import com.admin.core.service.LoginUser;
import com.admin.domain.user.RoleGroup;
import com.admin.domain.user.RoleMapping;
import com.admin.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class JwtProcessTest {

    public String createToken() {
        RoleGroup roleGroup = RoleGroup.builder()
                        .roleCode("ADMIN")
                        .build();

        User user = User.builder()
                .id(1L)
                .username("관리자")
                .build();

        RoleMapping roleMapping = RoleMapping.builder()
                .user(user)
                .roleGroup(roleGroup)
                .build();

        user.addRoleMapping(roleMapping);

        List<GrantedAuthority> authorities = new ArrayList<>();
               if (!user.getRoleMappings().isEmpty()) {
                   List<String> roles = user.getRoleMappings()
                           .stream()
                           .map(role -> "ROLE_" + role.getRoleGroup().getRoleCode())
                           .collect(Collectors.toList());

                   authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
               }


        LoginUser loginUser = new LoginUser(user, authorities);
        String token = JwtProcess.create(loginUser);
        return token;

    }

    @Test
    @DisplayName("토큰 발행")
    void createToken_test() {
        RoleGroup roleGroup = RoleGroup.builder()
                        .roleCode("ADMIN")
                        .build();

        User user = User.builder()
                .id(1L)
                .username("관리자")
                .build();

        RoleMapping roleMapping = RoleMapping.builder()
                .user(user)
                .roleGroup(roleGroup)
                .build();

        user.addRoleMapping(roleMapping);

        List<GrantedAuthority> authorities = new ArrayList<>();
               if (!user.getRoleMappings().isEmpty()) {
                   List<String> roles = user.getRoleMappings()
                           .stream()
                           .map(role -> "ROLE_" + role.getRoleGroup().getRoleCode())
                           .collect(Collectors.toList());

                   authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
               }


        LoginUser loginUser = new LoginUser(user, authorities);
        String token = JwtProcess.create(loginUser);
        System.out.println("테스트: " + token);
        assertThat(token.startsWith(JwtVO.TOKEN_PREFIX)).isTrue();
    }

    @Test
    @DisplayName("토큰 검증")
    void verify_test() {
        String token = createToken();
        token = token.replace(JwtVO.TOKEN_PREFIX, "");
        LoginUser loginUser = JwtProcess.verify(token);

        List<? extends GrantedAuthority> authorities = loginUser
                                                            .getAuthorities()
                                                            .stream()
                                                            .collect(Collectors.toList());

        assertThat(loginUser.getUser().getId()).isEqualTo(1L);
        assertThat(authorities).extracting("role").containsExactly("ROLE_ADMIN");
    }

}