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

import com.hemajoo.commerce.cherry.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.model.base.entity.ClientBaseEntity;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Represents a client email address entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Data
@ToString(callSuper = true)
//@Builder(setterPrefix = "with") // Does not work well with MapStruct!
@EqualsAndHashCode(callSuper = true)
public class ClientEmailAddressEntity extends ClientBaseEntity implements ClientEmailAddress
{
    /**
     * Email address.
     */
    @ApiModelProperty(name = "email", notes = "Email address", value = "joe.doe@gmail.com")
    //@Email(message = "email: '${validatedValue}' is not a valid email!")
    private String email;

    /**
     * Is it the default email address?
     */
    @ApiModelProperty(name = "defaultEmail", notes = "Is it the default email address", value = "true")
    private Boolean isDefaultEmail;

    /**
     * Email address type.
     */
    @ApiModelProperty(name = "addressType", notes = "Address type", value = "PRIVATE")
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    /**
     * The person identifier this email address belongs to.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    //@JsonIgnoreProperties("emailAddresses")
    @ApiModelProperty(name = "owner", notes = "Entity identity this email address belongs to", value = "1")
    private EntityIdentity owner; // TODO Could it be moved to base entity?

    /**
     * Creates a new client email address entity.
     */
    public ClientEmailAddressEntity()
    {
        super(EntityType.EMAIL_ADDRESS);
    }
}
