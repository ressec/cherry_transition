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

import com.hemajoo.commerce.cherry.model.person.entity.ClientPersonEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.model.person.type.GenderType;
import com.hemajoo.commerce.cherry.model.person.type.PersonType;
import com.hemajoo.commerce.cherry.persistence.base.randomizer.AbstractBaseServerEntityRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import lombok.experimental.UtilityClass;
import org.ressec.avocado.core.random.EnumRandomGenerator;

import java.util.UUID;

/**
 * Person generator.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public final class PersonRandomizer extends AbstractBaseServerEntityRandomizer
{
    /**
     * Person type enumeration generator.
     */
    private static final EnumRandomGenerator PERSON_TYPE_GENERATOR = new EnumRandomGenerator(PersonType.class);

    /**
     * Gender type enumeration generator.
     */
    private static final EnumRandomGenerator GENDER_TYPE_GENERATOR = new EnumRandomGenerator(GenderType.class);

    /**
     * Generates a new random persistent person.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * @return Random person.
     */
    public static ServerPersonEntity generatePersistent(final boolean withRandomId)
    {
        var entity = new ServerPersonEntity();
        AbstractBaseServerEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        entity.setFirstName(FAKER.name().firstName());
        entity.setLastName(FAKER.name().lastName());
        entity.setBirthDate(FAKER.date().birthday(18, 70));
        entity.setPersonType((PersonType) PERSON_TYPE_GENERATOR.gen());
        entity.setGenderType((GenderType) GENDER_TYPE_GENERATOR.gen());

        return entity;
    }

    /**
     * Generates a new random client person.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @return Random person.
     */
    public static ClientPersonEntity generateClient(final boolean withRandomId)
    {
        var entity = new ClientPersonEntity();
        AbstractBaseServerEntityRandomizer.populateBaseFields(entity);
   
        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        entity.setFirstName(FAKER.name().firstName());
        entity.setLastName(FAKER.name().lastName());
        entity.setBirthDate(FAKER.date().birthday(18, 70));
        entity.setPersonType((PersonType) PERSON_TYPE_GENERATOR.gen());
        entity.setGenderType((GenderType) GENDER_TYPE_GENERATOR.gen());

        return entity;
    }

    /**
     * Create a random person with its dependencies (i.e: email addresses, postal addresses and phone numbers).
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @param bound Maximum number of dependencies to generate.
     * @return Person.
     * @throws EmailAddressException Raised in case an error occurred when trying to create an email address!
     */
    public static ServerPersonEntity generateWithDependencies(final boolean withRandomId, final int bound) throws EmailAddressException
    {
        var person = generatePersistent(withRandomId);

        int count = bound > 0 ? bound : AbstractBaseServerEntityRandomizer.DEFAULT_DEPENDENCY_BOUND;
        for (var i = 0; i < count; i++)
        {
            person.addEmailAddress(EmailAddressRandomizer.generatePersistent(withRandomId));
            person.addPhoneNumber(PhoneNumberRandomizer.generatePersistent(withRandomId));
            person.addPostalAddress(PostalAddressRandomizer.generatePersistent(withRandomId));
        }

        return person;
    }
}
