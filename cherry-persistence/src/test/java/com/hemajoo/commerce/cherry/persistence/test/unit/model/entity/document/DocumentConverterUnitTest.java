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
package com.hemajoo.commerce.cherry.persistence.test.unit.model.entity.document;

import com.hemajoo.commerce.cherry.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.model.document.ClientDocumentEntity;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentContentException;
import com.hemajoo.commerce.cherry.model.person.exception.EntityException;
import com.hemajoo.commerce.cherry.persistence.base.entity.EntityComparator;
import com.hemajoo.commerce.cherry.persistence.document.converter.DocumentConverter;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.persistence.document.repository.DocumentService;
import com.hemajoo.commerce.cherry.persistence.test.SpringTestApplication;
import com.hemajoo.commerce.cherry.persistence.test.unit.base.AbstractBaseMapperTest;
import org.javers.core.diff.Diff;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for the document converter class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Allow to define BeforeAll as non-static.
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringTestApplication.class)
class DocumentConverterUnitTest extends AbstractBaseMapperTest
{
    /**
     * Document converter facility service.
     */
    @Autowired
    private DocumentConverter converter;

    /**
     * Document persistence service.
     */
    @Autowired
    private DocumentService documentService;

    /**
     * List element count.
     */
    private final int LIST_COUNT = 10;

    @Transactional
    @BeforeAll
    public void clearAllDocuments()
    {
        // Clean all documents that would exist in the underlying database.
        documentService.getRepository().deleteAll();
    }

    @Test
    @DisplayName("Test to convert a server document to a document identity")
    final void testConvertServerDocumentToIdentity() throws DocumentContentException
    {
        ServerDocumentEntity document = DocumentRandomizer.generatePersistent(true);
        EntityIdentity identity = converter.fromServerToIdentity(document);

        assertThat(identity)
                .as("Entity identity should not be null!")
                .isNotNull();
    }

    @Test
    @DisplayName("Test to convert a document identity to a server document")
    final void testConvertIdentityToServerDocument() throws DocumentContentException, EntityException
    {
        ServerDocumentEntity reference = documentService.save(DocumentRandomizer.generatePersistent(true));

        EntityIdentity identity = new EntityIdentity(reference.getIdentity());
        ServerDocumentEntity server = converter.fromIdentityToServer(identity);

        assertThat(server)
                .as("Server document should not be null!")
                .isNotNull();
    }

    @Test
    @DisplayName("Test to convert a document identity to a server document that does not exist")
    final void testConvertIdentityToServerDocumentNotExisting()
    {
        EntityIdentity identity = new EntityIdentity(UUID.fromString("a787b9b9-9098-4903-8d60-14d19a0b0aa4"), EntityType.DOCUMENT);

        assertThatThrownBy(() -> converter.fromIdentityToServer(identity))
                .isInstanceOf(EntityException.class);
    }

    @Test
    @DisplayName("Test to convert a client document with a owner to a server document")
    final void testConvertClientToServerDocumentWithOwner() throws DocumentContentException
    {
        ServerDocumentEntity owner = documentService.save(DocumentRandomizer.generatePersistent(false));

        ClientDocumentEntity client = DocumentRandomizer.generateClient(true);
        client.setOwner(new EntityIdentity(EntityType.PERSON, owner.getId()));
        converter.fromClientToServer(client);

        assertThat(client)
                .as("Server document should not be null!")
                .isNotNull();
    }

    @SuppressWarnings("java:S5977")
    @Test
    @DisplayName("Test to convert a client document with a non existing owner to a server document")
    final void testConvertClientToServerDocumentWithNonExistentOwner() throws DocumentContentException
    {
        ClientDocumentEntity client = DocumentRandomizer.generateClient(true);
        client.setOwner(new EntityIdentity(EntityType.PERSON,UUID.randomUUID()));

        // If the owner of the client document to convert does not exist, ensure an exception is thrown!
        assertThatThrownBy(() -> converter.fromClientToServer(client))
                .isInstanceOf(RuntimeException.class)
                .hasCauseExactlyInstanceOf(EntityException.class);
    }

