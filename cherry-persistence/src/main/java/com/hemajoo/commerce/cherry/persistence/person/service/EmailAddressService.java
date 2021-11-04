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

import com.hemajoo.commerce.cherry.commons.type.StatusType;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.model.person.exception.EntityException;
import com.hemajoo.commerce.cherry.model.person.search.SearchEmailAddress;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.persistence.person.repository.EmailAddressRepository;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Provides the behavior of the email address persistence service.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface EmailAddressService
{
    EmailAddressRepository getRepository();

    /**
     * Returns the total number of email addresses.
     * @return Total number of email addresses.
     */
    Long count();

    /**
     * Returns the email address matching the given identifier.
     * @param id Email address identifier.
     * @return Email address.
     */
    ServerEmailAddressEntity findById(UUID id);

    /**
     * Updates the given server email address entity.
     * @param emailAddress Server email address entity to update.
     * @return Updated server email address entity.
     * @throws EmailAddressException Thrown in case an error occurred while trying to update the server email address entity.
     */
    ServerEmailAddressEntity update(final ServerEmailAddressEntity emailAddress) throws EntityException;

    /**
     * Saves the given email address.
     * @param emailAddress Email address.
     * @return Saved email address.
     * @throws EmailAddressException Thrown in case an error occurred while trying to save the email address.
     */
    ServerEmailAddressEntity save(ServerEmailAddressEntity emailAddress) throws EmailAddressException, DocumentException;

    /**
     * Deletes the email address matching the given identifier.
     * @param id Email address identifier.
     */
    void deleteById(UUID id);

    /**
     * Returns all email addresses.
     * @return List of email addresses.
     */
    List<ServerEmailAddressEntity> findAll();

    /**
     * Returns the list of email addresses matching the given address type.
     * @param type Address type.
     * @return List of email addresses.
     */
    List<ServerEmailAddressEntity> findByAddressType(AddressType type);

    /**
     * Returns the list of email addresses matching the given status type.
     * @param status Status type.
     * @return List of email addresses.
     */
    List<ServerEmailAddressEntity> findByStatus(StatusType status);

    /**
     * Returns the list of default or not default email addresses.
     * @param isDefaultEmail Is it the default email address?
     * @return List of matching email addresses.
     */
    List<ServerEmailAddressEntity> findByIsDefaultEmail(Boolean isDefaultEmail);

    /**
     * Returns the list of email addresses belonging to a person.
     * @param personId Person identifier.
     * @return List of matching email addresses.
     */
    List<ServerEmailAddressEntity> findByPersonId(UUID personId);

    /**
     * Returns the email addresses matching the given set of predicates.
     * @param emailAddress Email address search object containing the predicates.
     * @return List of email addresses matching the given predicates.
     */
    List<ServerEmailAddressEntity> search(final @NonNull SearchEmailAddress emailAddress);
}
