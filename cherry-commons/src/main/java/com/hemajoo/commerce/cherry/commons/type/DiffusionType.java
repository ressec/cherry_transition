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
package com.hemajoo.commerce.cherry.commons.type;

/**
 * An enumeration representing the several possible diffusion types.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public enum DiffusionType
{
    /**
     * The underlying information is public and can be diffused publicly.
     */
    PUBLIC,

    /**
     * The underlying information can be publicly diffused is a consent is provided.
     */
    PUBLIC_WITH_CONSENT,

    /**
     * The underlying information is private and must not be diffused publicly.
     */
    PRIVATE,

    /**
     * The underlying information can be diffused only to persons of the organization the underlying person belongs to.
     */
    ORGANIZATION
}
