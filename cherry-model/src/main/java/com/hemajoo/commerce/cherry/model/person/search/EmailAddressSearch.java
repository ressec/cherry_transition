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
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Represents an email address search object.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class EmailAddressSearch extends BaseSearch
{
    /**
     * Email address.
     */
    @ApiModelProperty(value = "Email address", allowEmptyValue = true)
    private String email;

    /**
     * Is it the default email address?
     */
    @ApiModelProperty(value = "Is default email address?", required = true, example = "true")
    private boolean defaultEmail;

    /**
     * Email address type.
     */
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(name = "type", value = "Email address type", notes = "Address type", allowEmptyValue = true)
    private AddressType addressType;

    /**
     * The person identifier this email address belongs to.
     */
    @ApiModelProperty(value = "Person identifier (UUID) owning the email address(es)", hidden = false)
    private String personId;

    /**
     * Creates a new email address search entity.
     */
    public EmailAddressSearch()
    {
        super(EntityType.EMAIL_ADDRESS);
    }
}
