package com.admin.web.user.dto;

import com.admin.domain.RoleGroup;
import com.admin.domain.RoleMapping;
import com.admin.domain.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UserReqDto {

    @Getter @Setter
    public static class SignupReqDto {
        @NotEmpty(message = "사용자이름은 필수 입니다.")
        private String username;
        @NotEmpty(message = "패스워드는 필수 입니다.")
        private String password;
        @NotEmpty(message = "사용자 여부 필수 입니다.")
        private String useYn;

        @NotEmpty(message = "권한 아이디는 필수 입니다.")
        private String roleId;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .useYn(useYn)
                    .build();
        }

    }

}
