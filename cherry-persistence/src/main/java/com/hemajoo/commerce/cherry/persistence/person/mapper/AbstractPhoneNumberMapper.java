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

import com.hemajoo.commerce.cherry.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.model.person.entity.ClientPhoneNumberEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EntityException;
import com.hemajoo.commerce.cherry.persistence.base.entity.AbstractBaseEntityMapper;
import com.hemajoo.commerce.cherry.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPhoneNumberEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.persistence.EntityManager;

/**
 * Mapper interface to convert between instances of client and server phone numbers.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Mapper(uses = { AbstractBaseEntityMapper.class })
public abstract class AbstractPhoneNumberMapper
{
    /**
     * Instance to this bean mapper.
     */
    public static final AbstractPhoneNumberMapper INSTANCE = Mappers.getMapper(AbstractPhoneNumberMapper.class);

    /**
     * Maps from a server phone number entity to an entity identity.
     * @param entity Server phone number entity.
     * @param context Context object.
     * @return Entity identity.
     */
    public abstract EntityIdentity fromServerToIdentity(ServerPhoneNumberEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a server phone number entity to a client phone number entity.
     * @param entity Server phone number entity.
     * @param context Context object.
     * @param entityManager Entity manager.
     * @return Client phone number entity.
     * @throws EntityException Thrown to indicate an error occurred while retrieving the server entity from the underlying database.
     */
    public abstract ServerPhoneNumberEntity fromClientToServer(ClientPhoneNumberEntity entity, @Context CycleAvoidingMappingContext context, @Context EntityManager entityManager) throws EntityException;

    /**
     * Maps from a server phone number entity to a client phone number entity.
     * @param entity Server phone number entity.
     * @param context Context object.
     * @return Client phone number entity.
     */
    public abstract ClientPhoneNumberEntity fromServerToClient(ServerPhoneNumberEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a server phone number entity.
     * @param entity Server phone number entity.
     * @param context Context object.
     * @return Copy of the server phone number entity.
     */
    public abstract ServerPhoneNumberEntity copy(ServerPhoneNumberEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a client phone number entity.
     * @param entity Client phone number entity.
     * @param context Context object.
     * @return Copy of the client phone number entity.
     */
    public abstract ClientPhoneNumberEntity copy(ClientPhoneNumberEntity entity, @Context CycleAvoidingMappingContext context);
}
