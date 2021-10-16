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

import com.hemajoo.commerce.cherry.model.document.DocumentException;
import com.hemajoo.commerce.cherry.model.person.search.PersonSearch;
import com.hemajoo.commerce.cherry.persistence.base.entity.BaseServerEntity;
import com.hemajoo.commerce.cherry.persistence.document.entity.DocumentServerEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.EmailAddressServerEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.PersonServerEntity;
import com.hemajoo.commerce.cherry.persistence.person.repository.PersonRepository;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Person persistence service.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface PersonService
{
    /**
     * Returns the person JPA repository.
     * @return Person JPA repository.
     */
    PersonRepository getRepository();

    /**
     * Returns the number of persons.
     * @return Number of persons.
     */
    Long count();

    /**
     * Finds a person given its identifier.
     * @param id Person identifier.
     * @return Person if found, null otherwise.
     */
    PersonServerEntity findById(UUID id);

    /**
     * Saves a person.
     * @param person Person.
     * @return Saved person.
     * @throws DocumentException Thrown in case an error occurred with one of the document when trying to save the person!
     */
    PersonServerEntity save(PersonServerEntity person) throws DocumentException;

    /**
     * Deletes a person given its identifier.
     * @param id Person identifier.
     */
    void deleteById(UUID id);

    /**
     * Returns the persons.
     * @return List of persons.
     */
    List<PersonServerEntity> findAll();

    /**
     * Returns the persons matching the given set of predicates.
     * @param person Person search object containing the predicates.
     * @return List of persons matching the given predicates.
     */
    List<PersonServerEntity> search(final @NonNull PersonSearch person);

    List<EmailAddressServerEntity> getEmailAddresses(final @NonNull PersonServerEntity person);

    PersonServerEntity loadEmailAddresses(final @NonNull PersonServerEntity person);

    List<DocumentServerEntity> getDocuments(final @NonNull BaseServerEntity entity);

    void saveAndFlush(final @NonNull PersonServerEntity person);
}
