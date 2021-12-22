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
package com.hemajoo.commerce.cherry.persistence.person.validation.constraint;

import com.hemajoo.commerce.cherry.persistence.person.validation.validator.EmailAddressValidatorUpdate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Validation constraint to be used on field, parameter or local variables used to check
 * if an email address client entity is valid for an update.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Documented
@Constraint(validatedBy = EmailAddressValidatorUpdate.class)
@Target( { ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailAddressCheckCreate
{
    String message() default "{null}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
