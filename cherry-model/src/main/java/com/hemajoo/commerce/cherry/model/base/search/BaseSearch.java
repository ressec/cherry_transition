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
package com.hemajoo.commerce.cherry.model.base.search;

import com.hemajoo.commerce.cherry.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.commons.entity.IEntityIdentity;
import com.hemajoo.commerce.cherry.commons.entity.IdentityAware;
import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.commons.type.StatusType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Represents a base entity search object.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseSearch extends AbstractStatusSearch implements IEntityIdentity, IdentityAware
{
    /**
     * Entity identifier.
     */
    @ApiModelProperty(value = "Email address identifier (UUID)")
    private String id;

    /**
     * Entity type.
     */
    @ApiModelProperty(hidden = true)
    private EntityType entityType;

    /**
     * Entity name.
     */
    @ApiModelProperty(hidden = true)
    private String name;

    /**
     * Entity description.
     */
    @ApiModelProperty(hidden = true)
    private String description;

    /**
     * Creates a new instance given the entity type.
     * @param type Entity type.
     * @see StatusType
     */
    public BaseSearch(EntityType type)
    {
        this.entityType = type;
    }

    /**
     * Returns the unique identifier of this entity.
     * @return Entity identifier.
     */
    public final UUID getId()
    {
        return this.id != null ? UUID.fromString(id) : null;
    }

    @Override
    public final EntityIdentity getIdentity()
    {
        return new EntityIdentity(getId(), entityType);
    }
}