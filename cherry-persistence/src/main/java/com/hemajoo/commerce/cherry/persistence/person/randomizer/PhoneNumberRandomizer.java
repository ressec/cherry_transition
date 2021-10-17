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

import com.hemajoo.commerce.cherry.model.person.entity.ClientPhoneNumberEntity;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberType;
import com.hemajoo.commerce.cherry.persistence.base.randomizer.AbstractBaseServerEntityRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.entity.PhoneNumberServerEntity;
import lombok.experimental.UtilityClass;
import org.ressec.avocado.core.random.EnumRandomGenerator;

import java.util.UUID;

/**
 * Phone number random generator.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public final class PhoneNumberRandomizer extends AbstractBaseServerEntityRandomizer
{
    /**
     * Phone number type enumeration generator.
     */
    private static final EnumRandomGenerator PHONE_NUMBER_TYPE_GENERATOR = new EnumRandomGenerator(PhoneNumberType.class).exclude(PhoneNumberType.UNSPECIFIED);

    /**
     * Phone number category type enumeration generator.
     */
    private static final EnumRandomGenerator PHONE_NUMBER_CATEGORY_TYPE_GENERATOR = new EnumRandomGenerator(PhoneNumberCategoryType.class).exclude(PhoneNumberCategoryType.UNSPECIFIED);

    /**
     * Generates a new random persistent phone number.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @return Phone number.
     */
    public static PhoneNumberServerEntity generatePersistent(final boolean withRandomId)
    {
        var entity = new PhoneNumberServerEntity();
        AbstractBaseServerEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        entity.setNumber(FAKER.phoneNumber().cellPhone());
        entity.setCountryCode(FAKER.address().countryCode().toUpperCase());
        entity.setPhoneType((PhoneNumberType) PHONE_NUMBER_TYPE_GENERATOR.gen());
        entity.setCategoryType((PhoneNumberCategoryType) PHONE_NUMBER_CATEGORY_TYPE_GENERATOR.gen());
        entity.setIsDefault(RANDOM.nextBoolean());

        return entity;
    }

    /**
     * Generates a new random client phone number.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @return Phone number.
     */
    public static ClientPhoneNumberEntity generateClient(final boolean withRandomId)
    {
        var entity = new ClientPhoneNumberEntity();
        AbstractBaseServerEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        entity.setNumber(FAKER.phoneNumber().cellPhone());
        entity.setCountryCode(FAKER.address().countryCode().toUpperCase());
        entity.setPhoneType((PhoneNumberType) PHONE_NUMBER_TYPE_GENERATOR.gen());
        entity.setCategoryType((PhoneNumberCategoryType) PHONE_NUMBER_CATEGORY_TYPE_GENERATOR.gen());
        entity.setIsDefault(RANDOM.nextBoolean());

        return entity;
    }
}
