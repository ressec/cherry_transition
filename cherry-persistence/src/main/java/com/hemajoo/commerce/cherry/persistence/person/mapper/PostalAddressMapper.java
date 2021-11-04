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

import com.hemajoo.commerce.cherry.persistence.document.mapper.AbstractDocumentMapper;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface providing services to map between client postal address and server postal address entities and vice-versa.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        uses = AbstractDocumentMapper.class)
public interface PostalAddressMapper
{
    /**
     * Instance to this bean mapper.
     */
    PostalAddressMapper INSTANCE = Mappers.getMapper(PostalAddressMapper.class);

//    /**
//     * Maps from a persistent entity to a client entity.
//     * @param entity Source entity.
//     * @param context Context object.
//     * @return Client entity.
//     */
//    @Mapping(source = "person", target = "owner", qualifiedByName = "postalAddressToIdentity")
//    ClientPostalAddressEntity mapServer(ServerPostalAddressEntity entity, @Context CycleAvoidingMappingContext context);
//
//    /**
//     * Maps from a list of persistent entities to a list of client entities.
//     * @param list List of persistent entities.
//     * @param context Context object.
//     * @return List of client entities.
//     */
//    List<ClientPostalAddressEntity> mapServerList(List<ServerPostalAddressEntity> list, @Context CycleAvoidingMappingContext context);
//
//    /**
//     * Maps from a client entity to a persistent entity.
//     * @param entity Source entity.
//     * @param context Context object.
//     * @return Persistent entity.
//     */
//    @Mapping(source = "entity.owner", target = "person", qualifiedByName = "postalAddressFromIdentity")
//    ServerPostalAddressEntity mapClient(ClientPostalAddressEntity entity, @Context CycleAvoidingMappingContext context, @Context ServerEntityFactory factory);
//
//    /**
//     * Maps from a list of client entities to a list of persistent entities.
//     * @param list List of client entities.
//     * @param context Context object.
//     * @return List of persistent entities.
//     */
//    List<ServerPostalAddressEntity> mapClientList(List<ClientPostalAddressEntity> list, @Context CycleAvoidingMappingContext context, @Context ServerEntityFactory factory);
//
//    /**
//     * Copy a persistent entity.
//     * @param entity Source entity to copy.
//     * @param context Context object.
//     * @return Copy.
//     */
//    ServerPostalAddressEntity copy(ServerPostalAddressEntity entity, @Context CycleAvoidingMappingContext context);
//
//    /**
//     * Copy a client entity.
//     * @param entity Source entity to copy.
//     * @param context Context object.
//     * @return Copy.
//     */
//    ClientPostalAddressEntity copy(ClientPostalAddressEntity entity, @Context CycleAvoidingMappingContext context);
//
//    /**
//     * Converts a server person to an entity identity.
//     * @param person Server person entity.
//     * @return Entity identity.
//     */
//    @Named("postalAddressToIdentity")
//    default Identity toIdentity(final ServerPerson person)
//    {
//        return person != null ? person.getIdentity() : null;
//    }
//
//    /**
//     * Converts an entity identity to a server entity.
//     * @param identity Entity identity.
//     * @return Server entity.
//     * @throws EntityException Thrown in case an error occurred while trying to retrieve a server entity.
//     * @throws DocumentException Thrown in case an error occurred with a document.
//     */
//    @Named("postalAddressFromIdentity")
//    default ServerPersonEntity fromIdentity(final Identity identity) throws DocumentException, EntityException
//    {
//        return (ServerPersonEntity) new ServerEntityFactory().from(identity);
//    }
}
