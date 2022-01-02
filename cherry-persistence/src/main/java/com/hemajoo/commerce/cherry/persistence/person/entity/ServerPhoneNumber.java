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

import com.hemajoo.commerce.cherry.model.person.entity.base.PhoneNumber;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServerEntity;

/**
 * Interface providing the minimal behavior of a server phone number entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface ServerPhoneNumber extends PhoneNumber, ServerEntity
{
    /**
     * Returns the person owning this phone number.
     * @return Person.
     */
    ServerPerson getPerson();

    /**
     * Sets the person owning this phone number.
     * @param owner Person.
     */
    void setPerson(final ServerPerson owner);
}
