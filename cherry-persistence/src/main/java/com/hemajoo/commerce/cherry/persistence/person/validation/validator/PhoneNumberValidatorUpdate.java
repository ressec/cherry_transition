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

import com.hemajoo.commerce.cherry.model.person.entity.ClientPhoneNumberEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EntityValidationException;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.PhoneNumberCheckUpdate;
import com.hemajoo.commerce.cherry.persistence.person.validation.engine.PhoneNumberValidationEngine;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Phone number validator associated to the {@link PhoneNumberCheckUpdate} constraint used to validate the entity is valid tobe updated.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class PhoneNumberValidatorUpdate implements ConstraintValidator<PhoneNumberCheckUpdate, ClientPhoneNumberEntity>
{
    /**
     * Phone number validation engine.
     */
    @Autowired
    private PhoneNumberValidationEngine engine;

    @Override
    public void initialize(PhoneNumberCheckUpdate constraint) { /* Empty */}

    @Override
    @SuppressWarnings("squid:S1166")
    public boolean isValid(ClientPhoneNumberEntity entity, ConstraintValidatorContext context)
    {
        try
        {
            engine.isPersonIdValid(entity.getPerson().getId());
            engine.isIdValid(entity);
            engine.isPhoneNumberUnique(entity);
            engine.isPhoneNumberDefault(entity);

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
