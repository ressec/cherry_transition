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
package com.hemajoo.commerce.cherry.persistence.base.randomizer;

import com.github.javafaker.Faker;
import com.hemajoo.commerce.cherry.commons.type.StatusType;
import com.hemajoo.commerce.cherry.model.base.entity.ClientBaseEntity;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServerBaseEntity;
import lombok.NonNull;
import org.ressec.avocado.core.random.EnumRandomGenerator;

import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Base server entity randomizer.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class AbstractBaseServerEntityRandomizer
{
    /**
     * Default dependency bound.
     */
    protected static final int DEFAULT_DEPENDENCY_BOUND = 10;

    /**
     * Faker generator.
     */
    protected static final Faker FAKER = new Faker();

    /**
     * Random number generator.
     */
    protected static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Entity status type enumeration generator.
     */
    protected static final EnumRandomGenerator STATUS_TYPE_GENERATOR = new EnumRandomGenerator(StatusType.class).exclude(StatusType.UNSPECIFIED);

    /**
     * Creates a new base server entity randomizer.
     */
    protected AbstractBaseServerEntityRandomizer()
    {
        // Empty
    }

    /**
     * Populates the base persistent entity with random values.
     * @param parent Parent entity.
     */
    public static void populateBaseFields(final @NonNull ServerBaseEntity parent)
    {
        String description = FAKER.hitchhikersGuideToTheGalaxy().marvinQuote();
        if (description.length() > 255)
        {
            description = description.substring(1, 255);
        }
        parent.setDescription(description);
        parent.setReference(FAKER.ancient().hero());
        parent.setStatusType((StatusType) STATUS_TYPE_GENERATOR.gen());
        parent.setCreatedBy(FAKER.internet().emailAddress());
        parent.setCreatedDate(FAKER.date().past(100, TimeUnit.DAYS)); // Created in the previous 100 days
        parent.setModifiedBy(FAKER.internet().emailAddress());
        parent.setCreatedDate(FAKER.date().past(1, TimeUnit.HOURS)); // Modified in the last hour
    }

    /**
     * Populates the base client entity with random values.
     * @param parent Parent entity.
     */
    public static void populateBaseFields(final @NonNull ClientBaseEntity parent)
    {
        String description = FAKER.hitchhikersGuideToTheGalaxy().marvinQuote();
        if (description.length() > 255)
        {
            description = description.substring(1, 255);
        }
        parent.setDescription(description);
        parent.setReference(FAKER.ancient().hero());
        parent.setStatusType((StatusType) STATUS_TYPE_GENERATOR.gen());
        parent.setCreatedBy(FAKER.internet().emailAddress());
        parent.setCreatedDate(FAKER.date().past(100, TimeUnit.DAYS)); // Created in the previous 100 days
        parent.setModifiedBy(FAKER.internet().emailAddress());
        parent.setCreatedDate(FAKER.date().past(1, TimeUnit.HOURS)); // Modified in the last hour
    }

    /**
     * Returns a random element from a list.
     * @param list List.
     * @param <T> Element type.
     * @return Random element.
     */
    public static <T> T getRandomElement(final List<T> list)
    {
        return list.get(RANDOM.nextInt(list.size()));
    }
}
