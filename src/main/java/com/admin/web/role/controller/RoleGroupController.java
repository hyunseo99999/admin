package com.admin.web.role.controller;

import com.admin.response.ResponseDto;
import com.admin.response.ResponseUtil;
import com.admin.web.role.dto.RoleGroupReqDto;
import com.admin.web.role.dto.RoleGroupReqDto.RoleGroupSaveReqDto;
import com.admin.web.role.service.RoleGroupService;
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
@RequestMapping("/admin")
@RequiredArgsConstructor
public class RoleGroupController {

    private final RoleGroupService roleGroupService;

    @PostMapping("/role-group")
    public ResponseEntity<?> save(@RequestBody @Valid RoleGroupSaveReqDto roleGroupSaveDto) {
        roleGroupService.save(roleGroupSaveDto);
        return new ResponseEntity<>(new ResponseDto<>(201, "저장되었습니다.", null), HttpStatus.CREATED);
    }

}
