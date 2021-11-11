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
package com.hemajoo.commerce.cherry.persistence.person.repository;

import com.hemajoo.commerce.cherry.commons.type.StatusType;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberType;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPhoneNumberEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

/**
 * JPA repository for the phone number entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface PhoneNumberRepository extends JpaRepository<ServerPhoneNumberEntity, UUID>, JpaSpecificationExecutor<ServerPhoneNumberEntity>
{
    /**
     * Returns the list of phone numbers matching the given status type.
     * @param statusType Status type.
     * @return List of phone numbers.
     */
    List<ServerPhoneNumberEntity> findByStatusType(StatusType statusType);

    /**
     * Returns the list of phone numbers matching the given type.
     * @param type Phone number type.
     * @return List of phone numbers.
     */
    List<ServerPhoneNumberEntity> findByPhoneType(PhoneNumberType type);

    /**
     * Returns the list of phone numbers matching the given category type.
     * @param type Phone number category type.
     * @return List of phone numbers.
     */
    List<ServerPhoneNumberEntity> findByCategoryType(PhoneNumberCategoryType type);

    /**
     * Returns the list of phone numbers matching the given country code.
     * @param code Country code (ISO Alpha-3).
     * @return List of phone numbers.
     */
    List<ServerPhoneNumberEntity> findByCountryCode(String code);

    /**
     * Returns the list of phone numbers matching the given value.
     * @param isDefault True to get the default phone numbers, false otherwise.
     * @return List of phone numbers.
     */
    List<ServerPhoneNumberEntity> findByIsDefault(boolean isDefault);

    /**
     * Returns the list of phone numbers belonging to the given person identifier.
     * @param personId Person identifier.
     * @return List of phone numbers.
     */
    List<ServerPhoneNumberEntity> findByPersonId(long personId);

    /**
     * Returns the list of phone numbers matching the given specification.
     * @param specification Phone number specification.
     * @return List of phone numbers.
     */
    @NotNull
    List<ServerPhoneNumberEntity> findAll(final Specification<ServerPhoneNumberEntity> specification);
}
