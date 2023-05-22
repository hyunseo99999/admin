package com.admin.core.filter;

import com.admin.DummyObject;
import com.admin.domain.user.RoleGroup;
import com.admin.domain.user.RoleMapping;
import com.admin.domain.user.User;
import com.admin.web.role.repository.RoleGroupRepository;
import com.admin.web.user.dto.UserReqDto.LoginReqDto;
import com.admin.web.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = MOCK)

class JwtAuthenticationFilterTest extends DummyObject {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleGroupRepository roleGroupRepository;

    @BeforeEach
    void setData() {
        RoleGroup roleGroup = roleGroupRepository.save(newRoleGroup("ADMIN", "관리자"));
        User user = newUser("admin", "관리자");
        RoleMapping roleMapping = RoleMapping.createRoleMapping(roleGroup);
        User saveUser = User.createUser(user, roleMapping);

        userRepository.save(saveUser);
    }

    @Test
    @DisplayName("로그인 성공")
    void successfulAuthentication_test() throws Exception {
        LoginReqDto loginReqDto = new LoginReqDto();
        loginReqDto.setLoginId("admin");
        loginReqDto.setPassword("1234");

        String requestBody = mapper.writeValueAsString(loginReqDto);
        ResultActions resultActions = mvc.perform(post("/login")
                                        .content(requestBody)
                                        .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions
                                .andReturn()
                                .getResponse()
                                .getContentAsString();
        System.out.println("테스트 : " + responseBody);
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 실패")
    void unsuccessfulAuthentication_test() throws Exception {
      LoginReqDto loginReqDto = new LoginReqDto();
      loginReqDto.setLoginId("admin");
      loginReqDto.setPassword("12345");

      String requestBody = mapper.writeValueAsString(loginReqDto);
      ResultActions resultActions = mvc.perform(post("/login")
                                      .content(requestBody)
                                      .contentType(MediaType.APPLICATION_JSON));

      String responseBody = resultActions
                              .andReturn()
                              .getResponse()
                              .getContentAsString();
      System.out.println("테스트 : " + responseBody);
      resultActions.andExpect(status().isUnauthorized());
    }

}