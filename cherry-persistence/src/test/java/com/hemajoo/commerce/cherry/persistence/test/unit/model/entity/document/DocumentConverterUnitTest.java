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

import com.hemajoo.commerce.cherry.model.document.Document;
import com.hemajoo.commerce.cherry.model.document.DocumentContentException;
import com.hemajoo.commerce.cherry.model.document.DocumentException;
import com.hemajoo.commerce.cherry.persistence.document.converter.DocumentConverter;
import com.hemajoo.commerce.cherry.persistence.document.entity.DocumentServerEntity;
import com.hemajoo.commerce.cherry.persistence.document.mapper.DocumentMapper;
import com.hemajoo.commerce.cherry.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.persistence.test.unit.base.BaseMapperTest;
import lombok.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test the {@link DocumentMapper} class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
class DocumentConverterUnitTest extends BaseMapperTest
{
    /**
     * List element count.
     */
    private final int LIST_COUNT = 10;

    @Test
    @DisplayName("Map a persistent document to a client document") final void testMapPersistentDocumentToClientDocument() throws DocumentContentException
    {
        DocumentServerEntity persistent = DocumentRandomizer.generatePersistent(true);
        Document client = DocumentConverter.fromServer(persistent);
        checkFields(persistent, client);
    }

    @Test
    @DisplayName("Map a list of persistent documents to a list of client documents")
    final void testMapListPersistentDocumentToListClientDocument() throws DocumentContentException, DocumentException
    {
        List<DocumentServerEntity> persistentList = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            persistentList.add(DocumentRandomizer.generatePersistent(true));
        }

        List<Document> clientList = DocumentConverter.fromServerList(persistentList);
        checkFields(persistentList, clientList);
    }

    @Test
    @DisplayName("Map a client document to a persistent document")
    final void testMapClientDocumentToPersistentDocument() throws DocumentContentException
    {
        Document client = DocumentRandomizer.generateClient(true);
        DocumentServerEntity persistent = DocumentConverter.fromClient(client);
        checkFields(persistent, client);
    }

    @Test
    @DisplayName("Map a list of client documents to a list of persistent documents")
    final void testMapListClientDocumentToListPersistentDocument() throws DocumentContentException, DocumentException
    {
        List<Document> clientList = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            clientList.add(DocumentRandomizer.generateClient(true));
        }

        List<DocumentServerEntity> persistentList = DocumentConverter.fromClientList(clientList);
        checkFields(persistentList, clientList);
    }

    @Test
    @DisplayName("Create a deep copy of a client document")
    final void testDeepCopyClientDocument() throws DocumentContentException
    {
        Document client = DocumentRandomizer.generateClient(true);
        Document copy = DocumentConverter.copy(client);
        checkFields(client, copy);
    }

    @Test
    @DisplayName("Create a deep copy of a persistent document")
    final void testDeepCopyPersistentDocument() throws DocumentContentException, DocumentException
    {
        DocumentServerEntity persistent = DocumentRandomizer.generatePersistent(true);
        DocumentServerEntity copy = DocumentConverter.copy(persistent);
        checkFields(persistent, copy);
    }

    /**
     * Checks equality of fields between a persistent and a client entities.
     * @param persistent Persistent entity.
     * @param client Client entity.
     */
    private void checkFields(final @NonNull DocumentServerEntity persistent, final @NonNull Document client)
    {
        checkBaseFields(persistent, client);

        // Do not test equality for transient fields and for owner!
        assertThat(persistent.getFilename())
                .as("Filename should be equal!")
                .isEqualTo(client.getFilename());
        assertThat(persistent.getDocumentType())
                .as("Document type should be equal!")
                .isEqualTo(client.getDocumentType());
        assertThat(persistent.getTags())
                .as("Tags type should be equal!")
                .isEqualTo(client.getTags());
        assertThat(persistent.getExtension())
                .as("Extension type should be equal!")
                .isEqualTo(client.getExtension());
        assertThat(persistent.getContentId())
                .as("Content id type should be equal!")
                .isEqualTo(client.getContentId());
        assertThat(persistent.getContentPath())
                .as("Content path type should be equal!")
                .isEqualTo(client.getContentPath());
        assertThat(persistent.getContentLength())
                .as("Content length type should be equal!")
                .isEqualTo(client.getContentLength());
    }

    /**
     * Checks equality of fields between two client entities.
     * @param client Client entity.
     * @param copy Client entity.
     */
    private void checkFields(final @NonNull Document client, final @NonNull Document copy)
    {
        checkBaseFields(client, copy);

        // Do not test equality for transient fields and for owner!
        assertThat(client.getFilename())
                .as("Filename should be equal!")
                .isEqualTo(copy.getFilename());
        assertThat(client.getDocumentType())
                .as("Document type should be equal!")
                .isEqualTo(copy.getDocumentType());
        assertThat(client.getTags())
                .as("Tags type should be equal!")
                .isEqualTo(copy.getTags());
        assertThat(client.getExtension())
                .as("Extension type should be equal!")
                .isEqualTo(copy.getExtension());
        assertThat(client.getContentId())
                .as("Content id type should be equal!")
                .isEqualTo(copy.getContentId());
        assertThat(client.getContentPath())
                .as("Content path type should be equal!")
                .isEqualTo(copy.getContentPath());
        assertThat(client.getContentLength())
                .as("Content length type should be equal!")
                .isEqualTo(copy.getContentLength());
    }

    /**
     * Checks equality of fields between two persistent entities.
     * @param persistent Persistent entity.
     * @param copy Persistent entity.
     */
    private void checkFields(final @NonNull DocumentServerEntity persistent, final @NonNull DocumentServerEntity copy)
    {
        checkBaseFields(persistent, copy);

        // Do not test equality for transient fields and for owner!
        assertThat(persistent.getFilename())
                .as("Filename should be equal!")
                .isEqualTo(copy.getFilename());
        assertThat(persistent.getDocumentType())
                .as("Document type should be equal!")
                .isEqualTo(copy.getDocumentType());
        assertThat(persistent.getTags())
                .as("Tags type should be equal!")
                .isEqualTo(copy.getTags());
        assertThat(persistent.getExtension())
                .as("Extension type should be equal!")
                .isEqualTo(copy.getExtension());
        assertThat(persistent.getContentId())
                .as("Content id type should be equal!")
                .isEqualTo(copy.getContentId());
        assertThat(persistent.getContentPath())
                .as("Content path type should be equal!")
                .isEqualTo(copy.getContentPath());
        assertThat(persistent.getContentLength())
                .as("Content length type should be equal!")
                .isEqualTo(copy.getContentLength());
    }

    /**
     * Checks equality of fields between document entities.
     * @param persistentList List of persistent documents.
     * @param clientList List of client documents.
     * @throws DocumentException Thrown in case an error occurred finding a client document!
     */
    private void checkFields(final @NonNull List<DocumentServerEntity> persistentList, final @NonNull List<Document> clientList) throws DocumentException
    {
        Optional<Document> client;
        for (DocumentServerEntity persistent : persistentList)
        {
            client = clientList.stream().filter(element -> element.getId().equals(persistent.getId())).findFirst();
            if (client.isPresent())
            {
                checkBaseFields(persistent, client.get());
            }
            else
            {
                throw new DocumentException(String.format("Cannot find client document with id: '%s'!", persistent.getId()));
            }
        }
    }
}
