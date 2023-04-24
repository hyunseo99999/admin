package com.admin.web.role.dto;

import com.admin.domain.user.RoleGroup;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class RoleGroupReqDto {

    @Getter @Setter
    public static class RoleGroupSaveReqDto {
        @Size(max = 20)
        @NotEmpty(message = "권한코드는 필수 입니다.")
        private String roleCode;

        @Size(max = 100)
        @NotEmpty(message = "권한이름은 필수 입니다.")
        private String roleNm;

        @Size(max = 4000)
        private String roleDc;

        public RoleGroup toEntity() {
            return RoleGroup.builder()
                    .roleCode(roleCode)
                    .roleNm(roleNm)
                    .roleDc(roleDc)
                    .build();
        }
    }
}
