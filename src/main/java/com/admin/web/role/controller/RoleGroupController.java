package com.admin.web.role.controller;

import com.admin.response.ResponseDto;
import com.admin.response.ResponseUtil;
import com.admin.web.role.dto.RoleGroupReqDto;
import com.admin.web.role.dto.RoleGroupReqDto.RoleGroupSaveReqDto;
import com.admin.web.role.dto.RoleGroupRespDto;
import com.admin.web.role.service.RoleGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.admin.web.role.dto.RoleGroupRespDto.*;

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
    public ResponseEntity<?> update(@RequestBody @Valid RoleGroupSaveReqDto roleGroupSaveDto, BindingResult bindingResult) {
        roleGroupService.save(roleGroupSaveDto);
        return new ResponseEntity<>(new ResponseDto<>(204, "수정되었습니다.", null), HttpStatus.CREATED);
    }


    @GetMapping("/role-group")
    public ResponseEntity<?> roleGroups() {
        List<RoleGroupListRespDto> findByRoleGroups = roleGroupService.findListRoleGroup();
        return new ResponseEntity<>(new ResponseDto<>(200, "조회되었습니다.", findByRoleGroups), HttpStatus.OK);
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
