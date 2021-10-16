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

import java.io.Serializable;
import java.util.UUID;

/**
 * Interface providing the behavior for an entity to know its identity (its type and its unique identifier).
 * <p>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IEntityIdentity extends Serializable
{
    /**
     * Returns the identifier of the entity.
     * @return Entity identifier.
     */
    UUID getId();

    /**
     * Returns the entity type.
     * @return Entity type.
     */
    EntityType getEntityType();
}
