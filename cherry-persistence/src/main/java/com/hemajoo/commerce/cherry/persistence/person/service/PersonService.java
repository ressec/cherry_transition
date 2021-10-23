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
package com.hemajoo.commerce.cherry.persistence.person.service;

import com.hemajoo.commerce.cherry.model.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.model.person.search.SearchPerson;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServerBaseEntity;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.repository.PersonRepository;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Provides the behavior of the person persistence service.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface PersonService
{
    /**
     * Returns the person repository.
     * @return Person repository.
     */
    PersonRepository getRepository();

    /**
     * Returns the total number of persons.
     * @return Number of persons.
     */
    Long count();

    /**
     * Returns the person matching the given identifier.
     * @param id Person identifier.
     * @return Person.
     */
    ServerPersonEntity findById(UUID id);

    /**
     * Saves the person.
     * @param person Person.
     * @return Saved person.
     * @throws DocumentException Thrown in case an error occurred with one of the document when trying to save the person!
     */
    ServerPersonEntity save(ServerPersonEntity person) throws DocumentException;

    /**
     * Deletes the person matching the given identifier.
     * @param id Person identifier.
     */
    void deleteById(UUID id);

    /**
     * Returns all the persons.
     * @return List of persons.
     */
    List<ServerPersonEntity> findAll();

    /**
     * Returns the list of persons matching the given specification.
     * @param person Person specification.
     * @return List of persons.
     */
    List<ServerPersonEntity> search(final @NonNull SearchPerson person);

    /**
     * Returns the list of email addresses owned by the given person.
     * @param person Person.
     * @return List of email addresses.
     */
    List<ServerEmailAddressEntity> getEmailAddresses(final @NonNull ServerPersonEntity person);

//    ServerPersonEntity loadEmailAddresses(final @NonNull ServerPersonEntity person);

    /**
     * Returns the list of documents owned by the given base entity.
     * @param entity Base entity.
     * @return List of documents.
     */
    List<ServerDocumentEntity> getDocuments(final @NonNull ServerBaseEntity entity);

    /**
     * Saves the person and flush.
     * @param person Person to save.
     */
    void saveAndFlush(final @NonNull ServerPersonEntity person);
}
