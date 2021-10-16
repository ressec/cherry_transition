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
package com.hemajoo.commerce.cherry.commons.entity;

import com.hemajoo.commerce.cherry.commons.type.EntityType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * Entity identity contains minimal information of the identity of an entity (its type and its identifier).
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@ToString
public class EntityIdentity implements IEntityIdentity
{
    /**
     * Entity type.
     */
    @ApiModelProperty(hidden = true)
    @Getter
    private final EntityType entityType;

    /**
     * Entity unique identifier.
     */
    @ApiModelProperty(hidden = true)
    @Getter
    private final UUID id;

    /**
     * Creates a new entity identity.
     * @param id Entity identifier.
     * @param entityType Entity type.
     */
    public EntityIdentity(final UUID id, final EntityType entityType)
    {
        this.id = id;
        this.entityType = entityType;
    }
}