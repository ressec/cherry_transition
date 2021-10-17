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
import com.hemajoo.commerce.cherry.model.person.type.AddressCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServerBaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Represents a persistent postal address.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@Table(name = "POSTAL_ADDRESS")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class PostalAddressServerEntity extends ServerBaseEntity
{
    public static final String FIELD_IS_DEFAULT     = "isDefault";
    public static final String FIELD_ADDRESS_TYPE   = "addressType";
    public static final String FIELD_CATEGORY_TYPE  = "categoryType";
    public static final String FIELD_STREET_NAME    = "streetName";
    public static final String FIELD_STREET_NUMBER  = "streetNumber";
    public static final String FIELD_LOCALITY       = "locality";
    public static final String FIELD_COUNTRY_CODE   = "countryCode";
    public static final String FIELD_ZIP_CODE       = "zipCode";
    public static final String FIELD_AREA           = "area";

    public static final String FIELD_PERSON_ID      = "personId";

    /**
     * Postal address street name.
     */
    @Getter
    @Setter
    @NotNull(message = "Postal address: 'streetName' cannot be null!")
    @Column(name = "STREET_NAME")
    private String streetName;

    /**
     * Postal address street number.
     */
    @Getter
    @Setter
    @NotNull(message = "Postal address: 'streetNumber' cannot be null!")
    @Column(name = "STREET_NUMBER")
    private String streetNumber;

    /**
     * Postal address locality.
     */
    @Getter
    @Setter
    @NotNull(message = "Postal address: 'locality' cannot be null!")
    @Column(name = "LOCALITY")
    private String locality;

    /**
     * Postal address country code (ISO Alpha-3 code).
     */
    @Getter
    @Setter
    @NotNull(message = "Postal address: 'countryCode' cannot be null!")
    @Column(name = "COUNTRY_CODE", length = 3)
    private String countryCode;

    /**
     * Postal address zip (postal) code.
     */
    @Getter
    @Setter
    @NotNull(message = "Postal address: 'zipCode' cannot be null!")
    @Column(name = "ZIP_CODE", length = 10)
    private String zipCode;

    /**
     * Postal address area/region/department depending on the country.
     */
    @Getter
    @Setter
    @Column(name = "AREA")
    private String area;

    /**
     * Is it a default postal address?
     */
    @Getter
    @Setter
    @Column(name = "IS_DEFAULT")
    private Boolean isDefault;

    /**
     * Postal address type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "ADDRESS_TYPE")
    private AddressType addressType;

    /**
     * Postal address category type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY_TYPE")
    private AddressCategoryType categoryType;

    /**
     * The person identifier this postal address belongs to.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Getter
    @Setter
    @ManyToOne(targetEntity = PersonServerEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID", nullable = false)
    private PersonServerEntity person;

    /**
     * Creates a new postal address.
     */
    public PostalAddressServerEntity()
    {
        super(EntityType.POSTAL_ADDRESS);
    }
}
