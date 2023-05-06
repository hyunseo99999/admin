package com.admin.core.service;

import com.admin.domain.user.User;
import com.admin.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InternalAuthenticationServiceException("아이디 또는 패스워드를 확인해주세요"));

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (!user.getRoleMappings().isEmpty()) {
            List<String> roles = user.getRoleMappings()
                    .stream()
                    .map(role -> "ROLE_" + role.getRoleGroup().getRoleCode())
                    .collect(Collectors.toList());

            authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }

        return new LoginUser(user, authorities);
    }
}