    @Test
    @DisplayName("Test to convert a list of server documents to a list of client documents")
    final void testConvertListServerToClientDocument() throws DocumentContentException
    {
        List<ServerDocumentEntity> documents = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            documents.add(DocumentRandomizer.generatePersistent(true));
        }

        List<ClientDocumentEntity> clients = documents.stream()
                .map(document -> converter.fromServerToClient(document))
                .collect(Collectors.toList());

        assertThat(clients.size())
                .as("Document server and client list should have the same size!")
                .isEqualTo(documents.size());

        clients.forEach(client -> assertThat(client)
                .as("Client document should not be null!")
                .isNotNull());
    }

    @Test
    @DisplayName("Test to convert a list of client documents to a list of server documents")
    final void testConvertListClientToServerDocument() throws DocumentContentException
    {
        List<ServerDocumentEntity> documents = new ArrayList<>();

        // Create some random server documents.
        for (int i = 0; i < LIST_COUNT; i++)
        {
            documents.add(documentService.save(DocumentRandomizer.generatePersistent(false)));
        }

        // Convert from server to client document list.
        List<ClientDocumentEntity> clients = documents.stream()
                .map(document -> converter.fromServerToClient(document))
                .collect(Collectors.toList());

        assertThat(clients.size())
                .as("Document server and client list should have the same size!")
                .isEqualTo(documents.size());

        clients.forEach(clientDocument -> assertThat(clientDocument)
                .as("Client document should not be null!")
                .isNotNull());

        // Convert back from client to server document list.
        List<ServerDocumentEntity> servers = clients.stream()
                .map(document -> converter.fromClientToServer(document))
                .collect(Collectors.toList());

        assertThat(clients.size())
                .as("Document server and client list should have the same size!")
                .isEqualTo(servers.size());

        servers.forEach(serverDocument -> assertThat(serverDocument)
                .as("Server document should not be null!")
                .isNotNull());
    }

    @Test
    @DisplayName("Test to convert a list of server document to a list of entity identities")
    final void testConvertListServerDocumentToIdentity() throws DocumentContentException, EntityException
    {
        List<ServerDocumentEntity> documents = new ArrayList<>();

        // Create some random server documents.
        for (int i = 0; i < LIST_COUNT; i++)
        {
            documents.add(documentService.save(DocumentRandomizer.generatePersistent(false)));
        }

        // Convert from server to identity list.
        List<EntityIdentity> identities = documents.stream()
                .map(document -> converter.fromServerToIdentity(document))
                .collect(Collectors.toList());

        assertThat(identities.size())
                .as("Document server and identity list should have the same size!")
                .isEqualTo(documents.size());

        identities.forEach(identity -> assertThat(identity)
                .as("Identity should not be null!")
                .isNotNull());

        // Convert back from identity to server document list.
        List<ServerDocumentEntity> servers = new ArrayList<>();
        for (EntityIdentity document : identities)
        {
            ServerDocumentEntity serverDocumentEntity = converter.fromIdentityToServer(document);
            servers.add(serverDocumentEntity);
        }

        assertThat(servers.size())
                .as("Document server and identity list should have the same size!")
                .isEqualTo(identities.size());

        servers.forEach(serverDocument -> assertThat(serverDocument)
                .as("Server document should not be null!")
                .isNotNull());
    }

    @Test
    @DisplayName("Test a copy and the original server documents are equal")
    final void testCopyServerDocumentEquality() throws DocumentContentException
    {
        ServerDocumentEntity document = DocumentRandomizer.generatePersistent(true);
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
    @DisplayName("Test a copy and the original client documents are equal")
    final void testCopyClientDocumentEquality() throws DocumentContentException
    {
        ClientDocumentEntity document = DocumentRandomizer.generateClient(true);
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
