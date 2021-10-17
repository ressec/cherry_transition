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
import com.hemajoo.commerce.cherry.model.person.type.GenderType;
import com.hemajoo.commerce.cherry.model.person.type.PersonType;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Person repository.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface PersonRepository extends JpaRepository<ServerPersonEntity, UUID>, JpaSpecificationExecutor<ServerPersonEntity>
{
    @NotNull
    @EntityGraph(attributePaths = "documents")
    Optional<ServerPersonEntity> findById(final @NonNull UUID id);

    /**
     * Returns a list of persons matching the given status.
     * @param statusType Status type.
     * @return List of persons.
     * @see StatusType
     */
    List<ServerPersonEntity> findByStatusType(StatusType statusType);

    /**
     * Returns a list of persons matching the given person type.
     * @param personType Person type.
     * @return List of persons.
     * @see PersonType
     */
    List<ServerPersonEntity> findByPersonType(PersonType personType);

    /**
     * Returns a list of persons matching the given gender type.
     * @param gender Gender type.
     * @return List of persons.
     * @see GenderType
     */
    List<ServerPersonEntity> findByGenderType(GenderType gender);

    /**
     * Returns a list of persons matching the given last name.
     * @param lastName Last name (strict).
     * @return List of persons.
     */
    List<ServerPersonEntity> findByLastName(String lastName);

    /**
     * Returns a list of persons matching the given first name.
     * @param firstName First name (strict).
     * @return List of persons.
     */
    List<ServerPersonEntity> findByFirstName(String firstName);

    /**
     * Find persons given predicates.
     * @param specification Specification of the predicates.
     * @return List of matching persons.
     */
    List<ServerPersonEntity> findAll(final Specification<ServerPersonEntity> specification);

//    /**
//     * Returns a list of persons matching the given set of criteria.
//     * @param lastName Last name (is optional can be partial and make use of {@code SQL} wildcard characters).
//     * @param firstName First name (is optional can be partial and make use of {@code SQL} wildcard characters).
//     * @param birthDate Person birthdate (optional).
//     * @param type Person type (optional).
//     * @param status Person status type (optional).
//     * @param gender Person gender type (optional).
//     * @return List of persons.
//     * @see PersonType
//     * @see GenderType
//     * @see StatusType
//     */
//    @Query(value = "SELECT * FROM PERSON p " +
//            "WHERE p.LASTNAME LIKE COALESCE(NULLIF(:lastName, ''), p.LASTNAME) " +
//            "AND p.FIRSTNAME LIKE COALESCE(NULLIF(:firstName, ''), p.FIRSTNAME) " +
//            "AND p.BIRTHDATE = COALESCE(NULLIF(:birthDate, ''), p.BIRTHDATE) " +
//            "AND p.TYPE LIKE COALESCE(NULLIF(:type, ''), p.TYPE) " +
//            "AND p.GENDER LIKE COALESCE(NULLIF(:gender, ''), p.GENDER) " +
//            "AND p.STATUS LIKE COALESCE(NULLIF(:status, ''), p.STATUS)",
//            nativeQuery = true)
//    List<PersonEntity> findByCriteria(
//            @Param(value = "lastName") String lastName,
//            @Param(value = "firstName") String firstName,
//            @Param(value = "birthDate") String birthDate,
//            @Param(value = "type") String type,
//            @Param(value = "gender") String gender,
//            @Param(value = "status") String status);
}
