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

import com.hemajoo.commerce.cherry.model.person.entity.ClientEmailAddressEntity;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.ValidEmailAddressForUpdate;
import com.hemajoo.commerce.cherry.persistence.person.validation.engine.EmailAddressRuleEngine;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator associated to the {@link ValidEmailAddressForUpdate} constraint used to validate an email
 * address is valid to be updated.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class EmailAddressValidatorForUpdate implements ConstraintValidator<ValidEmailAddressForUpdate, ClientEmailAddressEntity>
{
    /**
     * Email address rule engine.
     */
    @Autowired
    private EmailAddressRuleEngine emailAddressRuleEngine;

    @Override
    public void initialize(ValidEmailAddressForUpdate constraint)
    {
        // Empty.
    }

    @Override
    public boolean isValid(ClientEmailAddressEntity emailAddress, ConstraintValidatorContext context)
    {
        try
        {
            emailAddressRuleEngine.validatePersonId(emailAddress);
            emailAddressRuleEngine.validateEmailAddressId(emailAddress);
            emailAddressRuleEngine.validateEmailAddressNameUniqueness(emailAddress);
            emailAddressRuleEngine.validateDefaultEmail(emailAddress);

            return true;
        }
        catch (Exception e)
        {
            context.buildConstraintViolationWithTemplate(e.getMessage()).addConstraintViolation();
            context.disableDefaultConstraintViolation(); // Allow to disable the standard constraint message
        }

        return false;
    }
}
