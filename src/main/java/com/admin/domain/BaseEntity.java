package com.admin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public class BaseEntity {

    @CreatedDate
    private LocalDateTime createAt;

    @CreatedBy
    @Column(name = "create_id", updatable = false, nullable = true)
    private Long createId;

    @LastModifiedDate
    private LocalDateTime updateAt;

    @LastModifiedBy
    @Column(name = "update_id" , nullable = true)
    private Long updateId;
}
