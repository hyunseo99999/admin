package com.admin.web.user.dto;

import com.admin.domain.user.User;
import com.admin.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class UserRespDto {

    public static class LoginRespDto {
        private Long id;
        private String username;
        private String createAt;

        public LoginRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.createAt = DateUtil.toStringFormat(user.getCreateAt());
        }
    }

    @Getter @Setter
    public static class UserListRespDto {
        private Long id;
        private String username;

        private String loginId;
        private String email;
        private String useYn;
        private String createAt;

        public UserListRespDto(User user) {
            this.id = user.getId();
            this.loginId = user.getLoginId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.useYn = user.getUseYn();
            this.createAt = DateUtil.toStringFormat(user.getCreateAt(), "yyyy-MM-dd");
        }
    }

    @Getter @Setter
    public static class UserDetailRespDto {
        private Long id;
        private String username;
        private String loginId;
        private String email;
        private String useYn;
        private String createAt;

        public UserDetailRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.loginId = user.getLoginId();
            this.email = user.getEmail();
            this.useYn = user.getUseYn();
            this.createAt = DateUtil.toStringFormat(user.getCreateAt(), "yyyy-MM-dd");
        }
    }
}
