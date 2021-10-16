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
package com.hemajoo.commerce.cherry.persistence.person.mapper;

import com.hemajoo.commerce.cherry.model.person.entity.Person;
import com.hemajoo.commerce.cherry.model.person.search.PersonSearch;
import com.hemajoo.commerce.cherry.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.persistence.document.mapper.DocumentMapper;
import com.hemajoo.commerce.cherry.persistence.person.entity.PersonServerEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

/**
 * A mapper interface providing services to map between {@link Person} and {@link PersonServerEntity} and vice-versa.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = DocumentMapper.class)
public interface PersonMapper
{
    /**
     * Instance to the bean mapper.
     */
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    /**
     * Maps from a persistent entity to a client entity.
     * @param persistent Persistent entity to map.
     * @param context Context object.
     * @return Mapped client entity.
     */
    Person mapPersistence(PersonServerEntity persistent, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a list of persistent entities to a list of client entities.
     * @param list List of persistent entities to map.
     * @param context Context object.
     * @return Mapped list of client entities.
     */
    List<Person> mapPersistenceList(List<PersonServerEntity> list, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a client entity to a persistent entity.
     * @param entity Client entity to map.
     * @param context Context object.
     * @return Mapped persistent entity.
     */
    PersonServerEntity mapClient(Person entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps a list of client entities to a list of persistent entities.
     * @param list List of client entities to map.
     * @param context Context object.
     * @return Mapped list of persistent entities.
     */
    List<PersonServerEntity> mapClientList(List<Person> list, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a persistent entity.
     * @param persistent Source persistent entity to copy.
     * @param context Context object.
     * @return Copied persistent entity.
     */
    PersonServerEntity copy(PersonServerEntity persistent, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a client entity.
     * @param entity Source client entity to copy.
     * @param context Context object.
     * @return Copied client entity.
     */
    Person copy(Person entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a persistent entity to a search entity.
     * @param entity Source entity to convert.
     * @param context Context object.
     * @return Person (search).
     */
    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
    PersonSearch toSearch(PersonServerEntity entity, @Context CycleAvoidingMappingContext context);

    @Named("stringToUuid")
    default UUID stringToUuid(String uuid)
    {
        return UUID.fromString(uuid);
    }

    @Named("uuidToString")
    default String UuidToString(UUID uuid)
    {
        return uuid.toString();
    }
}
