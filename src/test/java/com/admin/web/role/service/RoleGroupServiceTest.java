package com.admin.web.role.service;

import com.admin.web.role.dto.RoleGroupReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.admin.web.role.dto.RoleGroupReqDto.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class RoleGroupServiceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("권한그룹 등록")
    void roleGroup_success_test() throws Exception {
        // given
        RoleGroupSaveReqDto roleGroupDto = new RoleGroupSaveReqDto();
        roleGroupDto.setRoleCode("ADMIN");
        roleGroupDto.setRoleNm("관리자");
        roleGroupDto.setRoleDc("관리자");
        // when

        String requestBody = mapper.writeValueAsString(roleGroupDto);

        ResultActions result = mvc.perform(post("/role-group/insert").content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = result.andReturn().getResponse().getContentAsString();
        int statusCode = result.andReturn().getResponse().getStatus();

        assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value());
        // then
    }
}