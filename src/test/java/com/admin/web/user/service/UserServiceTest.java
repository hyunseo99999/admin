package com.admin.web.user.service;

import com.admin.DummyObject;
import com.admin.exception.ex.CustomApiException;
import com.admin.web.user.dto.UserReqDto;
import com.admin.web.user.dto.UserReqDto.SignupReqDto;
import com.admin.web.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserServiceTest extends DummyObject {

    @Autowired
    private UserService userService;

    @BeforeEach
    void createBefore() {
        createRoleGroup();
    }

    @Test
    @DisplayName("사용자 등록")
    void createUser() {
        SignupReqDto signupReqDto = new SignupReqDto();
        signupReqDto.setUsername("admin");
        signupReqDto.setPassword("1234");
        signupReqDto.setRoleId("1");
        signupReqDto.setUseYn("Y");

        userService.save(signupReqDto);
    }

    @Test
    @DisplayName("사용자 중복")
    void dulipUser() {
        createUser();

        SignupReqDto signupReqDto = new SignupReqDto();
        signupReqDto.setUsername("admin");
        signupReqDto.setPassword("1234");
        signupReqDto.setRoleId("1");
        signupReqDto.setUseYn("Y");
        Assertions.assertThrows(CustomApiException.class, ()-> {
            userService.save(signupReqDto);
        });
    }

    @Test
    @DisplayName("권한그룹 아이디 존재하지 않을 경우")
    void roleIdsNull_test() {
        SignupReqDto signupReqDto = new SignupReqDto();
        signupReqDto.setUsername("admin");
        signupReqDto.setPassword("1234");
        signupReqDto.setRoleId("5");
        signupReqDto.setUseYn("Y");
        Assertions.assertThrows(CustomApiException.class, ()-> {
            userService.save(signupReqDto);
        });
    }
}