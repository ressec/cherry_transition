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
package com.hemajoo.commerce.cherry.model.person.search;

import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.model.base.search.BaseSearch;
import com.hemajoo.commerce.cherry.model.person.type.AddressCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Represents a postal address search object.Œ
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PostalAddressSearch extends BaseSearch
{
    /**
     * Postal address street name.
     */
    @ApiModelProperty(value = "Street name", allowEmptyValue = true)
    private String streetName;

    /**
     * Postal address street number.
     */
    @ApiModelProperty(value = "Street number", allowEmptyValue = true)
    private String streetNumber;

    /**
     * Postal address locality.
     */
    @ApiModelProperty(value = "Locality", allowEmptyValue = true)
    private String locality;

    /**
     * Postal address country code (ISO Alpha-3 code).
     */
    @ApiModelProperty(value = "country code", notes = "ISO Alpha-3 code", allowEmptyValue = true)
    private String countryCode;

    /**
     * Postal address zip (postal) code.
     */
    @ApiModelProperty(value = "Postal code", allowEmptyValue = true)
    private String zipCode;

    /**
     * Postal address area/region/department depending on the country.
     */
    @ApiModelProperty(value = "Area", notes = "Area/Region", allowEmptyValue = true)
    private String area;

    /**
     * Is it a default postal address?
     */
    @ApiModelProperty(value = "Is default", allowEmptyValue = true)
    private Boolean isDefault;

    /**
     * Postal address type.
     */
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "addressType", notes = "Address type", allowEmptyValue = true)
    private AddressType addressType;

    /**
     * Postal address category.
     */
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "categoryType", notes = "Address category type", allowEmptyValue = true)
    private AddressCategoryType categoryType;

    /**
     * The person identifier this postal address belongs to.
     */
    @ApiModelProperty(value = "Person identifier", allowEmptyValue = true)
    private Long personId;

    /**
     * Creates a new postal address.
     */
    public PostalAddressSearch()
    {
        super(EntityType.POSTAL_ADDRESS);
    }
}
