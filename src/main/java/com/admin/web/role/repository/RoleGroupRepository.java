package com.admin.web.role.repository;

import com.admin.domain.RoleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleGroupRepository extends JpaRepository<RoleGroup, Long> {

    Optional<RoleGroup> findByRoleCode(String roleCode);

}
