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

import com.hemajoo.commerce.cherry.commons.entity.Identity;
import com.hemajoo.commerce.cherry.commons.entity.IdentityAware;

/**
 * Interface providing behavior of a base entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface BaseEntity extends StatusEntity, Identity, IdentityAware
{
    /**
     * Returns the entity name.
     * @return Entity name.
     */
    String getName();

    /**
     * Sets the entity name.
     * @param name Entity name.
     */
    void setName(final String name);

    /**
     * Returns the entity description.
     * @return Entity description.
     */
    String getDescription();

    /**
     * Sets the entity description.
     * @param description Entity description.
     */
    void setDescription(final String description);

    /**
     * Returns the entity reference.
     * @return Entity reference.
     */
    String getReference();

    /**
     * Sets the entity reference.
     * @param reference Entity reference.
     */
    void setReference(final String reference);

    //List<? extends BaseEntity> getDocuments();
}
