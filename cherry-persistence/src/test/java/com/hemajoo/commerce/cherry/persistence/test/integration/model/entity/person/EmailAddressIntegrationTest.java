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

import com.hemajoo.commerce.cherry.commons.type.StatusType;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentContentException;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import com.hemajoo.commerce.cherry.persistence.base.test.AbstractBaseDatabaseUnitTest;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.entity.EmailAddressServerEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.EmailAddressRandomizer;
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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the {@link EmailAddressServerEntity} persistent entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@SpringBootTest(classes = SpringCherryForIntegrationTest.class)
//@ActiveProfiles("prod") // Will search for: application-<profile>.properties
@ExtendWith(SpringExtension.class)
@Import(value = { PersistenceConfigurationForIntegrationTest.class })
//@Transactional // All test methods will be transactional
@Commit // Change default behavior for Spring Test which is normally to rollback transaction at the end of the test!
@DisplayName("Test the email address entity")
class EmailAddressIntegrationTest extends AbstractBaseDatabaseUnitTest
{
    @Test
    @DisplayName("Create an email address (without document)") final void testCreateEmailAddressWithoutDocument() throws DocumentException, EmailAddressException
    {
        // Generate random person & email address.
        ServerPersonEntity person = PersonRandomizer.generatePersistent(false);
        EmailAddressServerEntity email = EmailAddressRandomizer.generatePersistent(false);
        person.addEmailAddress(email);
        personService.save(person); // TODO Need to save the documents content in the content store!

        // Load the persisted person using its id.
        ServerPersonEntity persistent = personService.findById(person.getId());

        assertThat(persistent)
                .as("Person must not be null!")
                .isNotNull();

        assertThat(person.getEmailAddresses().size())
                .as(String.format("Person with id: %s should contain at least one email address!", person.getId()))
                .isPositive();
    }

    @Transactional
    @Test
    @DisplayName("Create an email address (with a document)") final void testCreateEmailAddressWithDocument() throws DocumentException, DocumentContentException, EmailAddressException
    {
        // Generate random entities.
        EmailAddressServerEntity email = EmailAddressRandomizer.generatePersistent(false);
        ServerDocumentEntity document = DocumentRandomizer.generatePersistent(false);
        ServerPersonEntity person = PersonRandomizer.generatePersistent(false);
        email.addDocument(document);
        person.addEmailAddress(email);

        // Save the entities on database.
        personService.save(person);

        // Load the saved person.
        ServerPersonEntity persistent = personService.findById(person.getId());

        assertThat(persistent)
                .as("Person must not be null!")
                .isNotNull();

        assertThat(persistent.getEmailAddresses().size())
                .as(String.format("Person with id: %s should contain at least one email address!", person.getId()))
                .isPositive();

        assertThat(persistent.getEmailAddresses().get(0).getDocuments().size())
                .as(String.format(
                        "Email address: %s should contain at least one document!",
                        persistent.getEmailAddresses().get(0).getEmail()))
                .isPositive();
    }

    @Transactional
    @Test
    @DisplayName("Update an email address") final void testUpdateEmailAddress() throws DocumentException, EmailAddressException
    {
        String referenceEmailAddress = "victor.hugo@gmail.com";

        // Generate random entities and save them in database.
        EmailAddressServerEntity email = EmailAddressRandomizer.generatePersistent(false);
        ServerPersonEntity person = PersonRandomizer.generatePersistent(false);
        person.addEmailAddress(email);
        personService.save(person);

        // Load the saved person and change its names.
        person = personService.findById(person.getId());
        email = person.getEmailAddresses().get(0);
        email.setEmail(referenceEmailAddress);
        email.setAddressType(AddressType.PRIVATE);
        email.setActive();
        email.setStatusType(StatusType.ACTIVE);
        personService.save(person);

        ServerPersonEntity persistent = personService.findById(person.getId());

        assertThat(persistent)
                .as("Person should not be null!")
                .isNotNull();

        assertThat(persistent.getEmailAddresses().get(0).getEmail())
                .as(String.format(
                        "Person with id: %s should have an associated email set to: %s",
                        persistent.getId(),
                        referenceEmailAddress))
                .isEqualTo(referenceEmailAddress);
    }

    @Transactional
    @Test
    @DisplayName("Delete an email address") final void testDeleteEmailAddress() throws DocumentException, EmailAddressException
    {
        // Generate a person and an email address.
        ServerPersonEntity person = PersonRandomizer.generatePersistent(false);
        EmailAddressServerEntity email = EmailAddressRandomizer.generatePersistent(false);
        person.addEmailAddress(email);
        personService.save(person);

        // Delete the email address.
        person.removeEmailAddress(email);
        personService.save(person);

        // Load the person from the database.
        ServerPersonEntity other = personService.findById(person.getId());

        assertThat(other)
                .as("Person must not be null!")
                .isNotNull();

        assertThat(other.getEmailAddresses().size())
                .as("Email address list should be empty!")
                .isZero();
    }

    @Transactional
    @Test
    @DisplayName("Orphan email address removal") final void testRemovalOrphanEmailAddress() throws DocumentException, EmailAddressException
    {
        // Generate a person and an email address.
        ServerPersonEntity person = PersonRandomizer.generatePersistent(false);
        EmailAddressServerEntity email = EmailAddressRandomizer.generatePersistent(false);
        person.addEmailAddress(email);
        personService.save(person);

        // Remove the email address and save back the person.
        person.getEmailAddresses().remove(email);

        // Commit the change to the database as we want the orphan mechanism to remove the orphan email address dynamically!
        personService.getRepository().saveAndFlush(person);

        // Check the email address has been automatically removed in the database as it was an orphan.
        EmailAddressServerEntity other = emailAddressService.findById(email.getId());

        assertThat(other)
                .as(String.format("Email address id.: %s should not exist anymore in the database!", email.getId()))
                .isNull();
    }
}
