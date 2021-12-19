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
package com.hemajoo.commerce.cherry.persistence.test.unit.model.entity.document.converter;

import com.hemajoo.commerce.cherry.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.model.document.ClientDocumentEntity;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentContentException;
import com.hemajoo.commerce.cherry.model.person.exception.EntityException;
import com.hemajoo.commerce.cherry.persistence.base.entity.EntityComparator;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServiceFactoryPerson;
import com.hemajoo.commerce.cherry.persistence.document.converter.DocumentConverter;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.persistence.test.unit.base.AbstractPostgresUnitTest;
import org.javers.core.diff.Diff;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for the document converter class.
 * <hr>
 * Unit tests are supposed to be executed with the Maven 'db-test' profile activated.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Testcontainers // Not to be used to keep container alive after the tests!
@DirtiesContext
@SpringBootTest
class DocumentConverterUnitTest extends AbstractPostgresUnitTest
{
    /**
     * Document converter facility service.
     */
    @Autowired
    private DocumentConverter converterDocument;

    /**
     * Person services.
     */
    @Autowired
    private ServiceFactoryPerson servicePerson;


    /**
     * List element count.
     */
    private final int LIST_COUNT = 10;

    @Test
    @DisplayName("Convert a server document to an identity")
    final void testConvertServerToIdentityDocument() throws DocumentContentException
    {
        ServerDocumentEntity document = DocumentRandomizer.generateServerEntity(true);
        EntityIdentity identity = converterDocument.fromServerToIdentity(document);

        assertThat(identity)
                .as("Entity identity should not be null!")
                .isNotNull();

        assertThat(identity.getId())
                .as("Identifiers are expected to be the same!")
                .isEqualTo(document.getId());

        assertThat(identity.getEntityType())
                .as("Identity is supposed to be of type: DOCUMENT")
                .isEqualTo(EntityType.DOCUMENT);
    }

    @Test
    @DisplayName("Convert an identity to a server document")
    final void testConvertIdentityToServerDocument() throws DocumentContentException, EntityException
    {
        // For an entity identity to be mapped to a server entity, the server entity must exist in the underlying database!
        ServerDocumentEntity reference = servicePerson.getDocumentService().save(DocumentRandomizer.generateServerEntity(true));

        EntityIdentity identity = new EntityIdentity(reference.getIdentity());
        ServerDocumentEntity server = converterDocument.fromIdentityToServer(identity);

        assertThat(server)
                .as("Server entity should not be null!")
                .isNotNull();

        assertThat(server.getId())
                .as("Identifiers are expected to be the same!")
                .isEqualTo(identity.getId());

        assertThat(server.getEntityType())
                .as("Identity is supposed to be of type: DOCUMENT")
                .isEqualTo(EntityType.DOCUMENT);
    }

    @Test
    @DisplayName("Convert an identity to a server document (that does not exist)")
    @SuppressWarnings("java:S5977")
    final void testConvertIdentityToServerDocumentNotExisting()
    {
        EntityIdentity identity = new EntityIdentity(UUID.randomUUID(), EntityType.DOCUMENT);

        assertThatThrownBy(() -> converterDocument.fromIdentityToServer(identity))
                .isInstanceOf(EntityException.class);
    }

    @Test
    @DisplayName("Convert a client document with a owner to a server document")
    final void testConvertClientToServerDocumentWithOwner() throws DocumentContentException
    {
        // For an entity identity to be mapped to a server entity, the server entity must exist in the underlying database!
        ServerDocumentEntity owner = servicePerson.getDocumentService().save(DocumentRandomizer.generateServerEntity(false));

        ClientDocumentEntity client = DocumentRandomizer.generateClientEntity(true);
        client.setOwner(owner.getIdentity());

        ServerDocumentEntity server = converterDocument.fromClientToServer(client);

        assertThat(server)
                .as("Server document should not be null!")
                .isNotNull();

        assertThat(server.getOwner())
                .as("Server document owner should not be null!")
                .isNotNull();
    }

    @Test
    @DisplayName("Convert a client document with a non existing owner to a server document. Should raise an exception!")
    @SuppressWarnings("java:S5977")
    final void testConvertClientToServerDocumentWithNonExistingOwner() throws DocumentContentException
    {
        ClientDocumentEntity client = DocumentRandomizer.generateClientEntity(true);
        client.setOwner(new EntityIdentity(EntityType.PERSON,UUID.randomUUID()));

        // If the owner of the client document to convert does not exist, ensure an exception is raised!
        assertThatThrownBy(() -> converterDocument.fromClientToServer(client))
                .isInstanceOf(EntityException.class);
    }

    @Test
    @DisplayName("Convert a list of server documents to a list of client documents")
    final void testConvertServerToClientDocumentList() throws DocumentContentException
    {
        List<ServerDocumentEntity> documents = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            documents.add(DocumentRandomizer.generateServerEntity(true));
        }

        List<ClientDocumentEntity> clients = documents.stream()
                .map(document -> converterDocument.fromServerToClient(document))
                .collect(Collectors.toList());

        assertThat(clients.size())
                .as("Document server and client list should have the same size!")
                .isEqualTo(documents.size());

        clients.forEach(client -> assertThat(client)
                .as("Client document should not be null!")
                .isNotNull());
    }

    @Test
    @DisplayName("Convert a list of client documents to a list of server documents")
    final void testConvertClientToServerDocumentList() throws DocumentContentException
    {
        List<ClientDocumentEntity> clients = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            clients.add(DocumentRandomizer.generateClientEntity(true));
        }

        List<ServerDocumentEntity> servers = clients.stream()
                .map(document -> converterDocument.fromClientToServer(document))
                .collect(Collectors.toList());

        assertThat(servers.size())
                .as("Both lists should have the same size!")
                .isEqualTo(clients.size());

        servers.forEach(document -> assertThat(document)
                .as("Server document should not be null!")
                .isNotNull());
    }

    @Test
    @DisplayName("Convert a list of server documents to a list of entity identities")
    final void testConvertServerDocumentToIdentityList() throws DocumentContentException
    {
        List<ServerDocumentEntity> documents = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            documents.add(DocumentRandomizer.generateServerEntity(true));
        }

        List<EntityIdentity> identities = documents.stream()
                .map(document -> converterDocument.fromServerToIdentity(document))
                .collect(Collectors.toList());

        assertThat(identities.size())
                .as("Both lists should have the same size!")
                .isEqualTo(documents.size());

        identities.forEach(identity -> assertThat(identity)
                .as("Identity should not be null!")
                .isNotNull());
    }

    @Test
    @DisplayName("Copy a server document")
    final void testCopyServerDocument() throws DocumentContentException
    {
        ServerDocumentEntity document = DocumentRandomizer.generateServerEntity(true);
        ServerDocumentEntity copy = DocumentConverter.copy(document);

        assertThat(document)
                .as("Both server documents should be equal!")
                .isEqualTo(copy);

        Diff diff = EntityComparator.getJavers().compare(document, copy);
        assertThat(diff.getChanges().size())
                .as("Both server documents should be equal!")
                .isZero();
    }

    @Test
    @DisplayName("Copy a server document")
    final void testCopyClientDocument() throws DocumentContentException
    {
        ClientDocumentEntity document = DocumentRandomizer.generateClientEntity(true);
        ClientDocumentEntity copy = DocumentConverter.copy(document);

        assertThat(document)
                .as("Both client documents should be equal!")
                .isEqualTo(copy);

        Diff diff = EntityComparator.getJavers().compare(document, copy);
        assertThat(diff.getChanges().size())
                .as("Both client documents should be equal!")
                .isZero();
    }
}
