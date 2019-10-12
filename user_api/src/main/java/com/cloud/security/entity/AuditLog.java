package com.cloud.security.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 审计日志实体.
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {

    /**
     * 主键.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createTime;

    /**
     * 修改时间.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modifyTime;

    /**
     * 请求方式.
     */
    private String method;

    /**
     * 请求URI.
     */
    private String path;

    /**
     * 状态.
     */
    private Integer status;

    /**
     * 创建人.
     */
    @CreatedBy
    private String username;
}
