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
import com.hemajoo.commerce.cherry.model.person.entity.ClientPhoneNumberEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EntityValidationException;
import com.hemajoo.commerce.cherry.model.person.search.SearchPhoneNumber;
import com.hemajoo.commerce.cherry.persistence.base.validation.AbstractEntityValidationEngine;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPhoneNumberEntity;
import com.hemajoo.commerce.cherry.persistence.person.service.PersonService;
import com.hemajoo.commerce.cherry.persistence.person.service.PhoneNumberService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

/**
 * Phone number validation object.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Component
public final class PhoneNumberValidationEngine extends AbstractEntityValidationEngine
{
    @Autowired
    private PersonService servicePerson;

    @Autowired
    private PhoneNumberService servicePhoneNumber;

    /**
     * Creates a new phone number validation engine.
     */
    public PhoneNumberValidationEngine()
    {
        super(EntityType.PHONE_NUMBER);
    }

    @Override
    public void isIdValid(final @NonNull UUID id) throws EntityValidationException
    {
        if (servicePhoneNumber.findById(id) == null)
        {
            throw new EntityValidationException(String.format("Phone number with id: '%s' does not exist!", id), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public <T extends ClientEntity> void isValidForUpdate(final @NonNull T phoneNumber) throws EntityValidationException
    {
        isTypeValid(phoneNumber.getEntityType());

        isIdValid(phoneNumber.getId());
        isPhoneNumberDefault((ClientPhoneNumberEntity) phoneNumber);
        isPhoneNumberUnique((ClientPhoneNumberEntity) phoneNumber);
    }

    @Override
    public <T extends ClientEntity> void isValidForCreate(T entity) throws EntityValidationException
    {
        //TODO Implement!
    }

    /**
     * Checks if the given phone number search object is valid.
     * @param search Search phone number object.
     * @throws EntityValidationException Thrown to indicate an error occurred while submitting a search phone number object.
     */
    @Override
    public <T extends SearchEntity> void isSearchValid(final @NonNull T search) throws EntityValidationException
    {
        super.internalIsSearchValid(new SearchPhoneNumber(), search);
    }

    /**
     * Checks if the person identifier is valid.
     * <br>
     * The person identifier owning the phone number must exist.
     * @param personId Person identifier.
     * @throws EntityValidationException Thrown to indicate an error occurred when trying to validate the person entity.
     */
    public void isPersonIdValid(final @NonNull UUID personId) throws EntityValidationException
    {
        if (servicePerson.findById(personId) == null)
        {
            throw new EntityValidationException(String.format("Person with id: %s does not exist!", personId), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Checks if the phone number is active and is the default one.
     * <br>
     * An active default phone number is unique for the person owning the phone number.
     * @param phoneNumber Phone number to check.
     * @throws EntityValidationException Thrown to indicate an error occurred when trying to validate the phone number entity.
     */
    public void isPhoneNumberDefault(final @NonNull ClientPhoneNumberEntity phoneNumber) throws EntityValidationException
    {
        if (Boolean.TRUE.equals(phoneNumber.getIsDefault()) && phoneNumber.isActive())
        {
            ServerPersonEntity person = servicePerson.findById(phoneNumber.getPerson().getId());
            ServerPhoneNumberEntity defaultPhoneNumber = person.getDefaultPhoneNumber();
            if (!Objects.equals(defaultPhoneNumber.getIdentity(), phoneNumber.getIdentity()) || phoneNumber.getId() == null)
            {
                throw new EntityValidationException(
                        String.format(
                                "Person with id: '%s' already has an active default phone number!",
                                phoneNumber.getPerson().getId()),
                        HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * Checks if the person being the owner of the phone number does not already hold such phone number.
     * @param phoneNumber Phone number.
     * @throws EntityValidationException Thrown to indicate an error occurred when trying to validate the phone number entity.
     */
    public void isPhoneNumberUnique(final @NonNull ClientPhoneNumberEntity phoneNumber) throws EntityValidationException
    {
        ServerPersonEntity person = servicePerson.findById(phoneNumber.getPerson().getId());

        if (person == null)
        {
            throw new EntityValidationException(
                    String.format(
                            "Person with id: '%s' cannot be found!",
                            phoneNumber.getPerson().getId()),
                    HttpStatus.BAD_REQUEST);
        }

        if (person.existPhoneNumber(phoneNumber.getNumber()))
        {
            if (phoneNumber.getId() != null) // New phone number entity?
            {
                ServerPhoneNumberEntity serverPhoneNumberEntity = person.getPhoneNumberById(phoneNumber.getId());
                if (serverPhoneNumberEntity != null && !serverPhoneNumberEntity.getId().equals(phoneNumber.getId()))
                {
                    throw new EntityValidationException(
                            String.format(
                                    "Phone number: '%s' already belongs to another entity: '%s'!",
                                    phoneNumber.getPerson(),
                                    phoneNumber.getNumber()),
                            HttpStatus.BAD_REQUEST);
                }
            }
            else
            {
                throw new EntityValidationException(String.format("Phone number: '%s' already exist!", phoneNumber.getNumber()), HttpStatus.BAD_REQUEST);
            }
        }
    }
}
