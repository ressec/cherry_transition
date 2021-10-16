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
package com.hemajoo.commerce.cherry.model.document;

import lombok.NonNull;

import java.util.Arrays;

/**
 * Enumeration representing the several possible document types.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public enum DocumentType
{
    /**
     * Unspecified document type.
     */
    UNSPECIFIED,

    /**
     * Generic document type.
     */
    DOCUMENT_GENERIC,

    /**
     * Photo document type.
     */
    DOCUMENT_PHOTO,

    /**
     * Invoice document type.
     */
    DOCUMENT_INVOICE,

    /**
     * Icon document type.
     */
    DOCUMENT_ICON;

    /**
     * Creates a new document type given its type as a string.
     * @param value Document type as a string.
     * @return Document type.
     * @throws IllegalArgumentException Thrown in case an error occurred while trying to create a document type enumerated value.
     */
    public static DocumentType from(final @NonNull String value)
    {
        for (DocumentType element : DocumentType.values())
        {
            if (element.name().equalsIgnoreCase(value))
            {
                return element;
            }
        }

        throw new IllegalArgumentException(String.format(
                "Cannot created a document type with value: '%s'. Possible values are: '%s'",
                value,
                Arrays.toString(DocumentType.values())));
    }
}
