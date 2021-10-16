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
package com.hemajoo.commerce.cherry.model.person.exception;

import com.hemajoo.commerce.cherry.commons.exception.AbstractEntityCheckedException;
import com.hemajoo.commerce.cherry.commons.type.EntityType;
import org.springframework.http.HttpStatus;

/**
 * Checked exception thrown to indicate an error occurred with a person.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class PersonException extends AbstractEntityCheckedException
{
    /**
     * Default serialization identifier.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Thrown to indicate that an error occurred with a person.
     * @param exception Parent exception.
     * @param status {@link HttpStatus}.
     */
    public PersonException(final Exception exception, final HttpStatus status)
    {
        super(EntityType.PERSON, exception, status);
    }

    /**
     * Thrown to indicate that an error occurred with a person.
     * @param message Message describing the error being the cause of the raised exception.
     */
    public PersonException(final String message)
    {
        super(EntityType.PERSON, message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Thrown to indicate that an error occurred with a person.
     * @param message Message describing the error being the cause of the raised exception.
     * @param status {@link HttpStatus}.
     */
    public PersonException(final String message, final HttpStatus status)
    {
        super(EntityType.PERSON, message, status);
    }

    /**
     * Thrown to indicate that an error occurred with a person.
     * @param message Message describing the error being the cause of the raised exception.
     * @param exception Parent exception.
     * @param status {@link HttpStatus}.
     */
    public PersonException(final String message, final Exception exception, final HttpStatus status)
    {
        super(EntityType.PERSON, message, exception, status);
    }
}
