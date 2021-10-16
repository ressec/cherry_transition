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
package com.hemajoo.commerce.cherry.persistence.person.randomizer;

import com.hemajoo.commerce.cherry.model.person.entity.PostalAddress;
import com.hemajoo.commerce.cherry.model.person.type.AddressCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import com.hemajoo.commerce.cherry.persistence.base.randomizer.BaseServerEntityRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.entity.PostalAddressServerEntity;
import lombok.experimental.UtilityClass;
import org.ressec.avocado.core.random.EnumRandomGenerator;

import java.util.UUID;

/**
 * Postal address random generator.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public final class PostalAddressRandomizer extends BaseServerEntityRandomizer
{
    /**
     * Address type enumeration generator.
     */
    private static final EnumRandomGenerator ADDRESS_TYPE_GENERATOR = new EnumRandomGenerator(AddressType.class).exclude(AddressType.UNSPECIFIED);

    /**
     * Address category type enumeration generator.
     */
    private static final EnumRandomGenerator ADDRESS_CATEGORY_TYPE_GENERATOR = new EnumRandomGenerator(AddressCategoryType.class).exclude(AddressCategoryType.UNSPECIFIED);

    /**
     * Create a random persistent postal address.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @return Postal address.
     */
    public static PostalAddressServerEntity generatePersistent(final boolean withRandomId)
    {
        var entity = new PostalAddressServerEntity();
        BaseServerEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        entity.setIsDefault(RANDOM.nextBoolean());
        entity.setStreetName(FAKER.address().streetName());
        entity.setStreetNumber(FAKER.address().streetAddressNumber());
        entity.setLocality(FAKER.address().cityName());
        entity.setArea(FAKER.address().state());
        String zipCode = FAKER.address().zipCode();
        entity.setZipCode(zipCode.length() <= 7 ? zipCode : zipCode.substring(0, 7));
        entity.setCountryCode(FAKER.address().countryCode().toUpperCase());
        entity.setAddressType((AddressType) ADDRESS_TYPE_GENERATOR.gen());
        entity.setCategoryType((AddressCategoryType) ADDRESS_CATEGORY_TYPE_GENERATOR.gen());

        return entity;
    }

    /**
     * Create a random client postal address.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @return Postal address.
     */
    public static PostalAddress generateClient(final boolean withRandomId)
    {
        var entity = new PostalAddress();
        BaseServerEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        entity.setStreetName(FAKER.address().streetName());
        entity.setStreetNumber(FAKER.address().streetAddressNumber());
        entity.setLocality(FAKER.address().cityName());
        entity.setArea(FAKER.address().state());
        String zipCode = FAKER.address().zipCode();
        entity.setZipCode(zipCode.length() <= 7 ? zipCode : zipCode.substring(0, 7));
        entity.setCountryCode(FAKER.address().countryCode().toUpperCase());
        entity.setAddressType((AddressType) ADDRESS_TYPE_GENERATOR.gen());
        entity.setCategoryType((AddressCategoryType) ADDRESS_CATEGORY_TYPE_GENERATOR.gen());
        entity.setIsDefault(RANDOM.nextBoolean());

        return entity;
    }
}
