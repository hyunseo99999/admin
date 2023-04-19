package com.admin.core.jwt;

import com.admin.core.service.LoginUser;
import com.admin.domain.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class JwtProcess {

    public static String create(LoginUser loginUser) {
        Collection<? extends GrantedAuthority> authorities = loginUser.getAuthorities();

        ArrayList<String> authsList = new ArrayList<>(authorities.size());

        for (GrantedAuthority authority : authorities) {
            authsList.add(authority.getAuthority());
        }

        String token = JWT.create()
                .withSubject("study")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVO.EXPIRATION_TIME))
                .withClaim("id", loginUser.getUser().getId())
                .withClaim("roles", authsList)
                .sign(Algorithm.HMAC512(JwtVO.SECRET));
        return JwtVO.TOKEN_PREFIX + token;
    }

    public static LoginUser verify(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtVO.SECRET)).build().verify(token);
        Long id = decodedJWT.getClaim("id").asLong();
        List<String> userRoles = decodedJWT.getClaim("roles").asList(String.class);
        List<GrantedAuthority> roles = userRoles
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User user = User.builder()
                .id(id)
                .build();
        LoginUser loginUser = new LoginUser(user, roles);
        return loginUser;
    }

}
