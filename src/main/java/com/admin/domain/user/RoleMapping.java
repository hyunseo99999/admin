package com.admin.domain.user;

import com.admin.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_role_mapp")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoleMapping extends BaseEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_group_id")
    private RoleGroup roleGroup;

    public static RoleMapping createRoleMapping(RoleGroup roleGroup) {
        return RoleMapping.builder()
                .roleGroup(roleGroup)
                .build();
    }

    @Builder
    public RoleMapping(User user, RoleGroup roleGroup) {
        this.user = user;
        this.roleGroup = roleGroup;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
