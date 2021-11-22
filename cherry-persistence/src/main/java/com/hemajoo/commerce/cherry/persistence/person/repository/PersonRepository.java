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
 * JPA repository for the person entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface PersonRepository extends JpaRepository<ServerPersonEntity, UUID>, JpaSpecificationExecutor<ServerPersonEntity>
{
    /**
     * Returns a person matching the given identifier.
     * @param id Person identifier.
     * @return Person.
     */
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
     * Returns the list of persons matching the given person type.
     * @param personType Person type.
     * @return List of persons.
     * @see PersonType
     */
    List<ServerPersonEntity> findByPersonType(PersonType personType);

    /**
     * Returns the list of persons matching the given gender type.
     * @param gender Gender type.
     * @return List of persons.
     * @see GenderType
     */
    List<ServerPersonEntity> findByGenderType(GenderType gender);

    /**
     * Returns the list of persons matching the given last name.
     * @param lastName Last name (strict).
     * @return List of persons.
     */
    List<ServerPersonEntity> findByLastName(String lastName);

    /**
     * Returns the list of persons matching the given first name.
     * @param firstName First name (strict).
     * @return List of persons.
     */
    List<ServerPersonEntity> findByFirstName(String firstName);

    /**
     * Returns the list of persons matching the given specification.
     * @param specification Person specification.
     * @return List of persons.
     */
    @NotNull
    List<ServerPersonEntity> findAll(final Specification<ServerPersonEntity> specification);
}
