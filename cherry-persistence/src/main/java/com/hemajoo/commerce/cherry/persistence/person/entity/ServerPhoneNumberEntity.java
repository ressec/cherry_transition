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

import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.model.person.entity.base.PhoneNumber;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberType;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServerBaseEntity;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServerEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Represents a server phone number entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "PHONE_NUMBER")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ServerPhoneNumberEntity extends ServerBaseEntity implements PhoneNumber, ServerEntity
{
    public static final String FIELD_IS_DEFAULT             = "isDefaultPhoneNumber";
    public static final String FIELD_NUMBER                 = "number";
    public static final String FIELD_COUNTRY_CODE           = "countryCode";
    public static final String FIELD_PHONE_TYPE             = "phoneType";
    public static final String FIELD_PHONE_CATEGORY_TYPE    = "categoryType";

    public static final String FIELD_PERSON_ID              = "personId";

    /**
     * Phone number.
     */
    @Getter
    @Setter
    @NotNull(message = "Phone number: 'phoneNumber' cannot be null!")
    @Column(name = "PHONE_NUMBER", length = 30)
    private String number;

    /**
     * Phone number country code (ISO Alpha-3 code).
     */
    @Getter
    @Setter
    @NotNull(message = "Phone number: 'countryCode' cannot be null!")
    @Column(name = "COUNTRY_CODE", length = 3)
    private String countryCode;

    /**
     * Phone number type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "PHONE_TYPE")
    private PhoneNumberType phoneType;

    /**
     * Phone number category type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY_TYPE")
    private PhoneNumberCategoryType categoryType;

    /**
     * Is it a default phone number?
     */
    @Getter
    @Setter
    @Column(name = "IS_DEFAULT", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDefault;

    /**
     * The person identifier this phone number belongs to.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Getter
    @ManyToOne(targetEntity = ServerPersonEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID", nullable = false)
    private ServerPersonEntity person;

    /**
     * Creates a new phone number.
     */
    public ServerPhoneNumberEntity()
    {
        super(EntityType.PHONE_NUMBER);
    }

    /**
     * Sets the owner person.
     * <hr>
     * <b>NOTE:</b> Never invoke directly this service to add a phone number to a person. For that, you need to call {@link ServerPersonEntity#addPhoneNumber(ServerPhoneNumberEntity)}.
     * @param person Person being the owner of the phone number.
     */
    public void setPerson(final ServerPersonEntity person)
    {
        this.person = person;
    }
}
