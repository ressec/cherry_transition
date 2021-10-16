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

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Represents the (abstract) audit part of a base (client) entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractAuditClientEntity
{
    /**
     * Entity creation date.
     */
    @ApiModelProperty(hidden = true)
    private Date createdDate;

    /**
     * Entity modification date.
     */
    @ApiModelProperty(hidden = true)
    private Date modifiedDate;

    /**
     * Entity creation author.
     */
    @ApiModelProperty(name = "createdBy", notes = "Person being the author of this entity.", required = false, example = "Henry.Jacobson@gmail.com")
    private String createdBy;

    /**
     * Entity modification author.
     */
    @ApiModelProperty(name = "modifiedBy", notes = "Last person having modified this entity.", required = false, example = "Henry.Jacobson@gmail.com")
    private String modifiedBy;
}
