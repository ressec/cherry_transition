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

import com.hemajoo.commerce.cherry.persistence.person.repository.EmailAddressRepository;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.ValidEmailAddressId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

/**
 * Constraint validator for the {@link ValidEmailAddressId} constraint to validate an email address identifier.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class EmailAddressIdValidator implements ConstraintValidator<ValidEmailAddressId, String>
{
    /**
     * Email address {@code JPA} repository.
     */
    @Autowired
    private EmailAddressRepository emailAddressRepository;

    @Override
    public void initialize(ValidEmailAddressId constraint)
    {
        // Empty.
    }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context)
    {
        return emailAddressRepository.existsById(UUID.fromString(id));
    }
}
