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

import com.hemajoo.commerce.cherry.persistence.person.repository.PersonRepository;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.ValidPersonId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

/**
 * Constraint validator of the {@link ValidPersonId} constraint for a person identifier.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class PersonIdValidator implements ConstraintValidator<ValidPersonId, String>
{
    /**
     * {@code JPA} repository.
     */
    @Autowired
    private PersonRepository personRepository;

    @Override
    public void initialize(ValidPersonId constraint)
    {
        // Empty.
    }

    @Override
    public boolean isValid(String personId, ConstraintValidatorContext context)
    {
        return personRepository.existsById(UUID.fromString(personId));
    }
}
