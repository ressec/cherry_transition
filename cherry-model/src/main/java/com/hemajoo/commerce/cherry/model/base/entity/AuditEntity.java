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
package com.hemajoo.commerce.cherry.model.base.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Interface providing audit behavior.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface AuditEntity extends Serializable
{
    /**
     * Returns the creation date.
     * @return Date.
     */
    Date getCreatedDate();

    /**
     * Sets the creation date.
     * @param date Creation date.
     */
    void setCreatedDate(final Date date);

    /**
     * Returns the last modification date.
     * @return Date.
     */
    Date getModifiedDate();

    /**
     * Sets the last modification date.
     * @param date Modification date.
     */
    void setModifiedDate(final Date date);

    /**
     * Returns the creation author.
     * @return Author.
     */
    String getCreatedBy();

    /**
     * Sets the creation author.
     * @param author Creation author.
     */
    void setCreatedBy(final String author);

    /**
     * Returns the last modification author.
     * @return Author.
     */
    String getModifiedBy();

    /**
     * Sets the last modification author.
     * @param author Modification author.
     */
    void setModifiedBy(final String author);
}
