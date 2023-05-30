package com.admin.web.role.controller;

import com.admin.response.ResponseDto;
import com.admin.response.ResponsePageDto;
import com.admin.web.role.dto.RoleGroupReqDto.RoleGroupSaveReqDto;
import com.admin.web.role.dto.RoleGroupReqDto.RoleGroupUpdateReqDto;
import com.admin.web.role.service.RoleGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.admin.web.role.dto.RoleGroupRespDto.RoleGroupDetailRespDto;
import static com.admin.web.role.dto.RoleGroupRespDto.RoleGroupListRespDto;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class RoleGroupController {

    private final RoleGroupService roleGroupService;

    @PostMapping("/role-group")
    public ResponseEntity<?> save(@RequestBody @Valid RoleGroupSaveReqDto roleGroupSaveDto, BindingResult bindingResult) {
        roleGroupService.save(roleGroupSaveDto);
        return new ResponseEntity<>(new ResponseDto<>(201, "저장되었습니다.", null), HttpStatus.CREATED);
    }

    @PutMapping("/role-group")
    public ResponseEntity<?> update(@RequestBody @Valid RoleGroupUpdateReqDto roleGroupUpdateReqDto, BindingResult bindingResult) {
        roleGroupService.updateRoleGroup(roleGroupUpdateReqDto);
        return new ResponseEntity<>(new ResponseDto<>(204, "수정되었습니다.", null), HttpStatus.CREATED);
    }


    @GetMapping("/role-group")
    public ResponseEntity<?> roleGroups(Pageable pageable) {
        Page<RoleGroupListRespDto> roleGroups = roleGroupService.findListRoleGroup(pageable);
        return new ResponseEntity<>(new ResponsePageDto<>(200, "조회되었습니다.", roleGroups), HttpStatus.OK);
    }

    @GetMapping("/role-group/{id}")
    public ResponseEntity<?> roleGroupDetail(@PathVariable Long id) {
        RoleGroupDetailRespDto findOneRoleGroup = roleGroupService.findOneRoleGroup(id);
        return new ResponseEntity<>(new ResponseDto<>(200, "조회되었습니다.", findOneRoleGroup), HttpStatus.OK);
    }

    @DeleteMapping("/role-group/{id}")
    public ResponseEntity<?> deleteRoleGroup(@PathVariable Long id) {
        roleGroupService.deleteRoleGroup(id);
        return new ResponseEntity<>(new ResponseDto<>(200, "삭제되었습니다.", null), HttpStatus.OK);
    }

}
