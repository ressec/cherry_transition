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

import com.hemajoo.commerce.cherry.model.base.entity.BaseEntity;
import com.hemajoo.commerce.cherry.model.base.entity.ClientEntity;
import com.hemajoo.commerce.cherry.model.person.entity.base.PhoneNumber;

/**
 * Behavior of a client phone number entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface ClientPhoneNumber extends PhoneNumber, ClientEntity, BaseEntity
{
//    /**
//     * Returns the entity owner identity.
//     * @return Owner entity identity.
//     */
//    Identity getOwner();
//
//    /**
//     * Sets the entity owner identity.
//     * @param owner Owner entity identity.
//     */
//    void setOwner(final Identity owner);

    ClientPersonEntity getPerson();

    void setPerson(final ClientPersonEntity person);
}
