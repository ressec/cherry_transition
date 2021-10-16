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
package com.hemajoo.commerce.cherry.persistence.person.validation.validator;

import com.hemajoo.commerce.cherry.model.person.entity.EmailAddress;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.persistence.person.entity.EmailAddressServerEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.PersonServerEntity;
import com.hemajoo.commerce.cherry.persistence.person.service.EmailAddressService;
import com.hemajoo.commerce.cherry.persistence.person.service.PersonService;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.ValidEmailAddressForCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator associated to the {@link ValidEmailAddressForCreation} constraint used to validate an email
 * address is valid to be created.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class EmailAddressValidatorForCreation implements ConstraintValidator<ValidEmailAddressForCreation, EmailAddress>
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

    @Override
    public void initialize(ValidEmailAddressForCreation constraint)
    {
        // Empty.
    }

    @Override
    public boolean isValid(EmailAddress emailAddress, ConstraintValidatorContext context)
    {
        try
        {
//            checkUniqueness(emailAddress);
//            checkActiveAndDefaultUniqueness(emailAddress);
        }
        catch (Exception e)
        {
            context.buildConstraintViolationWithTemplate(e.getMessage()).addConstraintViolation();
            return false;
        }

        return true;
    }

    /**
     * Checks the person being the owner of an email address has only one active default email address set per address type..
     * @param emailAddress Email address persistent entity.
     * @throws EmailAddressException Thrown in case the underlying person already has an active default email address set!
     */
    public void checkActiveAndDefaultUniqueness(EmailAddressServerEntity emailAddress) throws EmailAddressException
    {
        if (emailAddress.isDefaultEmail() && emailAddress.isActive())
        {
            PersonServerEntity person = personService.findById(emailAddress.getPerson().getId());
            EmailAddressServerEntity defaultEmailAddress = person.getDefaultEmailAddress();
            if (defaultEmailAddress.getIdentity() != emailAddress.getIdentity() || emailAddress.getId() == null)
            {
                throw new EmailAddressException(
                        String.format(
                                "Person with id: '%s' already has an active default email address!",
                                emailAddress.getPerson().getIdentity()),
                        HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * Checks the person being the owner of an email address does not already hold such email address.
     * @param emailAddress Email address persistent entity.
     * @throws EmailAddressException Thrown in case the underlying person already holds such an email address!
     */
    public void checkUniqueness(final EmailAddressServerEntity emailAddress) throws EmailAddressException
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
