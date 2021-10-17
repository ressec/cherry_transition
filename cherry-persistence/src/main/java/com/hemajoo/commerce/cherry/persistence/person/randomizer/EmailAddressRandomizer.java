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
import com.hemajoo.commerce.cherry.model.person.entity.ClientEmailAddressEntity;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import com.hemajoo.commerce.cherry.persistence.base.randomizer.AbstractBaseServerEntityRandomizer;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerEmailAddressEntity;
import lombok.experimental.UtilityClass;
import org.ressec.avocado.core.random.EnumRandomGenerator;

import java.util.UUID;

/**
 * Email address random generator.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public final class EmailAddressRandomizer extends AbstractBaseServerEntityRandomizer
{
    /**
     * Address type enumeration generator.
     */
    private static final EnumRandomGenerator ADDRESS_TYPE_GENERATOR = new EnumRandomGenerator(AddressType.class).exclude(AddressType.UNSPECIFIED);

    /**
     * Create a random persistent email address.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @return Email address.
     */
    public static ServerEmailAddressEntity generatePersistent(final boolean withRandomId)
    {
        var entity = new ServerEmailAddressEntity();
        AbstractBaseServerEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        entity.setEmail(FAKER.internet().emailAddress());
        entity.setAddressType((AddressType) ADDRESS_TYPE_GENERATOR.gen());
        entity.setDefaultEmail(RANDOM.nextBoolean());

        return entity;
    }

    /**
     * Create a random persistent email address with an associated document.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @param count Number of documents to generate.
     * @return Email address.
     * @throws DocumentContentException Thrown in case an error occurred while trying to generate a document.
     */
    public static ServerEmailAddressEntity generatePersistentWithDocument(final boolean withRandomId, final int count) throws DocumentContentException
    {
        var entity = new ServerEmailAddressEntity();
        ServerDocumentEntity document;
        AbstractBaseServerEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        for (int i = 0; i < count; i++)
        {
            document = DocumentRandomizer.generatePersistent(true);
            entity.addDocument(document);
        }

        entity.setEmail(FAKER.internet().emailAddress());
        entity.setAddressType((AddressType) ADDRESS_TYPE_GENERATOR.gen());
        entity.setDefaultEmail(RANDOM.nextBoolean());

        return entity;
    }

    /**
     * Create a random client email address.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @return Email address.
     */
    public static ClientEmailAddressEntity generateClient(final boolean withRandomId)
    {
        var entity = new ClientEmailAddressEntity();
        AbstractBaseServerEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        entity.setEmail(FAKER.internet().emailAddress());
        entity.setAddressType((AddressType) ADDRESS_TYPE_GENERATOR.gen());
        entity.setDefaultEmail(RANDOM.nextBoolean());

        return entity;
    }

    /**
     * Create a random client email address with associated documents.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @param count Number of documents to generate.
     * @return Email address.
     * @throws DocumentContentException Thrown in case an error occurred while trying to generate a document.
     */
    public static ClientEmailAddressEntity generateClientWithDocument(final boolean withRandomId, final int count) throws DocumentContentException
    {
        ClientDocumentEntity document;
        ClientEmailAddressEntity entity = new ClientEmailAddressEntity();
        AbstractBaseServerEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        for (int i = 0; i < count; i++)
        {
            document = DocumentRandomizer.generateClient(true);
            entity.addDocument(document);
        }

        entity.setEmail(FAKER.internet().emailAddress());
        entity.setAddressType((AddressType) ADDRESS_TYPE_GENERATOR.gen());
        entity.setDefaultEmail(RANDOM.nextBoolean());

        return entity;
    }
}
