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
package com.hemajoo.commerce.cherry.persistence.test.unit.model.entity.document.persistence;

import com.hemajoo.commerce.cherry.model.document.exception.DocumentContentException;
import com.hemajoo.commerce.cherry.persistence.base.factory.ServerEntityFactory;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.persistence.test.unit.base.AbstractPostgresUnitTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the document server class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@DirtiesContext
@Testcontainers // Not to be used to keep container alive after the tests!
@SpringBootTest
@Log4j2
class DocumentUnitTest extends AbstractPostgresUnitTest
{
    /**
     * Entity factory.
     */
    @Autowired
    private ServerEntityFactory entityFactory;

    @Test
    @DisplayName("Create a document")
    void testCreateDocument() throws DocumentContentException
    {
        ServerDocumentEntity document = entityFactory.getServices().getDocumentService().save(DocumentRandomizer.generateServerEntity(false));

        assertThat(document)
                .as("Document should not be null!")
                .isNotNull();

        assertThat(document.getId())
                .as("Document identifier should not be null!")
                .isNotNull();
    }

    @Test
    @DisplayName("Create a document being parent of another document")
    void testCreateDocumentOwningDocument() throws DocumentContentException
    {
        ServerDocumentEntity parent = entityFactory.getServices().getDocumentService().save(DocumentRandomizer.generateServerEntity(false));
        ServerDocumentEntity child = entityFactory.getServices().getDocumentService().save(DocumentRandomizer.generateServerEntity(false));

        assertThat(parent)
                .as("Parent document should not be null!")
                .isNotNull();
        assertThat(parent.getId())
                .as("Parent document identifier should not be null!")
                .isNotNull();
        assertThat(child)
                .as("Child document should not be null!")
                .isNotNull();
        assertThat(child.getId())
                .as("Child document identifier should not be null!")
                .isNotNull();

        parent.addDocument(child);
        parent = entityFactory.getServices().getDocumentService().save(parent);

        assertThat(parent.getDocuments().size())
                .as("Parent document list should not be empty!")
                .isNotZero();
    }

    @Test
    @DisplayName("Update a document")
    void testUpdateDocument() throws DocumentContentException
    {
        ServerDocumentEntity document = entityFactory.getServices().getDocumentService().save(DocumentRandomizer.generateServerEntity(false));

        assertThat(document)
                .as("Document should not be null!")
                .isNotNull();

        assertThat(document.getId())
                .as("Document identifier should not be null!")
                .isNotNull();

        String description = document.getDescription();
        document.setDescription("Test description for document: " + document.getId());
        entityFactory.getServices().getDocumentService().saveAndFlush(document);

        ServerDocumentEntity updated = entityFactory.getServices().getDocumentService().findById(document.getId());

        assertThat(updated)
                .as("Document should not be null!")
                .isNotNull();

        assertThat(updated.getDescription())
                .as("Documents description should be different!")
                .isNotEqualTo(description);
    }

    @Test
    @DisplayName("Delete a document")
    void testDeleteDocument() throws DocumentContentException
    {
        ServerDocumentEntity document = entityFactory.getServices().getDocumentService().save(DocumentRandomizer.generateServerEntity(false));

        assertThat(document)
                .as("Document should not be null!")
                .isNotNull();

        assertThat(document.getId())
                .as("Document identifier should not be null!")
                .isNotNull();

        entityFactory.getServices().getDocumentService().deleteById(document.getId());

        assertThat(entityFactory.getServices().getDocumentService().findById(document.getId()))
                .as("Document should be null!")
                .isNull();
    }
}
