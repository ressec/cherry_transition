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
import com.hemajoo.commerce.cherry.model.person.search.PhoneNumberSearch;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberType;
import com.hemajoo.commerce.cherry.persistence.person.entity.PhoneNumberServerEntity;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Phone number persistence service.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface PhoneNumberService
{
    /**
     * Returns the number of phone numbers.
     * @return Number of phone numbers.
     */
    Long count();

    /**
     * Finds a phone number given its identifier.
     * @param id Phone number identifier.
     * @return Phone number if found, null otherwise.
     */
    PhoneNumberServerEntity findById(UUID id);

    /**
     * Saves a phone number.
     * @param phoneNumber Phone number.
     * @return Saved phone number.
     */
    PhoneNumberServerEntity save(PhoneNumberServerEntity phoneNumber);

    /**
     * Deletes a phone number given its identifier.
     * @param id Phone number identifier.
     */
    void deleteById(UUID id);

    /**
     * Returns the phone numbers.
     * @return List of phone numbers.
     */
    List<PhoneNumberServerEntity> findAll();

    /**
     * Returns a list of phone numbers given a phone number type.
     * @param type Phone number type.
     * @return List of matching phone numbers.
     */
    List<PhoneNumberServerEntity> findByPhoneType(PhoneNumberType type);

    /**
     * Returns a list of phone numbers given a phone number category type.
     * @param category Phone number category type.
     * @return List of matching phone numbers.
     */
    List<PhoneNumberServerEntity> findByCategoryType(PhoneNumberCategoryType category);

    /**
     * Returns a list of phone numbers given a status type.
     * @param status Status type.
     * @return List of matching phone numbers.
     */
    List<PhoneNumberServerEntity> findByStatus(StatusType status);

    /**
     * Returns a list of default or not default phone numbers.
     * @param isDefault Is it a default phone number?
     * @return List of matching phone numbers.
     */
    List<PhoneNumberServerEntity> findByIsDefault(boolean isDefault);

    /**
     * Returns a list of phone numbers belonging to a person.
     * @param personId Person identifier.
     * @return List of matching phone numbers.
     */
    List<PhoneNumberServerEntity> findByPersonId(long personId);

    /**
     * Returns the phone numbers matching the given set of predicates.
     * @param phoneNumber Phone number search object containing the predicates.
     * @return List of phone numbers matching the given predicates.
     */
    List<PhoneNumberServerEntity> search(final @NonNull PhoneNumberSearch phoneNumber);
}
