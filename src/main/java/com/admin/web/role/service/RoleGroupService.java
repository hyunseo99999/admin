package com.admin.web.role.service;

import com.admin.domain.user.RoleGroup;
import com.admin.exception.ex.CustomApiException;
import com.admin.web.role.repository.RoleGroupRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.admin.web.role.dto.RoleGroupReqDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleGroupService {

    private final RoleGroupRepository roleGroupRepository;

    @Transactional
    public void save(@Valid RoleGroupSaveReqDto roleGroupSaveDto) {

        Optional<RoleGroup> roleGroup = roleGroupRepository.findByRoleCode(roleGroupSaveDto.getRoleCode());
        if (roleGroup.isPresent()) {
            throw new CustomApiException("권한코드는 중복될 수 없습니다.");
        }

        roleGroupRepository.save(roleGroupSaveDto.toEntity());
    }

}
