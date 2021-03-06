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
import com.hemajoo.commerce.cherry.model.person.entity.ClientEmailAddressEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.model.person.exception.EntityException;
import com.hemajoo.commerce.cherry.model.person.search.SearchEmailAddress;
import com.hemajoo.commerce.cherry.persistence.base.entity.EntityComparator;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.service.EmailAddressService;
import com.hemajoo.commerce.cherry.persistence.person.service.PersonService;
import lombok.NonNull;
import org.javers.core.diff.changetype.ValueChange;
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
    @Autowired
    private PersonService servicePerson;

    @Autowired
    private EmailAddressService serviceEmailAddress;

    /**
     * Checks if the given search object is valid or not?
     * @param search Search email address object.
     * @throws EmailAddressException Thrown to indicate an error occurred while submitting a search email address object.
     */
    public static void isSearchValid(final @NonNull SearchEmailAddress search) throws EmailAddressException
    {
        SearchEmailAddress reference = new SearchEmailAddress();

        if (EntityComparator.getJavers().compare(reference, search).getChangesByType(ValueChange.class).isEmpty())
        {
            throw new EmailAddressException("Search object must contain at least one search value!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Checks the person identifier is valid.
     * <br>
     * The person identifier owning the email address must exist.
     * @param personId Person identifier.
     * @throws EmailAddressException Thrown in case the validation failed!
     */
    public void validatePersonId(final @NonNull UUID personId) throws EmailAddressException
    {
        if (servicePerson.findById(personId) == null)
        {
            throw new EmailAddressException(String.format("Person with id: %s does not exist!", personId));
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
        validateEmailAddressId(emailAddress.getId());
    }

    /**
     * Checks the email address identifier is valid.
     * <br>
     * The email address identifier must exist.
     * @param id Email address identifier.
     * @throws EmailAddressException Thrown in case the validation failed!
     */
    public void validateEmailAddressId(final @NonNull UUID id) throws EmailAddressException
    {
        if (serviceEmailAddress.findById(id) == null)
        {
            throw new EmailAddressException(String.format("Email address with id: %s does not exist!", id), HttpStatus.NOT_FOUND);
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
            ServerPersonEntity person = servicePerson.findById(emailAddress.getPerson().getId());
            ServerEmailAddressEntity defaultEmailAddress = person.getDefaultEmailAddress();
            if (!Objects.equals(defaultEmailAddress.getIdentity(), emailAddress.getIdentity()) || emailAddress.getId() == null)
            {
                throw new EmailAddressException(
                        String.format(
                                "Person with id: '%s' already has an active default email address!",
                                emailAddress.getPerson().getId()),
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
        ServerPersonEntity person = servicePerson.findById(emailAddress.getPerson().getId());

        if (person == null)
        {
            throw new EmailAddressException(
                    String.format(
                            "Person id: '%s' cannot be found!",
                            emailAddress.getPerson().getId()),
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
                                    emailAddress.getPerson(),
                                    emailAddress.getEmail()),
                            HttpStatus.BAD_REQUEST);
                }
            }
            else
            {
                throw new EmailAddressException(String.format("Email address: '%s' already exist!", emailAddress.getEmail()), HttpStatus.BAD_REQUEST);
            }
        }
    }

    public void validateEmailForUpdate(final @NonNull ClientEmailAddressEntity emailAddress) throws EntityException
    {
        validateEmailAddressId(emailAddress.getId());
        validateEmailEntityType(emailAddress.getEntityType());
        //validatePersonId(emailAddress.getOwner().getId());
        validateDefaultEmail(emailAddress);
        validateNameUniqueness(emailAddress);
    }

    private void validateEmailEntityType(final EntityType entityType) throws EntityException
    {
        if (entityType != EntityType.EMAIL_ADDRESS)
        {
            throw new EntityException(entityType, String.format("Entity type: %s expected!", EntityType.EMAIL_ADDRESS), HttpStatus.BAD_REQUEST);
        }
    }
}
