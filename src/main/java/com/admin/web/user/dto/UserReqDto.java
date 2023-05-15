package com.admin.web.user.dto;

import com.admin.domain.user.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

public class UserReqDto {

    @Getter @Setter
    public static  class LoginReqDto {
        private String loginId;
        private String password;
    }

    @Getter @Setter
    public static class SignupReqDto {

        @NotEmpty(message = "로그인 아이디는 필수 입니다.")
        private String loginId;

        @NotEmpty(message = "사용자이름은 필수 입니다.")
        private String username;
        @NotEmpty(message = "패스워드는 필수 입니다.")
        private String password;
        @NotEmpty(message = "사용자 여부 필수 입니다.")
        private String useYn;

        @NotEmpty(message = "권한 아이디는 필수 입니다.")
        private String roleId;

        @NotEmpty(message = "이메일은 필수 입니다.")
        private String email;

        public User toEntity() {
            return User.builder()
                    .loginId(loginId)
                    .email(email)
                    .username(username)
                    .password(password)
                    .useYn(useYn)
                    .build();
        }

    }

}
