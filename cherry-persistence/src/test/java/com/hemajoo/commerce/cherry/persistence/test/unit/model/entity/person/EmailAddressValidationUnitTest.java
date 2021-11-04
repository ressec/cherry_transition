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
package com.hemajoo.commerce.cherry.persistence.test.unit.model.entity.person;

import com.hemajoo.commerce.cherry.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.EmailAddressRandomizer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test the validation rules for the server email address entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
class EmailAddressValidationUnitTest
{
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("Email address must not be null")
    final void testNullEmailAddress()
    {
        ServerEmailAddressEntity email = EmailAddressRandomizer.generateServerEntity(true);
        email.setEmail(null);

        Set<ConstraintViolation<ServerEmailAddressEntity>> violations = validator.validate(email);

        assertThat(violations)
                .as(String.format("Should have raised a violation rule because email address: '%s' is null or empty!", email.getEmail()))
                .isNotEmpty();
    }

    @Test
    @DisplayName("Email address must be valid")
    final void testValidEmailAddress()
    {
        ServerEmailAddressEntity email = EmailAddressRandomizer.generateServerEntity(true);
        email.setEmail("john.doe#gmail.com");

        Set<ConstraintViolation<ServerEmailAddressEntity>> violations = validator.validate(email);

        assertThat(violations)
                .as(String.format("Email address: %s is supposed to be invalid!", email.getEmail()))
                .isNotEmpty();
    }
}
