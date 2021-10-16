/*
 * (C) Copyright Resse Christophe 2021 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Resse Christophe. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Resse C. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Resse Christophe (christophe.resse@gmail.com).
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.commerce.cherry.persistence.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * Represents the base audit part of a persistence entity of the {@code Cherry} data model.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Data
@ToString
@EqualsAndHashCode
@MappedSuperclass
public abstract class AbstractAuditServerEntity implements Serializable
{
    public static final String FIELD_CREATED_DATE   = "createdDate";
    public static final String FIELD_MODIFIED_DATE  = "modifiedDate";
    public static final String FIELD_CREATED_BY     = "createdBy";
    public static final String FIELD_MODIFIED_BY    = "modifiedBy";

    /**
     * Entity creation date.
     */
    @Column(name = "CREATED_DATE", length = 26)
    @CreatedDate
    private Date createdDate;

    /**
     * Entity modification date.
     */
    @Column(name = "MODIFIED_DATE", length = 26)
    @LastModifiedDate
    private Date modifiedDate;

    /**
     * Entity author.
     */
    @Column(name = "CREATED_BY", length = 50)
    @CreatedBy
    private String createdBy;

    /**
     * Entity last modification author.
     */
    @Column(name = "MODIFIED_BY", length = 50)
    @LastModifiedBy
    private String modifiedBy;
}
