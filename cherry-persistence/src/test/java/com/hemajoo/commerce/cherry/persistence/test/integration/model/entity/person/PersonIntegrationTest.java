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
package com.hemajoo.commerce.cherry.persistence.test.integration.model.entity.person;

import com.hemajoo.commerce.cherry.model.document.exception.DocumentContentException;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.persistence.base.test.AbstractBaseDatabaseUnitTest;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.PersonRandomizer;
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

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the {@link ServerPersonEntity} persistent entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@SpringBootTest(classes = SpringCherryForIntegrationTest.class)
//@ActiveProfiles("prod") // Will search for: application-<profile>.properties
@ExtendWith(SpringExtension.class)
@Import(value = { PersistenceConfigurationForIntegrationTest.class })
@Commit // Change default behavior for Spring Test which is normally to rollback transaction at the end of the test!
@DisplayName("Integration tests for person entity")
class PersonIntegrationTest extends AbstractBaseDatabaseUnitTest
{
    @Test
    @DisplayName("Create a persistent person (without document)")
    final void testCreatePersonWithoutDocument() throws DocumentException
    {
        // Generate random person.
        ServerPersonEntity person = PersonRandomizer.generateServer(false);
        personService.save(person);

        // Load the persisted person using its id.
        ServerPersonEntity persistent = personService.findById(person.getId());
        assertThat(person)
                .as("Person must not be null!")
                .isNotNull();
    }

    @Test
    @DisplayName("Create a persistent person (with a document)")
    final void testCreatePersonWithDocument() throws DocumentException, DocumentContentException
    {
        // Generate random person and document.
        ServerPersonEntity person = PersonRandomizer.generateServer(false);
        ServerDocumentEntity document = DocumentRandomizer.generatePersistent(false);
        person.addDocument(document);

        // Save the entities on database.
        personService.save(person);

        // Load the saved person.
        ServerPersonEntity persistent = personService.findById(person.getId());

        assertThat(person)
                .as("Person must not be null!")
                .isNotNull();

        assertThat(person.getDocuments().size())
                .as(String.format("Person with id: %s should have at least one document!", person.getId()))
                .isPositive();
    }

    @Transactional
    @Test
    @DisplayName("Update a persistent person")
    final void testUpdatePerson() throws DocumentException
    {
        String firstname = "Victor";
        String lastname = "Hugo";

        // Generate random person.
        ServerPersonEntity person = PersonRandomizer.generateServer(false);
        personService.save(person);

        // Load the saved person and change its names.
        person = personService.findById(person.getId());
        person.setFirstName(firstname);
        person.setLastName(lastname);
        personService.save(person);

        ServerPersonEntity persistent = personService.findById(person.getId());

        assertThat(person)
                .as("Person must not be null!")
                .isNotNull();

        assertThat(person.getFirstName())
                .as(String.format("Person with id: %s should have firstname set to: %s!", person.getId(), firstname))
                .isEqualTo(firstname);
        assertThat(person.getLastName())
                .as(String.format("Person with id: %s should have lastname set to: %s!", person.getId(), lastname))
                .isEqualTo(lastname);
    }

    @Test
    @DisplayName("Delete a persistent person")
    final void testDeletePerson() throws DocumentException
    {
        // Generate a random person.
        ServerPersonEntity person = PersonRandomizer.generateServer(false);
        personService.save(person);
        UUID id = person.getId();

        assertThat(person.getId())
                .as("Person identifier must not be null!")
                .isNotNull();

        // Load the person from the database.
        personService.deleteById(person.getId());

        ServerPersonEntity other = personService.findById(id);
        assertThat(other)
                .as("Person should be null!")
                .isNull();
    }

    @Test
    @DisplayName("Document attached to a person should be automatically deleted if the person is deleted")
    final void testChildDocumentRemoval() throws DocumentException, DocumentContentException
    {
        ServerPersonEntity person = PersonRandomizer.generateServer(false);
        ServerDocumentEntity document = DocumentRandomizer.generatePersistent(false);
        person.addDocument(document);
        personService.save(person);

        UUID documentId = document.getId();
        UUID personId = person.getId();

        personService.deleteById(person.getId());
        assertThat(documentService.findById(documentId))
                .as(String.format("Document with id: %s should have been deleted as parent person with id: %s has been deleted!",
                        documentId,
                        personId))
                .isNull();
    }
}
