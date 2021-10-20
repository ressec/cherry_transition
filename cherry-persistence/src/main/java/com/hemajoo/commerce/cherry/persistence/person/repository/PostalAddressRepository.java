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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

/**
 * Postal address repository.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface PostalAddressRepository extends JpaRepository<ServerPostalAddressEntity, UUID>, JpaSpecificationExecutor<ServerPostalAddressEntity>
{
    /**
     * Returns a list of {@link ServerPostalAddressEntity} matching a given address type.
     * @param type Address type.
     * @return List of postal addresses.
     * @see AddressType
     */
    List<ServerPostalAddressEntity> findByAddressType(AddressType type);

    /**
     * Returns a list of {@link ServerPostalAddressEntity} matching a given status type.
     * @param statusType Status type.
     * @return List of postal addresses.
     * @see StatusType
     */
    List<ServerPostalAddressEntity> findByStatusType(StatusType statusType);

    /**
     * Returns a list of {@link ServerPostalAddressEntity} matching a given country code.
     * @param countryCode Country code (ISO Alpha-3).
     * @return List of postal addresses.
     */
    List<ServerPostalAddressEntity> findByCountryCode(String countryCode);

    /**
     * Returns a list of {@link ServerPostalAddressEntity} matching a given locality.
     * @param locality Locality.
     * @return List of postal addresses.
     */
    List<ServerPostalAddressEntity> findByLocality(String locality);

    /**
     * Returns a list of {@link ServerPostalAddressEntity} matching a given zip code.
     * @param zipCode Zip or postal code.
     * @return List of postal addresses.
     */
    List<ServerPostalAddressEntity> findByZipCode(String zipCode);

    /**
     * Returns a list of {@link ServerPostalAddressEntity} matching a given area.
     * @param area Area.
     * @return List of postal addresses.
     */
    List<ServerPostalAddressEntity> findByArea(String area);

    /**
     * Returns a list of default or not default postal addresses.
     * @param isDefault Is default postal address?
     * @return List of postal addresses.
     */
    List<ServerPostalAddressEntity> findByIsDefault(boolean isDefault);

    /**
     * Returns a list of postal addresses belonging to a person.
     * @param personId Person identifier.
     * @return List of postal addresses.
     */
    List<ServerPostalAddressEntity> findByPersonId(long personId);

//    /**
//     * Returns a list of postal addresses matching the given set of criteria.
//     * @param streetName Street name (is optional can be partial and make use of {@code SQL} wildcard characters).
//     * @param streetNumber Street number (is optional can be partial and make use of {@code SQL} wildcard characters).
//     * @param type Postal address type (optional).
//     * @param status Status type (optional).
//     * @param locality Locality (is optional can be partial and make use of {@code SQL} wildcard characters).
//     * @param area Area (is optional can be partial and make use of {@code SQL} wildcard characters).
//     * @param zipCode Zip code (is optional can be partial and make use of {@code SQL} wildcard characters).
//     * @param countryCode Country code ; ISO Alpha-3 (is optional can be partial and make use of {@code SQL} wildcard characters).
//     * @return List of {@link PersonEntity}s.
//     * @see AddressType
//     * @see StatusType
//     */
//    @Query(value = "SELECT * FROM POSTAL_ADDRESS a " +
//            "WHERE a.STREET_NAME LIKE COALESCE(NULLIF(:streetName, ''), a.STREET_NAME) " +
//            "AND a.STREET_NUMBER LIKE COALESCE(NULLIF(:streetNumber, ''), a.STREET_NUMBER) " +
//            "AND a.TYPE LIKE COALESCE(NULLIF(:type, ''), a.TYPE) " +
//            "AND a.LOCALITY LIKE COALESCE(NULLIF(:locality, ''), a.LOCALITY) " +
//            "AND a.ZIP_CODE LIKE COALESCE(NULLIF(:zipCode, ''), a.ZIP_CODE) " +
//            "AND a.AREA LIKE COALESCE(NULLIF(:area, ''), a.AREA) " +
//            "AND a.COUNTRY_CODE LIKE COALESCE(NULLIF(:countryCode, ''), a.COUNTRY_CODE) " +
//            "AND a.STATUS LIKE COALESCE(NULLIF(:status, ''), a.STATUS)",
//            nativeQuery = true)
//    List<PostalAddressEntity> findByCriteria(
//            @Param(value = "streetName") String streetName,
//            @Param(value = "streetNumber") String streetNumber,
//            @Param(value = "type") String type,
//            @Param(value = "status") String status,
//            @Param(value = "locality") String locality,
//            @Param(value = "area") String area,
//            @Param(value = "zipCode") String zipCode,
//            @Param(value = "countryCode") String countryCode);

    /**
     * Find postal addresses given predicates.
     * @param specification Specification of the predicates.
     * @return List of matching postal addresses.
     */
    List<ServerPostalAddressEntity> findAll(final Specification<ServerPostalAddressEntity> specification);
}
