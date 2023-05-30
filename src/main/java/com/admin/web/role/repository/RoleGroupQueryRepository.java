package com.admin.web.role.repository;

import com.admin.web.role.dto.QRoleGroupRespDto_RoleGroupListRespDto;
import com.admin.web.role.dto.RoleGroupRespDto.RoleGroupListRespDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.admin.domain.user.QRoleGroup.roleGroup;
import static com.admin.domain.user.QUser.user;

@RequiredArgsConstructor
@Repository
public class RoleGroupQueryRepository {
    private final EntityManager em;

    public Page<RoleGroupListRespDto> findAll(Pageable pageable) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        List<RoleGroupListRespDto> roleGroupList = query.select(new QRoleGroupRespDto_RoleGroupListRespDto(
                                roleGroup
                                , ExpressionUtils.as(
                                JPAExpressions.select(user.username)
                                        .from(user)
                                        .where(user.id.eq(roleGroup.createId)), "user")
                        )
                )
                .from(roleGroup)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(roleGroup.createAt.desc())
                .fetch();

        JPAQuery<Long> countQuery = query
                       .select(roleGroup.count())
                       .from(roleGroup);

        return PageableExecutionUtils.getPage(roleGroupList, pageable, countQuery::fetchOne);
    }

}
