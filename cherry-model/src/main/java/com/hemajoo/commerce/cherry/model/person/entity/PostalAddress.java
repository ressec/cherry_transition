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

import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.model.base.entity.ClientBaseEntity;
import com.hemajoo.commerce.cherry.model.person.type.AddressCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Represents a postal address (associated to a person).
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Data
//@Builder(setterPrefix = "with") // Does not work well with MapStruct!
@EqualsAndHashCode(callSuper = false)
public class PostalAddress extends ClientBaseEntity
{
    /**
     * Postal address street name.
     */
    @ApiModelProperty(name = "name", notes = "Postal address street name", value = "Rue de la Libération")
    private String streetName;

    /**
     * Postal address street number.
     */
    @ApiModelProperty(name = "number", notes = "Postal address street number", value = "18 bis")
    private String streetNumber;

    /**
     * Postal address locality.
     */
    @ApiModelProperty(name = "locality", notes = "Postal address locality (city)", value = "Paris")
    private String locality;

    /**
     * Postal address country code (ISO Alpha-3 code).
     */
    @ApiModelProperty(name = "countryCode", notes = "Postal address country code (ISO Alpha-3)", value = "FRA")
    private String countryCode;

    /**
     * Postal address zip (postal) code.
     */
    @ApiModelProperty(name = "zipCode", notes = "Postal address zip code (postal code)", value = "75000")
    private String zipCode;

    /**
     * Postal address area/region/department depending on the country.
     */
    @ApiModelProperty(name = "area", notes = "Postal address area (region/state)", value = "Île-de-France")
    private String area;

    /**
     * Is it a default postal address?
     */
    @ApiModelProperty(name = "isDefault", notes = "Is it a default postal address ?", value = "true")
    private Boolean isDefault;

    /**
     * Postal address type.
     */
    @ApiModelProperty(name = "addressType", notes = "Postal address type", value = "PRIVATE")
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    /**
     * Postal address category.
     */
    @ApiModelProperty(name = "categoryType", notes = "Postal address category type", value = "POSTAL")
    @Enumerated(EnumType.STRING)
    private AddressCategoryType categoryType;

    /**
     * The person identifier this postal address belongs to.
     */
    //private Long personId;
    private Person person;

    /**
     * Creates a new postal address.
     */
    public PostalAddress()
    {
        super(EntityType.POSTAL_ADDRESS);
    }
}
