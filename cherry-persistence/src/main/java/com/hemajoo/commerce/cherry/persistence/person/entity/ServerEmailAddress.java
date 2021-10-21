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
package com.hemajoo.commerce.cherry.persistence.person.entity;

import com.hemajoo.commerce.cherry.model.person.entity.base.EmailAddress;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServerEntity;

/**
 * Interface providing the behavior of a server email address entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @since Cherry 0.1.0
 * @version 1.0.0
 */
public interface ServerEmailAddress extends EmailAddress, ServerEntity
{
    /**
     * Returns the person owning this email address.
     * @return Person.
     */
    ServerPerson getPerson();

    /**
     * Sets the person owning this email address.
     * @param owner Person.
     */
    void setPerson(final ServerPerson owner);
}
