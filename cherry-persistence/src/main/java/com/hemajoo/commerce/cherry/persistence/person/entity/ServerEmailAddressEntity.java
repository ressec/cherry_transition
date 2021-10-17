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
package com.hemajoo.commerce.cherry.persistence.person.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.model.person.entity.base.EmailAddress;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServerBaseEntity;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServerEntity;
import com.hemajoo.commerce.cherry.persistence.base.validation.BasicValidation;
import com.hemajoo.commerce.cherry.persistence.base.validation.ExtendedValidation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.GroupSequence;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * Represents a server email address entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@GroupSequence( { ServerEmailAddressEntity.class, BasicValidation.class, ExtendedValidation.class } )
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@Table(name = "EMAIL_ADDRESS")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ServerEmailAddressEntity extends ServerBaseEntity implements EmailAddress, ServerEntity
{
    public static final String FIELD_EMAIL          = "email";
    public static final String FIELD_IS_DEFAULT     = "defaultEmail";
    public static final String FIELD_ADDRESS_TYPE   = "addressType";

    public static final String FIELD_PERSON         = "person";

    /**
     * Email address.
     */
    @Getter
    @Setter
    @NotNull(message = "Email address: 'email' cannot be null!")
    @Email(message = "Email address: '${validatedValue}' is not a valid email!", groups = { Default.class })
    @Column(name = "EMAIL")
    private String email;

    /**
     * Is it the default email address?
     */
    @Getter
    @Setter
    @Column(name = "IS_DEFAULT", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDefaultEmail;

    /**
     * Email type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "ADDRESS_TYPE")
    private AddressType addressType;

    /**
     * The person identifier this email address belongs to.
     */
    @Getter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties
    @ManyToOne(targetEntity = ServerPersonEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID", nullable = false)
    private ServerPersonEntity person;

    /**
     * Creates a new persistent email address.
     */
    public ServerEmailAddressEntity()
    {
        super(EntityType.EMAIL_ADDRESS);
    }

    public void setPerson(final ServerPersonEntity person)
    {
        if (person != null)
        {
            if (this.person == null)
            {
                this.person = person;
            }
        }
    }
}
