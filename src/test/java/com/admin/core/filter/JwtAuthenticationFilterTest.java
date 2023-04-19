package com.admin.core.filter;

import com.admin.DummyObject;
import com.admin.core.service.LoginUser;
import com.admin.web.user.dto.UserReqDto;
import com.admin.web.user.dto.UserReqDto.LoginReqDto;
import com.admin.web.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class JwtAuthenticationFilterTest extends DummyObject {

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void createUser() {
        createRoleUser();
    }

    @Test
    @DisplayName("로그인 성공")
    void successfulAuthentication_test() throws JsonProcessingException {
        LoginReqDto loginReqDto = new LoginReqDto();
        loginReqDto.setUsername("admin");
        loginReqDto.setPassword("1234");

        String requestBody = mapper.writeValueAsString(loginReqDto);
       /* ResultActions resultActions = mvc.pre*/


    }

    @Test
    @DisplayName("로그인 실패")
    void unsuccessfulAuthentication() {

    }

}