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
package com.hemajoo.commerce.cherry.model.person.entity.base;

import com.hemajoo.commerce.cherry.model.base.entity.BaseEntity;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;

/**
 * Behavior of an email address entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface EmailAddress extends BaseEntity
{
    String getEmail();

    void setEmail(final String email);

    Boolean getIsDefaultEmail();

    void setIsDefaultEmail(final Boolean isDefaultEmail);

    AddressType getAddressType();

    void setAddressType(final AddressType type);

}
