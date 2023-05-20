package com.admin.web.role.service;

import com.admin.domain.user.RoleGroup;
import com.admin.domain.user.User;
import com.admin.exception.ex.CustomApiException;
import com.admin.web.role.dto.RoleGroupRespDto;
import com.admin.web.role.dto.RoleGroupRespDto.RoleGroupDetailRespDto;
import com.admin.web.role.dto.RoleGroupRespDto.RoleGroupListRespDto;
import com.admin.web.role.repository.RoleGroupRepository;
import com.admin.web.user.dto.UserRespDto;
import com.admin.web.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.admin.web.role.dto.RoleGroupReqDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleGroupService {

    private final RoleGroupRepository roleGroupRepository;

    private final UserRepository userRepository;

    @Transactional
    public void save(@Valid RoleGroupSaveReqDto roleGroupSaveDto) {

        Optional<RoleGroup> roleGroup = roleGroupRepository.findByRoleCode(roleGroupSaveDto.getRoleCode());
        if (roleGroup.isPresent()) {
            throw new CustomApiException("권한코드는 중복될 수 없습니다.");
        }

        roleGroupRepository.save(roleGroupSaveDto.toEntity());
    }

    public List<RoleGroupListRespDto> findListRoleGroup() {
        List<RoleGroup> findByRoleGroups = roleGroupRepository.findAll();
        return findByRoleGroups
                .stream()
                .map(roleGroup -> new RoleGroupListRespDto(roleGroup, userRepository.findUserById(roleGroup.getCreateId()).get()))
                .collect(Collectors.toList());
    }

    public RoleGroupDetailRespDto findOneRoleGroup(Long roleGroupId) {
        RoleGroup roleGroup = roleGroupRepository.findById(roleGroupId)
                .orElseThrow(() -> new CustomApiException("등록된 권한이 없습니다."));

        return new RoleGroupDetailRespDto(roleGroup);
    }

    @Transactional
    public void deleteRoleGroup(Long roleGroupId) {
        roleGroupRepository.deleteById(roleGroupId);
    }

}
