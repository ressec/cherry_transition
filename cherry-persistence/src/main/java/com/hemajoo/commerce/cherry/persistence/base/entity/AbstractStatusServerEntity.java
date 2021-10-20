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

import com.hemajoo.commerce.cherry.commons.type.StatusType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * Represents the base status part of a persistence entity of the {@code Cherry} data model.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class AbstractStatusServerEntity extends AbstractAuditServerEntity implements ServerEntity
{
    public static final String FIELD_STATUS_TYPE    = "statusType";
    public static final String FIELD_SINCE          = "since";

    /**
     * Entity status.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_TYPE", length = 50)
    private StatusType statusType;

    /**
     * Inactivity time stamp information (server time) that must be filled when the entity becomes inactive.
     */
    @ApiModelProperty(hidden = true)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SINCE", length = 26)
    private Date since;

    /**
     * Returns if this entity is active?
     * @return {@code True} if the entity is active, {@code false} otherwise.
     */
    @ApiModelProperty(hidden = true) // STATUS is used instead!
    public final boolean isActive()
    {
        return statusType == StatusType.ACTIVE;
    }

    /**
     * Sets the underlying entity to {@link StatusType#ACTIVE}.
     */
    public final void setActive()
    {
        statusType = StatusType.ACTIVE;
        since = null;
    }

    /**
     * Sets the underlying entity to {@link StatusType#INACTIVE}.
     */
    public final void setInactive()
    {
        statusType = StatusType.INACTIVE;
        since = new Date(System.currentTimeMillis());
    }

    /**
     * Sets the status of the entity.
     * @param status Status.
     * @see StatusType
     */
    public void setStatusType(StatusType status)
    {
        if (status != this.statusType)
        {
            if (status == StatusType.INACTIVE)
            {
                setInactive();
            }
            else
            {
                setActive();
            }
        }
    }
}
