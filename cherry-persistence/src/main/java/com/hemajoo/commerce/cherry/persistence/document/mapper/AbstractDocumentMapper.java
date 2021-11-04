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
package com.hemajoo.commerce.cherry.persistence.document.mapper;

import com.hemajoo.commerce.cherry.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.model.document.ClientDocumentEntity;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.persistence.base.entity.AbstractBaseEntityMapper;
import com.hemajoo.commerce.cherry.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.persistence.EntityManager;

/**
 * Mapper interface to convert between instances of client and server documents.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Mapper(uses = { AbstractBaseEntityMapper.class })
public abstract class AbstractDocumentMapper
{
    /**
     * Instance to this bean mapper.
     */
    public static final AbstractDocumentMapper INSTANCE = Mappers.getMapper(AbstractDocumentMapper.class);

    /**
     * Maps from a server document entity to an entity identity.
     * @param entity Server document entity.
     * @param context Context object.
     * @return Entity identity.
     */
    public abstract EntityIdentity fromServerToIdentity(ServerDocumentEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a server document entity to a client document entity.
     * @param document Server document entity.
     * @param context Context object.
     * @param entityManager Entity manager.
     * @return Client document entity.
     */
    public abstract ServerDocumentEntity fromClientToServer(ClientDocumentEntity document, @Context CycleAvoidingMappingContext context, @Context EntityManager entityManager);

    /**
     * Maps from a server document entity to a client document entity.
     * @param document Server document entity.
     * @param context Context object.
     * @return Client document entity.
     */
    public abstract ClientDocumentEntity fromServerToClient(ServerDocumentEntity document, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a server document entity.
     * @param entity Server document entity.
     * @param context Context object.
     * @return Copy of the server document entity.
     * @throws DocumentException Thrown if an error occurred while trying to copy the server document entity!
     */
    public abstract ServerDocumentEntity copy(ServerDocumentEntity entity, @Context CycleAvoidingMappingContext context) throws DocumentException;

    /**
     * Copy a client document entity.
     * @param entity Client document entity.
     * @param context Context object.
     * @return Copy of the client document entity.
     */
    public abstract ClientDocumentEntity copy(ClientDocumentEntity entity, @Context CycleAvoidingMappingContext context);
}
