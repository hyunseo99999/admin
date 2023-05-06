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
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
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
    public void createUser() {
        RoleGroup roleGroup = roleGroupRepository.save(createRoleGroup());

        User user = createRoleUser();
        RoleMapping roleMapping = RoleMapping
                .builder()
                .user(user)
                .roleGroup(roleGroup)
                .build();

        roleMapping.setCreateId(1L);

        user.addRoleMapping(roleMapping);
        user.setCreateId(1L);
        userRepository.save(user);
    }

    @Test
    @DisplayName("로그인 성공")
    void successfulAuthentication_test() throws Exception {
        LoginReqDto loginReqDto = new LoginReqDto();
        loginReqDto.setUsername("admin");
        loginReqDto.setPassword("1234");

        String requestBody = mapper.writeValueAsString(loginReqDto);
        ResultActions resultActions = mvc.perform(post("/login").content(requestBody).contentType(MediaType.APPLICATION_JSON));

        System.out.println("테스트 => " + resultActions.andReturn().getResponse().getContentAsString());
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 실패")
    void unsuccessfulAuthentication() throws Exception {
        LoginReqDto loginReqDto = new LoginReqDto();
        loginReqDto.setUsername("admin1");
        loginReqDto.setPassword("1234");

        String requestBody = mapper.writeValueAsString(loginReqDto);
        ResultActions resultActions = mvc.perform(post("/login").content(requestBody).contentType(MediaType.APPLICATION_JSON));

        System.out.println("테스트 => " + resultActions.andReturn().getResponse().getContentAsString());
        resultActions.andExpect(status().isUnauthorized());
    }

}