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
import com.hemajoo.commerce.cherry.model.document.DocumentException;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.model.person.search.EmailAddressSearch;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import com.hemajoo.commerce.cherry.persistence.person.entity.EmailAddressServerEntity;
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
    /**
     * Returns the number of email addresses.
     * @return Number of email addresses.
     */
    Long count();

    /**
     * Finds an email address given its identifier.
     * @param id Email address identifier.
     * @return Email address if found, null otherwise.
     */
    EmailAddressServerEntity findById(UUID id);

    /**
     * Saves an email address.
     * @param emailAddress Email address.
     * @return Saved email address.
     * @throws EmailAddressException Thrown in case an error occurred while trying to save the email address.
     */
    EmailAddressServerEntity save(EmailAddressServerEntity emailAddress) throws EmailAddressException, DocumentException;

    /**
     * Deletes an email address given its identifier.
     * @param id Email address identfier.
     */
    void deleteById(UUID id);

    /**
     * Returns the email addresses.
     * @return List of email addresses.
     */
    List<EmailAddressServerEntity> findAll();

    /**
     * Returns a list of email addresses given an address type.
     * @param type Address type.
     * @return List of matching email addresses.
     */
    List<EmailAddressServerEntity> findByAddressType(AddressType type);

    /**
     * Returns a list of email addresses given a status type.
     * @param status Status type.
     * @return List of matching email addresses.
     */
    List<EmailAddressServerEntity> findByStatus(StatusType status);

    /**
     * Returns a list of default or not default email addresses.
     * @param defaultEmail Is it the default email address?
     * @return List of matching email addresses.
     */
    List<EmailAddressServerEntity> findByDefaultEmail(boolean defaultEmail);

    /**
     * Returns a list of email addresses belonging to a person.
     * @param personId Person identifier.
     * @return List of matching email addresses.
     */
    List<EmailAddressServerEntity> findByPersonId(UUID personId);

    /**
     * Returns the email addresses matching the given set of predicates.
     * @param emailAddress Email address search object containing the predicates.
     * @return List of email addresses matching the given predicates.
     */
    List<EmailAddressServerEntity> search(final @NonNull EmailAddressSearch emailAddress);
}
