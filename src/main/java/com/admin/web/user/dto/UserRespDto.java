package com.admin.web.user.dto;

import com.admin.domain.User;
import com.admin.util.DateUtil;

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
}
