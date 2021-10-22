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

import com.hemajoo.commerce.cherry.commons.entity.Identity;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.model.person.entity.ClientEmailAddressEntity;
import com.hemajoo.commerce.cherry.persistence.base.factory.ServerEntityFactory;
import com.hemajoo.commerce.cherry.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.persistence.document.mapper.DocumentMapper;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPerson;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * A mapper interface providing services to map between client email address and server email address entities and vice-versa.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = DocumentMapper.class)
public interface EmailAddressMapper
{
    /**
     * Instance to the bean mapper.
     */
    EmailAddressMapper INSTANCE = Mappers.getMapper(EmailAddressMapper.class);

    /**
     * Maps a client entity to a persistent entity.
     * @param entity Client entity to map.
     * @param context Context object.
     * @return Mapped persistent entity.
     */
    //@Mapping(source = "entity.person", target = "person", qualifiedByName = "fromIdentity")
    ServerEmailAddressEntity mapClient(ClientEmailAddressEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps a list of client entities to a list of persistent entities.
     * @param list List of client entities to maps.
     * @param context Context object.
     * @return Mapped list of persistent entities.
     */
    List<ServerEmailAddressEntity> mapClientList(List<ClientEmailAddressEntity> list, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a persistent entity to a client entity.
     * @param entity Persistent entity to map.
     * @param context Context object.
     * @return Mapped client entity.
     */
    //@Mapping(source = "entity.person", target = "person", qualifiedByName = "toIdentity")
    ClientEmailAddressEntity mapServer(ServerEmailAddressEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a list of persistent entities to a list of client entities.
     * @param list List of persistent entities to map.
     * @param context Context object.
     * @return Mapped list of client entities.
     */
    List<ClientEmailAddressEntity> mapServerList(List<ServerEmailAddressEntity> list, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a persistent entity.
     * @param entity Source persistent entity to copy.
     * @param context Context object.
     * @return Copied persistent entity.
     */
    ServerEmailAddressEntity copy(ServerEmailAddressEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a client entity.
     * @param entity Source client entity to copy.
     * @param context Context object.
     * @return Copied client entity.
     */
    ClientEmailAddressEntity copy(ClientEmailAddressEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Converts a server person to an entity identity.
     * @param person Server email address entity.
     * @return Entity identity.
     */
    @Named("toIdentity")
    default Identity toIdentity(final ServerPerson person)
    {
        return person != null ? person.getIdentity() : null;
    }

    /**
     * Converts an entity identity to a server person entity.
     * @param identity Entity identity.
     * @return Server entity.
     * @throws DocumentException Thrown in case an error occurred with a document.
     */
    @Named("fromIdentity")
    default ServerPerson fromIdentity(final Identity identity) throws DocumentException
    {
        return (ServerPerson) new ServerEntityFactory().from(identity);
    }
}
