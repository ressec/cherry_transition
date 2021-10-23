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

import com.hemajoo.commerce.cherry.model.document.ClientDocumentEntity;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentContentException;
import com.hemajoo.commerce.cherry.model.person.entity.ClientPhoneNumberEntity;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberType;
import com.hemajoo.commerce.cherry.persistence.base.randomizer.AbstractBaseEntityRandomizer;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPhoneNumberEntity;
import lombok.experimental.UtilityClass;
import org.ressec.avocado.core.random.EnumRandomGenerator;

import java.util.UUID;

/**
 * Random phone number generator.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public final class PhoneNumberRandomizer extends AbstractBaseEntityRandomizer
{
    /**
     * Phone number type enumeration generator.
     */
    private static final EnumRandomGenerator PHONE_NUMBER_TYPE_GENERATOR = new EnumRandomGenerator(PhoneNumberType.class);

    /**
     * Phone number category type enumeration generator.
     */
    private static final EnumRandomGenerator PHONE_NUMBER_CATEGORY_TYPE_GENERATOR = new EnumRandomGenerator(PhoneNumberCategoryType.class);

    /**
     * Generates a new random persistent phone number.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @return Phone number.
     */
    public static ServerPhoneNumberEntity generateServer(final boolean withRandomId)
    {
        var entity = new ServerPhoneNumberEntity();
        AbstractBaseEntityRandomizer.populateBaseFields(entity);

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
     * Generates a new random server phone number with associated documents.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @param count Number of random documents to generate.
     * @return Phone number.
     * @throws DocumentContentException Thrown in case an error occurred while trying to generate a document.
     */
    public static ServerPhoneNumberEntity generateServerWithDocument(final boolean withRandomId, final int count) throws DocumentContentException
    {
        var entity = new ServerPhoneNumberEntity();
        ServerDocumentEntity document;
        AbstractBaseEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        for (int i = 0; i < count; i++)
        {
            document = DocumentRandomizer.generatePersistent(true);
            entity.addDocument(document);
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
        AbstractBaseEntityRandomizer.populateBaseFields(entity);

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
     * Generates a new random client phone number with associated documents.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @param count Number of documents to generate.
     * @return Phone number.
     * @throws DocumentContentException Thrown in case an error occurred while trying to generate a document.
     */
    public static ClientPhoneNumberEntity generateClientWithDocument(final boolean withRandomId, final int count) throws DocumentContentException
    {
        ClientDocumentEntity document;
        ClientPhoneNumberEntity entity = new ClientPhoneNumberEntity();
        AbstractBaseEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        for (int i = 0; i < count; i++)
        {
            document = DocumentRandomizer.generateClient(true);
            entity.addDocument(document);
        }

        entity.setNumber(FAKER.phoneNumber().cellPhone());
        entity.setCountryCode(FAKER.address().countryCode().toUpperCase());
        entity.setPhoneType((PhoneNumberType) PHONE_NUMBER_TYPE_GENERATOR.gen());
        entity.setCategoryType((PhoneNumberCategoryType) PHONE_NUMBER_CATEGORY_TYPE_GENERATOR.gen());
        entity.setIsDefault(RANDOM.nextBoolean());

        return entity;
    }
}
