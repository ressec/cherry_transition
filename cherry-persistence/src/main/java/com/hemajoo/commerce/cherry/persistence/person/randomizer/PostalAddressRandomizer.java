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
import com.hemajoo.commerce.cherry.model.person.entity.ClientPostalAddressEntity;
import com.hemajoo.commerce.cherry.model.person.type.AddressCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import com.hemajoo.commerce.cherry.persistence.base.randomizer.AbstractBaseEntityRandomizer;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPostalAddressEntity;
import lombok.experimental.UtilityClass;
import org.ressec.avocado.core.random.EnumRandomGenerator;

import java.util.UUID;

/**
 * Random postal address generator.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public final class PostalAddressRandomizer extends AbstractBaseEntityRandomizer
{
    /**
     * Address type enumeration generator.
     */
    private static final EnumRandomGenerator ADDRESS_TYPE_GENERATOR = new EnumRandomGenerator(AddressType.class);

    /**
     * Address category type enumeration generator.
     */
    private static final EnumRandomGenerator ADDRESS_CATEGORY_TYPE_GENERATOR = new EnumRandomGenerator(AddressCategoryType.class);

    /**
     * Create a random persistent postal address.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @return Postal address.
     */
    public static ServerPostalAddressEntity generatePersistent(final boolean withRandomId)
    {
        var entity = new ServerPostalAddressEntity();
        AbstractBaseEntityRandomizer.populateBaseFields(entity);

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
     * Generates a new random server postal address with associated documents.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @param count Number of random documents to generate.
     * @return Postal address.
     * @throws DocumentContentException Thrown in case an error occurred while trying to generate a document.
     */
    public static ServerPostalAddressEntity generateServerWithDocument(final boolean withRandomId, final int count) throws DocumentContentException
    {
        var entity = new ServerPostalAddressEntity();
        ServerDocumentEntity document;
        AbstractBaseEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        for (int i = 0; i < count; i++)
        {
            document = DocumentRandomizer.generateServerEntity(true);
            entity.addDocument(document);
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
    public static ClientPostalAddressEntity generateClient(final boolean withRandomId)
    {
        var entity = new ClientPostalAddressEntity();
        AbstractBaseEntityRandomizer.populateBaseFields(entity);

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

    /**
     * Generates a new random client postal address with associated documents.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @param count Number of documents to generate.
     * @return Postal address.
     * @throws DocumentContentException Thrown in case an error occurred while trying to generate a document.
     */
    public static ClientPostalAddressEntity generateClientWithDocument(final boolean withRandomId, final int count) throws DocumentContentException
    {
        ClientDocumentEntity document;
        ClientPostalAddressEntity entity = new ClientPostalAddressEntity();
        AbstractBaseEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        for (int i = 0; i < count; i++)
        {
            document = DocumentRandomizer.generateClientEntity(true);
            entity.addDocument(document.getIdentity());
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
