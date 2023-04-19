package com.admin.core.filter;

import com.admin.DummyObject;
import com.admin.domain.RoleGroup;
import com.admin.domain.RoleMapping;
import com.admin.domain.User;
import com.admin.web.role.repository.RoleGroupRepository;
import com.admin.web.user.dto.UserReqDto.LoginReqDto;
import com.admin.web.user.repository.UserRepository;
import com.admin.web.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class JwtAuthenticationFilterTest extends DummyObject {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        user.addRoleMapping(roleMapping);
        userRepository.save(createRoleUser());
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