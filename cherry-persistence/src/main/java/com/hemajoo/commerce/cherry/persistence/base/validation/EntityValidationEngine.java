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

import com.hemajoo.commerce.cherry.model.base.entity.ClientEntity;
import com.hemajoo.commerce.cherry.model.base.search.SearchEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EntityValidationException;

import java.util.UUID;

public interface EntityValidationEngine
{
    /**
     * Checks if an entity identifier is valid.
     * @param entity Entity.
     * @throws EntityValidationException Thrown to indicate an error occurred when trying to validate an entity identifier.
     */
    <T extends ClientEntity> void isIdValid(T entity) throws EntityValidationException;

    /**
     * Checks if an entity identifier is valid.
     * @param entityId Entity identifier (UUID).
     * @throws EntityValidationException Thrown to indicate an error occurred when trying to validate an entity identifier.
     */
    void isIdValid(UUID entityId) throws EntityValidationException;

    /**
     * Checks if an entity is valid to be updated.
     * @param entity Entity.
     * @throws EntityValidationException Thrown to indicate an error occurred when trying to validate the entity to be updated.
     */
    <T extends ClientEntity> void isValidForUpdate(T entity) throws EntityValidationException;

    /**
     * Checks if an entity is valid to be created.
     * @param entity Entity.
     * @throws EntityValidationException Thrown to indicate an error occurred when trying to validate the entity to be created.
     */
    <T extends ClientEntity> void isValidForCreate(T entity) throws EntityValidationException;

    /**
     * Checks if a search entity is valid.
     * @param search Search entity.
     * @throws EntityValidationException Thrown to indicate an error occurred when trying to validate the search entity.
     */
    <T extends SearchEntity> void isSearchValid(T search) throws EntityValidationException;
}
