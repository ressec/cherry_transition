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
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberType;

/**
 * Behavior of a phone number entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface PhoneNumber extends BaseEntity
{
    String getNumber();

    void setNumber(final String number);

    String getCountryCode();

    void setCountryCode(final String countryCode);

    PhoneNumberType getPhoneType();

    void setPhoneType(final PhoneNumberType type);

    PhoneNumberCategoryType getCategoryType();

    void setCategoryType(final PhoneNumberCategoryType type);

    Boolean getIsDefault();

    void setIsDefault(final Boolean isDefault);
}
