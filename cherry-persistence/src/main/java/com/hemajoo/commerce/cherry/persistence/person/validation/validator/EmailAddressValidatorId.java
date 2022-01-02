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

import com.hemajoo.commerce.cherry.model.person.exception.EntityValidationException;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.EmailAddressCheckId;
import com.hemajoo.commerce.cherry.persistence.person.validation.engine.EmailAddressValidationEngine;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

/**
 * Validator used to validate an <b>Email Address</b> identifier is valid.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 * @see EmailAddressCheckId
 */
public class EmailAddressValidatorId implements ConstraintValidator<EmailAddressCheckId, String>
{
    /**
     * Email address validation engine.
     */
    @Autowired
    private EmailAddressValidationEngine engine;

    @Override
    public void initialize(EmailAddressCheckId constraint) { /* Empty */ }

    @Override
    @SuppressWarnings("squid:S1166")
    public boolean isValid(String id, ConstraintValidatorContext context)
    {
        try
        {
            engine.isIdValid(UUID.fromString(id));

            return true;
        }
        catch (EntityValidationException e)
        {
            context.buildConstraintViolationWithTemplate(e.getStatus() + "@@" + e.getMessage()).addConstraintViolation();
            context.disableDefaultConstraintViolation(); // Allow to disable the standard constraint message
        }

        return false;
    }
}
