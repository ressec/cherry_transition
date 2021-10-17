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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

/**
 * Phone number repository.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface PhoneNumberRepository extends JpaRepository<ServerPhoneNumberEntity, UUID>, JpaSpecificationExecutor<ServerPhoneNumberEntity>
{
    /**
     * Returns a list of phone numbers matching a given {@link StatusType}.
     * @param statusType Status type.
     * @return List of matching phone numbers.
     */
    List<ServerPhoneNumberEntity> findByStatusType(StatusType statusType);

    /**
     * Returns a list of phone numbers matching a given {@link PhoneNumberType}.
     * @param type Phone number type.
     * @return List of matching phone numbers.
     */
    List<ServerPhoneNumberEntity> findByPhoneType(PhoneNumberType type);

    /**
     * Returns a list of phone numbers matching a given {@link PhoneNumberCategoryType}.
     * @param type Phone number category type.
     * @return List of matching phone numbers.
     */
    List<ServerPhoneNumberEntity> findByCategoryType(PhoneNumberCategoryType type);

    /**
     * Returns a list of phone numbers matching a given country code.
     * @param code Country code (ISO Alpha-3).
     * @return List of matching phone numbers.
     */
    List<ServerPhoneNumberEntity> findByCountryCode(String code);

    /**
     * Returns a list of default or not default phone numbers.
     * @param isDefault Is default phone number?
     * @return List of matching phone numbers.
     */
    List<ServerPhoneNumberEntity> findByIsDefault(boolean isDefault);

    /**
     * Returns a list of phone numbers belonging to a person.
     * @param personId Person identifier.
     * @return List of matching phone numbers.
     */
    List<ServerPhoneNumberEntity> findByPersonId(long personId);

    /**
     * Find phone numbers given predicates.
     * @param specification Specification of the predicates.
     * @return List of matching phone numbers.
     */
    List<ServerPhoneNumberEntity> findAll(final Specification<ServerPhoneNumberEntity> specification);
}
