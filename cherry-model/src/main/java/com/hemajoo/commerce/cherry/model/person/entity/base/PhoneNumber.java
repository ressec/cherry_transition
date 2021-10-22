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
 * Interface providing the behavior of a phone number entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface PhoneNumber extends BaseEntity
{
    /**
     * Returns the phone number.
     * @return Phone number.
     */
    String getNumber();

    /**
     * Sets the phone number.
     * @param number Phone number.
     */
    void setNumber(final String number);

    /**
     * Returns the phone number country code.
     * @return Country code.
     */
    String getCountryCode();

    /**
     * Sets the phone number country code.
     * @param countryCode Country code.
     */
    void setCountryCode(final String countryCode);

    /**
     * Returns the phone number type.
     * @return Phone type.
     */
    PhoneNumberType getPhoneType();

    /**
     * Sets the phone number type.
     * @param type Phone number type.
     */
    void setPhoneType(final PhoneNumberType type);

    /**
     * Returns the phone number category type.
     * @return Category type.
     */
    PhoneNumberCategoryType getCategoryType();

    /**
     * Sets the phone number category type.
     * @param type Category type.
     */
    void setCategoryType(final PhoneNumberCategoryType type);

    /**
     * Returns if the phone number is the default one.
     * @return True if it's the default phone number, false otherwise.
     */
    Boolean getIsDefault();

    /**
     * Sets if the phone number is the default one.
     * @param isDefault True to set it as the default phone number, false otherwise.
     */
    void setIsDefault(final Boolean isDefault);
}
