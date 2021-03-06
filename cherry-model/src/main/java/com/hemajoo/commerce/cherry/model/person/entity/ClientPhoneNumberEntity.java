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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hemajoo.commerce.cherry.commons.entity.Identity;
import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.model.base.entity.ClientBaseEntity;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Represents a phone number client entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientPhoneNumberEntity extends ClientBaseEntity implements ClientPhoneNumber
{
    /**
     * Phone number.
     */
    @ApiModelProperty(name = "number", notes = "Phone number", value = "0652897412")
    private String number;

    /**
     * Phone number country code (ISO Alpha-3 code).
     */
    @ApiModelProperty(name = "countryCode", notes = "Phone number country code (ISO Alpha-3)", value = "FRA")
    private String countryCode;

    /**
     * Phone number type.
     */
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(name = "phoneType", notes = "Phone number type", value = "PRIVATE")
    private PhoneNumberType phoneType;

    /**
     * Phone number category type.
     */
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(name = "categoryType", notes = "Phone number category type", value = "MOBILE")
    private PhoneNumberCategoryType categoryType;

    /**
     * Is it a default phone number?
     */
    @ApiModelProperty(name = "isDefault", notes = "Is it the default phone number?", value = "true")
    private Boolean isDefault;

    /**
     * The entity identity this phone number belongs to.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("phoneNumbers")
    @ApiModelProperty(name = "owner", notes = "Entity identity this phone number belongs to", value = "1")
    private Identity owner;

    /**
     * Creates a new phone number.
     */
    public ClientPhoneNumberEntity()
    {
        super(EntityType.PHONE_NUMBER);
    }
}
