package com.admin.web.user.service;

import com.admin.domain.RoleGroup;
import com.admin.domain.RoleMapping;
import com.admin.domain.User;
import com.admin.exception.ex.CustomApiException;
import com.admin.web.role.repository.RoleGroupRepository;
import com.admin.web.user.dto.UserReqDto;
import com.admin.web.user.dto.UserReqDto.SignupReqDto;
import com.admin.web.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleGroupRepository roleGroupRepository;

    private final EntityManager em;

    @Transactional
    public void save(SignupReqDto signupReqDto) {

        Optional<RoleGroup> findRoleGroup = roleGroupRepository.findById(Long.parseLong(signupReqDto.getRoleId()));
        /*if (findRoleGroup.isPresent()) {
            throw new CustomApiException("권한그룹이 존재하지 않습니다.");
        }*/

        RoleMapping roleMapping = RoleMapping.createRoleMapping(findRoleGroup.get());
        User user = User.createUser(signupReqDto.toEntity(), roleMapping);
        userRepository.save(user);
    }

}
