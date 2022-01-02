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

import com.hemajoo.commerce.cherry.model.base.entity.AuditEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Represents the base audit part of a persistence entity of the {@code Cherry} data model.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractServerAuditEntity implements AuditEntity
{
    public static final String FIELD_CREATED_DATE   = "createdDate";
    public static final String FIELD_MODIFIED_DATE  = "modifiedDate";
    public static final String FIELD_CREATED_BY     = "createdBy";
    public static final String FIELD_MODIFIED_BY    = "modifiedBy";

    /**
     * Entity creation date.
     */
    @Setter
    @Column(name = "CREATED_DATE", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreatedDate
    private LocalDateTime createdDate;

    /**
     * Entity modification date.
     */
    @Setter
    @Column(name = "MODIFIED_DATE", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    /**
     * Entity author.
     */
    @Getter
    @Setter
    @Column(name = "CREATED_BY", length = 50)
    @CreatedBy
    private String createdBy;

    /**
     * Entity last modification author.
     */
    @Getter
    @Setter
    @Column(name = "MODIFIED_BY", length = 50)
    @LastModifiedBy
    private String modifiedBy;

    public LocalDateTime getCreatedDate()
    {
        return createdDate.truncatedTo(ChronoUnit.MILLIS);
    }

    public LocalDateTime getModifiedDate()
    {
        return modifiedDate.truncatedTo(ChronoUnit.MILLIS);
    }
}
