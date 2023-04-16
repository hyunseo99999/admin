package com.admin.web.user.controller;

import com.admin.response.ResponseUtil;
import com.admin.web.role.dto.RoleGroupReqDto;
import com.admin.web.user.dto.UserReqDto;
import com.admin.web.user.dto.UserReqDto.SignupReqDto;
import com.admin.web.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/insert")
    public ResponseEntity<?> userSave(@RequestBody @Valid SignupReqDto signupReqDto) {
        System.out.println("test1111");
        userService.save(signupReqDto);
        return new ResponseEntity<>(new ResponseUtil<>(201, "저장되었습니다."), HttpStatus.CREATED);
    }
}
