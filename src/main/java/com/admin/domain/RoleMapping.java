package com.admin.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_role_mapp")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class RoleMapping extends BaseEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
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
