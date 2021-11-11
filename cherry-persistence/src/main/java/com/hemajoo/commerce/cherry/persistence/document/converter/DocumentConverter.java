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
package com.hemajoo.commerce.cherry.persistence.document.converter;

import com.hemajoo.commerce.cherry.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.model.document.ClientDocumentEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EntityException;
import com.hemajoo.commerce.cherry.persistence.base.entity.AbstractBaseEntityMapper;
import com.hemajoo.commerce.cherry.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.mapper.AbstractDocumentMapper;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Converter to convert between instances of client and server documents.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Component
public class DocumentConverter
{
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Converts from a server document entity to an entity identity.
     * @param server Server document entity.
     * @return Entity identity.
     */
    public EntityIdentity fromServerToIdentity(ServerDocumentEntity server)
    {
        return AbstractDocumentMapper.INSTANCE.fromServerToIdentity(server, new CycleAvoidingMappingContext());
    }

    /**
     * Converts from an entity identity to a server document entity.
     * @param identity Entity identity.
     * @return Server document entity.
     * @throws EntityException Thrown to indicate an error occurred while retrieving the server entity from the underlying database.
     */
    public ServerDocumentEntity fromIdentityToServer(EntityIdentity identity) throws EntityException
    {
        return AbstractBaseEntityMapper.INSTANCE.map(identity,entityManager);
    }

    /**
     * Converts from a client document entity to a server document entity.
     * @param client Client document entity.
     * @return Server document entity.
     * @throws EntityException Thrown to indicate an error occurred while retrieving the server entity from the underlying database.
     */
    public ServerDocumentEntity fromClientToServer(ClientDocumentEntity client) throws EntityException
    {
        return AbstractDocumentMapper.INSTANCE.fromClientToServer(client, new CycleAvoidingMappingContext(), entityManager);
    }

    /**
     * Converts from a server document entity to a client document entity.
     * @param server Server document entity.
     * @return Client document entity.
     */
    public ClientDocumentEntity fromServerToClient(ServerDocumentEntity server)
    {
        return AbstractDocumentMapper.INSTANCE.fromServerToClient(server, new CycleAvoidingMappingContext());
    }

    /**
     * Copy a server document entity.
     * @param server Server document entity.
     * @return Copied server document entity.
     */
    public static ServerDocumentEntity copy(ServerDocumentEntity server)
    {
        return AbstractDocumentMapper.INSTANCE.copy(server, new CycleAvoidingMappingContext());
    }

    /**
     * Copy a client document entity.
     * @param client Client document entity.
     * @return Copied client document entity.
     */
    public static ClientDocumentEntity copy(ClientDocumentEntity client)
    {
        return AbstractDocumentMapper.INSTANCE.copy(client, new CycleAvoidingMappingContext());
    }
}
