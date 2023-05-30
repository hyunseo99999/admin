package com.admin.web.role.service;

import com.admin.domain.user.RoleGroup;
import com.admin.exception.ex.CustomApiException;
import com.admin.web.role.dto.RoleGroupRespDto.RoleGroupDetailRespDto;
import com.admin.web.role.dto.RoleGroupRespDto.RoleGroupListRespDto;
import com.admin.web.role.repository.RoleGroupQueryRepository;
import com.admin.web.role.repository.RoleGroupRepository;
import com.admin.web.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.admin.web.role.dto.RoleGroupReqDto.RoleGroupSaveReqDto;
import static com.admin.web.role.dto.RoleGroupReqDto.RoleGroupUpdateReqDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleGroupService {

    private final RoleGroupRepository roleGroupRepository;

    private final RoleGroupQueryRepository roleGroupQueryRepository;

    private final UserRepository userRepository;

    @Transactional
    public void save(@Valid RoleGroupSaveReqDto roleGroupSaveDto) {

        Optional<RoleGroup> roleGroup = roleGroupRepository.findByRoleCode(roleGroupSaveDto.getRoleCode());
        if (roleGroup.isPresent()) {
            throw new CustomApiException("권한코드는 중복될 수 없습니다.");
        }

        roleGroupRepository.save(roleGroupSaveDto.toEntity());
    }

    @Transactional
    public void updateRoleGroup(@Valid RoleGroupUpdateReqDto updateReqDto) {

        Optional<RoleGroup> roleGroup = roleGroupRepository.findById(updateReqDto.getId());
        if (roleGroup.isEmpty()) {
            throw new CustomApiException("수정 할 아이다가 없습니다.");
        }
        RoleGroup findRoleGroup = updateReqDto.toEntity();
        roleGroupRepository.save(findRoleGroup);
    }

    public Page<RoleGroupListRespDto> findListRoleGroup(Pageable pageable) {
        return roleGroupQueryRepository.findAll(pageable);
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
