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
package com.hemajoo.commerce.cherry.persistence.test.integration.model.entity.document;

import com.hemajoo.commerce.cherry.model.document.DocumentContentException;
import com.hemajoo.commerce.cherry.model.document.DocumentException;
import com.hemajoo.commerce.cherry.persistence.base.test.AbstractBaseDatabaseUnitTest;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.persistence.test.integration.SpringCherryForIntegrationTest;
import com.hemajoo.commerce.cherry.persistence.test.integration.configuration.PersistenceConfigurationForIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the {@link ServerDocumentEntity} persistent entity.
 * <p>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@SpringBootTest(classes = SpringCherryForIntegrationTest.class)
//@ActiveProfiles("test") // Will search for: application-test.properties
@ExtendWith(SpringExtension.class)
@Import(value = {PersistenceConfigurationForIntegrationTest.class})
//@Transactional
@Commit // Change default behavior for Spring Test which is normally to rollback transaction at the end of the test!
@DisplayName("Test the document model entity in database")
class DocumentIntegrationTest extends AbstractBaseDatabaseUnitTest
{
    @Test
    @DisplayName("Create a document") final void testCreateDocument() throws DocumentContentException, DocumentException
    {
        // Generate a random document.
        ServerDocumentEntity document = DocumentRandomizer.generatePersistent(false); // No id set!
        documentService.save(document);

        assertThat(document)
                .as("Document should not be null!")
                .isNotNull();

        assertThat(document.getId())
                .as("Document identifier should not be null!")
                .isNotNull();
    }

    @Test
    @DisplayName("Create a document (with specific content)") final void testCreateDocumentWithSpecificContent() throws DocumentContentException, DocumentException
    {
        String contentName = "./media/java-8-streams-cheat-sheet.pdf";
        String contentFileName = "java-8-streams-cheat-sheet.pdf";

        // Generate a random document.
        ServerDocumentEntity document = DocumentRandomizer.generatePersistent(false);
        document.setContent(contentName);

        // Save it and store the associated media file.
        documentService.save(document);

        assertThat(document)
                .as("Document must not be null!")
                .isNotNull();
        assertThat(document.getContent())
                .as("Document content must not be null!")
                .isNotNull();
        assertThat(document.getFilename())
                .as(String.format("Document content file name should be set to: %s!", contentFileName))
                .isEqualTo(contentFileName);
    }

    @Transactional
    @Test
    @DisplayName("Update a document") final void testUpdateDocument() throws DocumentContentException, DocumentException
    {
        // Generate a random document.
        ServerDocumentEntity document = DocumentRandomizer.generatePersistent(false);
        documentService.save(document);

        // Update the document 'tags'.
        document.setTags("cherry");
        documentService.save(document);

        assertThat(document.getId())
                .as("Document id should not be null!")
                .isNotNull();

        // Assert the document has been updated in the database!
        ServerDocumentEntity other = documentService.findById(document.getId());

        assertThat(other)
                .as("Updated document should not be null!")
                .isNotNull();
        assertThat(other.getTags())
                .as("Document tags should be equal to: 'cherry'")
                .isEqualTo("cherry");
    }

    @Test
    @DisplayName("Update a document content") final void testUpdateDocumentContent() throws DocumentContentException, DocumentException
    {
        // Generate a random document.
        ServerDocumentEntity document = DocumentRandomizer.generatePersistent(false);
        document.setContent("./media/java-8-streams-cheat-sheet.pdf");
        long length = document.getContentLength();
        documentService.save(document);

        // Update the document content.
        document.setContent("./media/android-10.jpg");
        documentService.save(document);

        // Assert the document has been updated in the database!
        ServerDocumentEntity other = documentService.findById(document.getId());
        assertThat(other)
                .as("Updated document should not be null!")
                .isNotNull();
        assertThat(other.getContentLength())
                .as(String.format("Document content length should be equal to: '%s'", document.getContentLength()))
                .isEqualTo(document.getContentLength());
    }

    @Test
    @DisplayName("Delete a document") final void testDeleteDocumentInDatabase() throws DocumentContentException, DocumentException
    {
        // Generate a random document.
        ServerDocumentEntity document = DocumentRandomizer.generatePersistent(false);
        document.setContent("./media/java-8-streams-cheat-sheet.pdf");
        documentService.save(document);

        assertThat(document.getId())
                .as("Document id should not be null!")
                .isNotNull();

        assertThat(document.getContent())
                .as("Document content should not be null!")
                .isNotNull();

        UUID id = document.getId();

        // Fetch the document back and delete it from the database.
        documentService.deleteById(document.getId());

        ServerDocumentEntity other = documentService.findById(id);
        assertThat(other)
                .as("Document should be null!")
                .isNull();
    }

    @Transactional
    @Test
    @DisplayName("Delete multiple documents") final void testDeleteMultipleDocuments() throws DocumentContentException, DocumentException
    {
        List<ServerDocumentEntity> documents = new ArrayList<>();
        List<String> uuids = new ArrayList<>();
        ServerDocumentEntity document;

        for (int i = 0; i < 100; i++)
        {
            document = DocumentRandomizer.generatePersistent(false);
            document.setContent("./media/java-8-streams-cheat-sheet.pdf");
            documentService.save(document);
            documents.add(document);
            uuids.add(document.getContentId());
        }

        for (ServerDocumentEntity element : documents)
        {
            documentService.deleteById(element.getId());
        }

        for (String uuid : uuids)
        {
            assertThat(documentStore.getResource(uuid).exists())
                    .as(String.format("Resource with sid: '%s' should not exist anymore!", uuid))
                    .isFalse();
        }
    }
}
