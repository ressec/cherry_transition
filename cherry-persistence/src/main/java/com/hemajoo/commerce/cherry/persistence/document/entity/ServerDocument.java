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
package com.hemajoo.commerce.cherry.persistence.document.entity;

import com.hemajoo.commerce.cherry.model.base.entity.BaseEntity;
import com.hemajoo.commerce.cherry.model.document.base.Document;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServerEntity;

/**
 * Behavior of a server document entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface ServerDocument extends Document, ServerEntity
{
//    /**
//     * Returns the owner entity of this document.
//     * @return Owner entity.
//     */
//    BaseEntity getOwner();

    /**
     * Returns the owner entity of this document.
     * @param <T> Type of the owner.
     * @return Owner entity.
     */
    <T extends BaseEntity & ServerEntity> T getOwner();

//    /**
//     * Sets the owner entity of this document.
//     * @param owner Owner entity.
//     */
//    void setOwner(final BaseEntity owner);

    /**
     * Sets the owner entity of this document.
     * @param <T> Type of the owner.
     * @param owner Owner entity.
     */
    <T extends BaseEntity & ServerEntity> void setOwner(final T owner);
}
