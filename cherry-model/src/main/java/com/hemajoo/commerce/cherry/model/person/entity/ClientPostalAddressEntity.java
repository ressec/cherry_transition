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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hemajoo.commerce.cherry.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.model.base.entity.ClientBaseEntity;
import com.hemajoo.commerce.cherry.model.person.type.AddressCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Represents a client postal address entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ClientPostalAddressEntity extends ClientBaseEntity implements ClientPostalAddress
{
    /**
     * Postal address street name.
     */
    @JsonProperty("streetName")
    @ApiModelProperty(name = "name", notes = "Postal address street name", value = "Rue de la Libération")
    private String streetName;

    /**
     * Postal address street number.
     */
    @JsonProperty("streetNumber")
    @ApiModelProperty(name = "number", notes = "Postal address street number", value = "18 bis")
    private String streetNumber;

    /**
     * Postal address locality.
     */
    @JsonProperty("locality")
    @ApiModelProperty(name = "locality", notes = "Postal address locality (city)", value = "Paris")
    private String locality;

    /**
     * Postal address country code (ISO Alpha-3 code).
     */
    @JsonProperty("countryCode")
    @ApiModelProperty(name = "countryCode", notes = "Postal address country code (ISO Alpha-3)", value = "FRA")
    private String countryCode;

    /**
     * Postal address zip (postal) code.
     */
    @JsonProperty("zipCode")
    @ApiModelProperty(name = "zipCode", notes = "Postal address zip code (postal code)", value = "75000")
    private String zipCode;

    /**
     * Postal address area/region/department depending on the country.
     */
    @JsonProperty("area")
    @ApiModelProperty(name = "area", notes = "Postal address area (region/state)", value = "Île-de-France")
    private String area;

    /**
     * Is it a default postal address?
     */
    @JsonProperty("isDefault")
    @ApiModelProperty(name = "isDefault", notes = "Is it a default postal address ?", value = "true")
    private Boolean isDefault;

    /**
     * Postal address type.
     */
    @JsonProperty("addressType")
    @ApiModelProperty(name = "addressType", notes = "Postal address type", value = "PRIVATE")
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    /**
     * Postal address category.
     */
    @JsonProperty("categoryType")
    @ApiModelProperty(name = "categoryType", notes = "Postal address category type", value = "POSTAL")
    @Enumerated(EnumType.STRING)
    private AddressCategoryType categoryType;

    /**
     * The person this postal address belongs to.
     */
    @JsonProperty("person")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ApiModelProperty(name = "person", notes = "Person this postal address belongs to", value = "1")
    private EntityIdentity person; // TODO Could it be moved to base entity?

    /**
     * Creates a new postal address.
     */
    public ClientPostalAddressEntity()
    {
        super(EntityType.POSTAL_ADDRESS);
    }
}
