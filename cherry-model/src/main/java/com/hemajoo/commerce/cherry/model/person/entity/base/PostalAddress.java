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
import com.hemajoo.commerce.cherry.model.person.type.AddressCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;

/**
 * Interface providing the behavior of a postal address entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface PostalAddress extends BaseEntity
{
    /**
     * Returns the postal address street name.
     * @return Street name.
     */
    String getStreetName();

    /**
     * Sets the postal address street name.
     * @param streetName Street name.
     */
    void setStreetName(final String streetName);

    /**
     * Returns the postal address street number.
     * @return Street number.
     */
    String getStreetNumber();

    /**
     * Sets the postal address street number.
     * @param streetNumber Street number.
     */
    void setStreetNumber(final String streetNumber);

    /**
     * Returns the postal address locality.
     * @return Locality.
     */
    String getLocality();

    /**
     * Sets the postal address locality.
     * @param locality Locality.
     */
    void setLocality(final String locality);

    /**
     * Returns the postal address country code.
     * @return Country code.
     */
    String getCountryCode();

    /**
     * Sets the postal address country code.
     * @param countryCode Country code.
     */
    void setCountryCode(final String countryCode);

    /**
     * Returns the postal address zip code.
     * @return Zip code.
     */
    String getZipCode();

    /**
     * Sets the postal address zip code.
     * @param zipCode Zip code.
     */
    void setZipCode(final String zipCode);

    /**
     * Returns the postal address area.
     * @return Area.
     */
    String getArea();

    /**
     * Sets the postal address area.
     * @param area Area.
     */
    void setArea(final String area);

    /**
     * Returns if the postal address is the default one.
     * @return True if it's the default one, false otherwise.
     */
    Boolean getIsDefault();

    /**
     * Sets if the postal address is the default one.
     * @param isDefault True if it's the default postal address, false otherwise.
     */
    void setIsDefault(final Boolean isDefault);

    /**
     * Returns the postal address type.
     * @return Postal address type.
     */
    AddressType getAddressType();

    /**
     * Sets the postal address type.
     * @param type Postal address type.
     */
    void setAddressType(final AddressType type);

    /**
     * Returns the postal address category type.
     * @return Category type.
     */
    AddressCategoryType getCategoryType();

    /**
     * Sets the postal address category type.
     * @param type Category type.
     */
    void setCategoryType(final AddressCategoryType type);
}
