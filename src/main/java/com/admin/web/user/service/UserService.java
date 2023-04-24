package com.admin.web.user.service;

import com.admin.domain.user.RoleGroup;
import com.admin.domain.user.RoleMapping;
import com.admin.domain.user.User;
import com.admin.exception.ex.CustomApiException;
import com.admin.web.role.repository.RoleGroupRepository;
import com.admin.web.user.dto.UserReqDto.SignupReqDto;
import com.admin.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final RoleGroupRepository roleGroupRepository;

    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void save(SignupReqDto signupReqDto) {

        RoleGroup findRoleGroup = roleGroupRepository.findById(Long.parseLong(signupReqDto.getRoleId()))
                .orElseThrow(() -> new CustomApiException("권한그룹이 존재하지 않습니다."));

        Optional<User> findByUsername = userRepository.findByUsername(signupReqDto.getUsername());
        if (findByUsername.isPresent()) {
            throw new CustomApiException("동일한 username이 존재합니다.");
        }

        signupReqDto.setPassword(passwordEncoder.encode(signupReqDto.getPassword()));
        RoleMapping roleMapping = RoleMapping.createRoleMapping(findRoleGroup);
        User user = User.createUser(signupReqDto.toEntity(), roleMapping);
        userRepository.save(user);
    }

}
