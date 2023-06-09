package com.admin.web.user.controller;

import com.admin.response.ResponseDto;
import com.admin.response.ResponseUtil;
import com.admin.web.role.dto.RoleGroupReqDto;
import com.admin.web.user.dto.UserReqDto;
import com.admin.web.user.dto.UserReqDto.SignupReqDto;
import com.admin.web.user.dto.UserRespDto;
import com.admin.web.user.dto.UserRespDto.UserDetailRespDto;
import com.admin.web.user.dto.UserRespDto.UserListRespDto;
import com.admin.web.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> userSave(@RequestBody @Valid SignupReqDto signupReqDto) {
        userService.save(signupReqDto);
        return new ResponseEntity<>(new ResponseDto<>(201, "저장되었습니다.", null), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<?> findUsers() {
        List<UserListRespDto> findListUser = userService.findListUser();
        return new ResponseEntity<>(new ResponseDto<>(200, "조회되었습니다", findListUser), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findUser(@PathVariable Long id) {
        UserDetailRespDto detailUser = userService.findOneUser(id);
        return new ResponseEntity<>(new ResponseDto<>(200, "조회되었습니다", detailUser), HttpStatus.OK);
    }
}
