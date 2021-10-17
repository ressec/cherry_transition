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

import com.hemajoo.commerce.cherry.model.document.ClientDocumentEntity;
import com.hemajoo.commerce.cherry.model.document.base.Document;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentContentException;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.persistence.document.converter.DocumentConverter;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.mapper.DocumentMapper;
import com.hemajoo.commerce.cherry.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.persistence.test.unit.base.AbstractBaseMapperTest;
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
class DocumentConverterUnitTest extends AbstractBaseMapperTest
{
    /**
     * List element count.
     */
    private final int LIST_COUNT = 10;

    @Test
    @DisplayName("Map a persistent document to a client document") final void testMapPersistentDocumentToClientDocument() throws DocumentContentException
    {
        ServerDocumentEntity persistent = DocumentRandomizer.generatePersistent(true);
        ClientDocumentEntity client = DocumentConverter.fromServer(persistent);
        checkFields(persistent, client);
    }

    @Test
    @DisplayName("Map a list of persistent documents to a list of client documents")
    final void testMapListPersistentDocumentToListClientDocument() throws DocumentContentException, DocumentException
    {
        List<ServerDocumentEntity> persistentList = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            persistentList.add(DocumentRandomizer.generatePersistent(true));
        }

        List<ClientDocumentEntity> clientList = DocumentConverter.fromServerList(persistentList);
        checkFields(persistentList, clientList);
    }

    @Test
    @DisplayName("Map a client document to a persistent document")
    final void testMapClientDocumentToPersistentDocument() throws DocumentContentException
    {
        ClientDocumentEntity client = DocumentRandomizer.generateClient(true);
        ServerDocumentEntity persistent = DocumentConverter.fromClient(client);
        checkFields(persistent, client);
    }

    @Test
    @DisplayName("Map a list of client documents to a list of persistent documents")
    final void testMapListClientDocumentToListPersistentDocument() throws DocumentContentException, DocumentException
    {
        List<ClientDocumentEntity> clientList = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            clientList.add(DocumentRandomizer.generateClient(true));
        }

        List<ServerDocumentEntity> persistentList = DocumentConverter.fromClientList(clientList);
        checkFields(persistentList, clientList);
    }

    @Test
    @DisplayName("Create a deep copy of a client document")
    final void testDeepCopyClientDocument() throws DocumentContentException
    {
        ClientDocumentEntity client = DocumentRandomizer.generateClient(true);
        ClientDocumentEntity copy = DocumentConverter.copy(client);
        checkFields(client, copy);
    }

    @Test
    @DisplayName("Create a deep copy of a persistent document")
    final void testDeepCopyPersistentDocument() throws DocumentContentException, DocumentException
    {
        ServerDocumentEntity persistent = DocumentRandomizer.generatePersistent(true);
        ServerDocumentEntity copy = DocumentConverter.copy(persistent);
        checkFields(persistent, copy);
    }

    /**
     * Checks equality of fields between two document entities.
     * @param source Source entity.
     * @param target Target entity.
     */
    private void checkFields(final @NonNull Document source, final @NonNull Document target)
    {
        checkBaseFields(source, target);

        // Do not test equality for transient fields and for owner!
        assertThat(source.getFilename())
                .as("Filename should be equal!")
                .isEqualTo(target.getFilename());
        assertThat(source.getDocumentType())
                .as("Document type should be equal!")
                .isEqualTo(target.getDocumentType());
        assertThat(source.getTags())
                .as("Tags type should be equal!")
                .isEqualTo(target.getTags());
        assertThat(source.getExtension())
                .as("Extension type should be equal!")
                .isEqualTo(target.getExtension());
        assertThat(source.getContentId())
                .as("Content id type should be equal!")
                .isEqualTo(target.getContentId());
        assertThat(source.getContentPath())
                .as("Content path type should be equal!")
                .isEqualTo(target.getContentPath());
        assertThat(source.getContentLength())
                .as("Content length type should be equal!")
                .isEqualTo(target.getContentLength());
    }

    /**
     * Checks equality of fields between two list of document entities.
     * @param source Source list of document entities.
     * @param target Target list of document entities.
     * @throws DocumentException Thrown in case an error occurred finding a client document!
     */
    private void checkFields(final @NonNull List<? extends Document> source, final @NonNull List<? extends Document> target) throws DocumentException
    {
        Optional<Document> targetEntity;
        for (Document sourceEntity : source)
        {
            targetEntity = (Optional<Document>) target.stream().filter(element -> element.getId().equals(sourceEntity.getId())).findFirst();
            if (targetEntity.isPresent())
            {
                checkBaseFields(sourceEntity, targetEntity.get());
            }
            else
            {
                throw new DocumentException(String.format("Cannot find client document with id: '%s'!", sourceEntity.getId()));
            }
        }
    }
}
