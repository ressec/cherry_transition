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
package com.hemajoo.commerce.cherry.model.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hemajoo.commerce.cherry.commons.type.StatusType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Represents a client abstract status entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractClientStatusEntity extends AbstractClientAuditEntity
{
    /**
     * Email address status.
     */
    @JsonProperty("status")
    @ApiModelProperty(name = "statusType", notes = "Email address status type", value = "ACTIVE")
    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    /**
     * Inactivity time stamp information (server time) that must be filled when the email address becomes inactive.
     */
    @JsonProperty("inactiveSince")
    @ApiModelProperty(hidden = true) // As it is set automatically when status changes.
    private LocalDateTime since;

    /**
     * Returns if the entity is active?
     * @return {@code True} if the entity is active, {@code false} otherwise.
     */
    @ApiModelProperty(hidden = true) // Status type is used instead!
    @JsonIgnore
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
        since = LocalDateTime.now(ZoneId.systemDefault());
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
