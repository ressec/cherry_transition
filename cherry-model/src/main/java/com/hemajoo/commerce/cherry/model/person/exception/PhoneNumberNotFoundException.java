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

import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * Checked exception thrown to indicate a phone number identifier cannot be found.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@SuppressWarnings("java:S110")
public class PhoneNumberNotFoundException extends EmailAddressException
{
    /**
     * Default serialization identifier.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Thrown to indicate a phone number cannot be found.
     * @param id Phone number identifier.
     */
    public PhoneNumberNotFoundException(final Long id)
    {
        super(String.format("Phone number id: '%s' cannot be found!", id), HttpStatus.NOT_FOUND);
    }
}
