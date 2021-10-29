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

import com.hemajoo.commerce.cherry.model.person.entity.ClientEmailAddressEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.service.EmailAddressService;
import com.hemajoo.commerce.cherry.persistence.person.service.PersonService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

/**
 * Email address validation engine.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Component
public final class EmailAddressValidationEngine
{
    /**
     * Email address persistence service.
     */
    @Autowired
    private EmailAddressService emailAddressService;

    /**
     * Person persistence service.
     */
    @Autowired
    private PersonService personService;

    /**
     * Checks the person identifier is valid.
     * <br>
     * The person identifier owning the email address must exist.
     * @param emailAddress Email address to check.
     * @throws EmailAddressException Thrown in case the validation failed!
     */
    public void validatePersonId(final @NonNull ClientEmailAddressEntity emailAddress) throws EmailAddressException
    {
        if (emailAddress.getOwner() == null || emailAddress.getOwner().getId() == null)
        {
            throw new EmailAddressException("Person id cannot be null!");
        }

        if (personService.findById(emailAddress.getOwner().getId()) == null)
        {
            throw new EmailAddressException(String.format("Person with id: %s does not exist!", emailAddress.getOwner().getId()));
        }
    }

    /**
     * Checks the email address identifier is valid.
     * <br>
     * The email address identifier must exist.
     * @param emailAddress Email address to check.
     * @throws EmailAddressException Thrown in case the validation failed!
     */
    public void validateEmailAddressId(final @NonNull ClientEmailAddressEntity emailAddress) throws EmailAddressException
    {
        validateEmailAddressId(emailAddress.getId().toString());
    }

    /**
     * Checks the email address identifier is valid.
     * <br>
     * The email address identifier must exist.
     * @param id Email address identifier.
     * @throws EmailAddressException Thrown in case the validation failed!
     */
    public void validateEmailAddressId(final @NonNull String id) throws EmailAddressException
    {
        if (emailAddressService.findById(UUID.fromString(id)) == null)
        {
            throw new EmailAddressException(String.format("Email address with id: %s does not exist!", id));
        }
    }

    /**
     * Checks if the email address is active and a default one.
     * <br>
     * An active default email address must be unique for the person owning the email address.
     * @param emailAddress Email address to check.
     * @throws EmailAddressException Thrown in case the validation failed!
     */
    public void validateDefaultEmail(final @NonNull ClientEmailAddressEntity emailAddress) throws EmailAddressException
    {
        if (Boolean.TRUE.equals(emailAddress.getIsDefaultEmail()) && emailAddress.isActive())
        {
            ServerPersonEntity person = personService.findById(emailAddress.getOwner().getId());
            ServerEmailAddressEntity defaultEmailAddress = person.getDefaultEmailAddress();
            if (!Objects.equals(defaultEmailAddress.getIdentity(), emailAddress.getIdentity()) || emailAddress.getId() == null)
            {
                throw new EmailAddressException(
                        String.format(
                                "Person with id: '%s' already has an active default email address!",
                                emailAddress.getOwner().getId()),
                        HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * Checks the person being the owner of the email address does not already hold such email address.
     * @param emailAddress Email address persistent entity.
     * @throws EmailAddressException Thrown in case the validation failed!
     */
    public void validateNameUniqueness(final @NonNull ClientEmailAddressEntity emailAddress) throws EmailAddressException
    {
        ServerPersonEntity person = personService.findById(emailAddress.getOwner().getId());

        if (person == null)
        {
            throw new EmailAddressException(
                    String.format(
                            "Person id: '%s' cannot be found!",
                            emailAddress.getOwner().getId()),
                    HttpStatus.BAD_REQUEST);
        }

        if (person.existEmail(emailAddress.getEmail()))
        {
            if (emailAddress.getId() != null) // New email address entity
            {
                ServerEmailAddressEntity email = person.getEmailById(emailAddress.getId());
                if (email != null && !email.getId().equals(emailAddress.getId()))
                {
                    throw new EmailAddressException(
                            String.format(
                                    "Email address: '%s' already belongs to another entity: '%s'!",
                                    emailAddress.getOwner(),
                                    emailAddress.getEmail()),
                            HttpStatus.BAD_REQUEST);
                }
            }
            else
            {
                throw new EmailAddressException(String.format("Email address: '%s' already exist", emailAddress.getEmail()), HttpStatus.BAD_REQUEST);
            }
        }
    }
}