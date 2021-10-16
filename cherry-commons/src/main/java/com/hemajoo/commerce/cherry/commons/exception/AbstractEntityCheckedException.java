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
package com.hemajoo.commerce.cherry.commons.exception;

import com.hemajoo.commerce.cherry.commons.type.EntityType;
import lombok.Getter;
import org.ressec.avocado.core.exception.checked.AbstractCheckedException;
import org.springframework.http.HttpStatus;

/**
 * Abstract checked exception thrown to indicate an error occurred with an entity being part of the {@code Cherry} data
 * model.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class AbstractEntityCheckedException extends AbstractCheckedException
{
    /**
     * Http status.
     */
    @Getter
    private final HttpStatus status;

    /**
     * Underlying entity type.
     */
    @Getter
    private final EntityType entityType;

    /**
     * Default serialization identifier.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Thrown to indicate that an error occurred with an entity.
     * @param type Underlying entity type.
     * @param exception Parent {@link Exception}.
     * @param status Http status type.
     * @see EntityType
     * @see HttpStatus
     */
    protected AbstractEntityCheckedException(final EntityType type, final Exception exception, final HttpStatus status)
    {
        super(exception);
        this.status = status;
        this.entityType = type;
    }

    /**
     * Thrown to indicate that an error occurred with an entity.
     * @param type Underlying entity type.
     * @param message Message describing the error being the cause of the raised exception.
     * @param status Http status type.
     * @see EntityType
     * @see HttpStatus
     */
    protected AbstractEntityCheckedException(final EntityType type, final String message, final HttpStatus status)
    {
        super(message);
        this.status = status;
        this.entityType = type;
    }

    /**
     * Thrown to indicate that an error occurred with an entity.
     * @param type Underlying entity type.
     * @param message Message describing the error being the cause of the raised exception.
     * @param exception Parent {@link Exception}.
     * @param status Http status type.
     * @see EntityType
     * @see HttpStatus
     */
    protected AbstractEntityCheckedException(final EntityType type, final String message, final Exception exception, final HttpStatus status)
    {
        super(message, exception);
        this.status = status;
        this.entityType = type;
    }
}
