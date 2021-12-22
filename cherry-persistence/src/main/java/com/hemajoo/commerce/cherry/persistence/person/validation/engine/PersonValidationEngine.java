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
package com.hemajoo.commerce.cherry.persistence.person.validation.engine;

import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.model.base.entity.ClientEntity;
import com.hemajoo.commerce.cherry.model.base.search.SearchEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EntityValidationException;
import com.hemajoo.commerce.cherry.persistence.base.validation.AbstractEntityValidationEngine;
import com.hemajoo.commerce.cherry.persistence.person.service.PersonService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Person validation engine.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Component
public final class PersonValidationEngine extends AbstractEntityValidationEngine
{
    @Autowired
    private PersonService servicePerson;

    public PersonValidationEngine()
    {
        super(EntityType.PERSON);
    }

    /**
     * Checks if a person identifier is valid.
     * <br>
     * For an entity identifier to be valid, it must exist in the underlying persistence system.
     * @param personId Person identifier.
     * @throws EntityValidationException Thrown to indicate an error occurred when trying to validate the person identifier.
     */
    public void isIdValid(final @NonNull UUID personId) throws EntityValidationException
    {
        if (!servicePerson.existId(personId))
        {
            throw new EntityValidationException(String.format("Person with id: '%s' cannot be found!", personId), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public <T extends ClientEntity> void isValidForUpdate(@NonNull T entity) throws EntityValidationException
    {
        //TODO Implement!
    }

    @Override
    public <T extends ClientEntity> void isValidForCreate(T entity) throws EntityValidationException
    {
        //TODO Implement!
    }

    @Override
    public <T extends SearchEntity> void isSearchValid(T search) throws EntityValidationException
    {
        //TODO Implement!
    }
}
