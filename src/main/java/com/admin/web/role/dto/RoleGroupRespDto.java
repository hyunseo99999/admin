package com.admin.web.role.dto;

import com.admin.domain.user.RoleGroup;
import com.admin.domain.user.User;
import com.admin.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

public class RoleGroupRespDto {

    @Getter @Setter
    public static class RoleGroupListRespDto {
        private Long id;
        private String roleCode;
        private String roleNm;

        private String createAt;

        private String createName;


        public RoleGroupListRespDto(RoleGroup roleGroup, User user) {
            this.id = roleGroup.getId();
            this.roleCode = roleGroup.getRoleCode();
            this.roleNm = roleGroup.getRoleNm();
            this.createAt = DateUtil.toStringFormat(roleGroup.getCreateAt(), "YYYY-MM-DD");
            this.createName = user.getUsername();
        }
    }

    @Getter @Setter
    public static class RoleGroupDetailRespDto {
        private Long id;
        private String roleCode;
        private String roleNm;
        private String roleDc;

        public RoleGroupDetailRespDto(RoleGroup roleGroup) {
            this.id = roleGroup.getId();
            this.roleCode = roleGroup.getRoleCode();
            this.roleNm = roleGroup.getRoleNm();
            this.roleDc = roleGroup.getRoleDc();
        }
    }

}
