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
package com.hemajoo.commerce.cherry.persistence.base.validation;

import javax.validation.ConstraintValidatorContext;

/**
 * Abstract base constraint validator for constraint annotations used on types allowing to redefine the error message
 * dynamically.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class BaseConstraintValidator
{
    /**
     * Error message template.
     */
    protected String errorMessage = "Default message template";

    /**
     * Creates a new base constraint validator instance.
     * @param message Error message.
     */
    protected BaseConstraintValidator(final String message)
    {
        errorMessage = message;
    }

    /**
     * Sets the validation error message.
     * @param context Constraint validator context.
     * @param template Message template.
     */
    protected final void setValidationErrorMessage(ConstraintValidatorContext context, String template)
    {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(template).addConstraintViolation();
    }
}
