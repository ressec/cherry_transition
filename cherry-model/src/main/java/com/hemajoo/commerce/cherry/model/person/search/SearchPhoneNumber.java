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
import com.hemajoo.commerce.cherry.model.base.search.AbstractEntitySearch;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Represents a phone number search object.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@ApiModel(value = "PhoneNumberSearch", description = "Specification object used to search for phone numbers.")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SearchPhoneNumber extends AbstractEntitySearch
{
    /**
     * Phone number.
     */
    @ApiModelProperty(value = "Number", allowEmptyValue = true)
    private String number;

    /**
     * Phone number country code (ISO Alpha-3 code).
     */
    @ApiModelProperty(value = "Country code", notes = "Iso Alpha-3 code.", allowEmptyValue = true)
    private String countryCode;

    /**
     * Phone number type.
     */
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "phoneType", notes = "Phone type", allowEmptyValue = true)
    private PhoneNumberType phoneType;

    /**
     * Phone number category type.
     */
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "categoryType", notes = "Category type", allowEmptyValue = true)
    private PhoneNumberCategoryType categoryType;

    /**
     * Is it a default phone number?
     */
    @ApiModelProperty(value = "Is default", allowEmptyValue = true)
    private Boolean isDefault;

    /**
     * The person identifier this phone number belongs to.
     */
    @ApiModelProperty(value = "Person identifier", allowEmptyValue = true)
    private Long personId;

    /**
     * Creates a new phone number search instance.
     */
    public SearchPhoneNumber()
    {
        super(EntityType.PHONE_NUMBER);
    }
}
