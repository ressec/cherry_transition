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
package com.hemajoo.commerce.cherry.model.person.entity;

import com.hemajoo.commerce.cherry.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.model.base.entity.ClientEntity;
import com.hemajoo.commerce.cherry.model.person.entity.base.EmailAddress;

/**
 * Interface providing the behavior of a client email address entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface ClientEmailAddress extends EmailAddress, ClientEntity
{
    /**
     * Returns the entity identity owning this email address.
     * @return Entity identity.
     */
    EntityIdentity getPerson();

    /**
     * Sets the entity identity owning this email address.
     * @param owner Entity identity.
     */
    void setPerson(final EntityIdentity owner);
}
