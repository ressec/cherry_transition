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
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerEmailAddressEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

/**
 * Email address repository.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface EmailAddressRepository extends JpaRepository<ServerEmailAddressEntity, UUID>, JpaSpecificationExecutor<ServerEmailAddressEntity>
{
    /**
     * Returns a list of email addresses given an address type.
     * @param addressType Address type.
     * @return List of matching email addresses.
     */
    List<ServerEmailAddressEntity> findByAddressType(AddressType addressType);

    /**
     * Returns a list of email addresses given a status type.
     * @param statusType Status type.
     * @return List of matching email addresses.
     */
    List<ServerEmailAddressEntity> findByStatusType(StatusType statusType);

    /**
     * Returns a list of default or not default email addresses.
     * @param isDefaultEmail Is the default email address?
     * @return List of matching email addresses.
     */
    List<ServerEmailAddressEntity> findByIsDefaultEmail(Boolean isDefaultEmail);

    /**
     * Returns a list of email addresses belonging to a person.
     * @param personId Person identifier.
     * @return List of matching email addresses.
     */
    List<ServerEmailAddressEntity> findByPersonId(UUID personId);

//    /**
//     * Returns a list of email addresses matching the given set of criteria.
//     * @param id Email address identifier, can be null.
//     */
//    @Query(value = "SELECT * FROM EMAIL_ADDRESS e " +
//            "WHERE e.IS_DEFAULT = :isDefault" +
//            "AND e.EMAIL_ID = COALESCE(NULLIF(:id, ''), e.EMAIL_ID) " +
//            "AND e.EMAIL LIKE COALESCE(NULLIF(:email, ''), e.EMAIL) " +
//            "AND e.TYPE LIKE COALESCE(NULLIF(:type, ''), e.TYPE) " +
//            "AND e.STATUS LIKE COALESCE(NULLIF(:status, ''), e.STATUS)" +
//            "AND e.PERSON_ID = COALESCE(NULLIF(:personId, ''), e.PERSON_ID)",
//            nativeQuery = true)
//    List<EmailAddressEntity> findByCriteria(
//            @Param(value = "id") Long id,
//            @Param(value = "email") String email,
//            @Param(value = "isDefault") boolean isDefault,
//            @Param(value = "type") String type,
//            @Param(value = "status") String status,
//            @Param(value = "personId") Long personId);

    List<ServerEmailAddressEntity> findAll(final Specification<ServerEmailAddressEntity> specification);
}
