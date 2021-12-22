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
package com.hemajoo.commerce.cherry.persistence.base.validation;

import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.model.base.entity.ClientEntity;
import com.hemajoo.commerce.cherry.model.base.search.SearchEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EntityValidationException;
import com.hemajoo.commerce.cherry.persistence.base.entity.EntityComparator;
import lombok.NonNull;
import org.javers.core.diff.changetype.ValueChange;
import org.springframework.http.HttpStatus;

public abstract class AbstractEntityValidationEngine implements EntityValidationEngine
{
    /**
     * Expected entity type.
     */
    private final EntityType entityType;

    /**
     * Creates a new abstract entity validation engine given the expected entity type.
     * @param entityType Entity type.
     */
    protected AbstractEntityValidationEngine(final EntityType entityType)
    {
        this.entityType = entityType;
    }

    @Override
    public final <T extends ClientEntity> void isIdValid(T entity) throws EntityValidationException
    {
        isIdValid(entity.getId());
    }

    /**
     * Internal service used to check if the given search entity is valid.
     * @param reference Reference (empty) entity.
     * @param search Search entity.
     * @param <T> Search entity type.
     * @throws EntityValidationException Thrown to indicate an error occurred when trying to validate the search entity.
     */
    protected final <T extends SearchEntity> void internalIsSearchValid(final @NonNull T reference, final @NonNull T search) throws EntityValidationException
    {
        if (EntityComparator.getJavers().compare(reference, search).getChangesByType(ValueChange.class).isEmpty())
        {
            throw new EntityValidationException(
                    String.format("Search object of type: '%s' must contain at least one search value!", search.getEntityType()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Checks if the entity type is valid.
     * @param type Entity type.
     * @throws EntityValidationException Thrown to indicate an error occurred when trying to validate the entity type.
     */
    protected final void isTypeValid(final EntityType type) throws EntityValidationException
    {
        if (type != this.entityType)
        {
            throw new EntityValidationException(String.format(
                    "Entity type: '%s' was expected but received: '%s'!",
                    type,
                    entityType), HttpStatus.BAD_REQUEST);
        }
    }
}
