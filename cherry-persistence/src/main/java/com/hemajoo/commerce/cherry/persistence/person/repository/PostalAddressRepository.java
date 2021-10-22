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
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPostalAddressEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

/**
 * JPA repository for the postal address entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface PostalAddressRepository extends JpaRepository<ServerPostalAddressEntity, UUID>, JpaSpecificationExecutor<ServerPostalAddressEntity>
{
    /**
     * Returns the list of postal addresses matching the given address type.
     * @param type Address type.
     * @return List of postal addresses.
     * @see AddressType
     */
    List<ServerPostalAddressEntity> findByAddressType(AddressType type);

    /**
     * Returns the list of postal addresses matching the given status type.
     * @param statusType Status type.
     * @return List of postal addresses.
     * @see StatusType
     */
    List<ServerPostalAddressEntity> findByStatusType(StatusType statusType);

    /**
     * Returns the list of postal addresses matching the given country code.
     * @param countryCode Country code (ISO Alpha-3).
     * @return List of postal addresses.
     */
    List<ServerPostalAddressEntity> findByCountryCode(String countryCode);

    /**
     * Returns the list of postal addresses matching the given locality.
     * @param locality Locality.
     * @return List of postal addresses.
     */
    List<ServerPostalAddressEntity> findByLocality(String locality);

    /**
     * Returns the list of postal addresses matching the given zip code.
     * @param zipCode Zip or postal code.
     * @return List of postal addresses.
     */
    List<ServerPostalAddressEntity> findByZipCode(String zipCode);

    /**
     * Returns the list of postal addresses matching the given area.
     * @param area Area.
     * @return List of postal addresses.
     */
    List<ServerPostalAddressEntity> findByArea(String area);

    /**
     * Returns the list of default or not default postal address.
     * @param isDefault True to get a list of default postal addresses, false otherwise.
     * @return List of postal addresses.
     */
    List<ServerPostalAddressEntity> findByIsDefault(boolean isDefault);

    /**
     * Returns the list of postal addresses belonging to the given person identifier.
     * @param personId Person identifier.
     * @return List of postal addresses.
     */
    List<ServerPostalAddressEntity> findByPersonId(UUID personId);

    /**
     * Returns the list of postal addresses matching the given specification.
     * @param specification Postal address specification.
     * @return List of postal addresses.
     */
    @NotNull
    List<ServerPostalAddressEntity> findAll(final Specification<ServerPostalAddressEntity> specification);
}
