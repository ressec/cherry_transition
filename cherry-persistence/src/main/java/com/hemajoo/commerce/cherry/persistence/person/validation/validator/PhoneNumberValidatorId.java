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
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.PhoneNumberCheckId;
import com.hemajoo.commerce.cherry.persistence.person.validation.engine.PhoneNumberValidationEngine;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

/**
 * Phone number validator for the {@link PhoneNumberCheckId} constraint used to validate a phone number identifier is valid (exist).
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class PhoneNumberValidatorId implements ConstraintValidator<PhoneNumberCheckId, String>
{
    /**
     * Phone number validation engine.
     */
    @Autowired
    private PhoneNumberValidationEngine engine;

    @Override
    public void initialize(PhoneNumberCheckId constraint) { /* Empty */ }

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
