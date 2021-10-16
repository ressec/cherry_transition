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

import com.hemajoo.commerce.cherry.model.person.entity.EmailAddress;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.persistence.person.entity.EmailAddressServerEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.PersonServerEntity;
import com.hemajoo.commerce.cherry.persistence.person.service.EmailAddressService;
import com.hemajoo.commerce.cherry.persistence.person.service.PersonService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class EmailAddressRuleEngine
{
    /**
     * Service for the email addresses.
     */
    @Autowired
    private EmailAddressService emailAddressService;

    /**
     * Service for the persons.
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
    public void validatePersonId(final @NonNull EmailAddress emailAddress) throws EmailAddressException
    {
        if (emailAddress.getPerson() == null || emailAddress.getPerson().getId() == null)
        {
            throw new EmailAddressException("Person id cannot be null!");
        }

        if (personService.findById(emailAddress.getPerson().getId()) == null)
        {
            throw new EmailAddressException(String.format("Person with id: %s does not exist!", emailAddress.getPerson().getId()));
        }
    }

    /**
     * Checks the email address identifier is valid.
     * <br>
     * The email address identifier must exist.
     * @param emailAddress Email address to check.
     * @throws EmailAddressException Thrown in case the validation failed!
     */
    public void validateEmailAddressId(final @NonNull EmailAddress emailAddress) throws EmailAddressException
    {
        if (emailAddress.getId() == null)
        {
            throw new EmailAddressException("Email address id cannot be null!");
        }

        if (emailAddressService.findById(emailAddress.getId()) == null)
        {
            throw new EmailAddressException(String.format("Email address with id: %s does not exist!", emailAddress.getId()));
        }
    }

    /**
     * Checks if the email address is active and a default one.
     * <br>
     * An active default email address must be unique for the person owning the email address.
     * @param emailAddress Email address to check.
     * @throws EmailAddressException Thrown in case the validation failed!
     */
    public void validateDefaultEmail(final @NonNull EmailAddress emailAddress) throws EmailAddressException
    {
        if (emailAddress.isDefaultEmail() && emailAddress.isActive())
        {
            PersonServerEntity person = personService.findById(emailAddress.getPerson().getId());
            EmailAddressServerEntity defaultEmailAddress = person.getDefaultEmailAddress();
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
    public void validateEmailAddressNameUniqueness(final @NonNull EmailAddress emailAddress) throws EmailAddressException
    {
        PersonServerEntity person = personService.findById(emailAddress.getPerson().getId());
        if (person.existEmail(emailAddress.getEmail()))
        {
            EmailAddressServerEntity emailById = person.getEmailById(emailAddress.getId());
            // TODO Services to implement
//            EmailAddressEntity emailByName = person.getEmailByName(emailAddress.getName());
//            List<EmailAddressEntity> emailsByType = person.getEmails(emailAddress.getAddressType());
//            List<EmailAddressEntity> emailsByStatus = person.getEmails(emailAddress.getStatusType());
//            List<EmailAddressEntity> emailsByName = person.getEmails(emailAddress.getName());

            if (emailById != null && !emailById.getId().equals(emailAddress.getId()))
            {
                throw new EmailAddressException(
                        String.format(
                                "Person with id: '%s' already holds the email address: '%s'",
                                emailAddress.getPerson().getIdentity(),
                                emailAddress.getEmail()),
                        HttpStatus.BAD_REQUEST);
            }
        }
    }
}
