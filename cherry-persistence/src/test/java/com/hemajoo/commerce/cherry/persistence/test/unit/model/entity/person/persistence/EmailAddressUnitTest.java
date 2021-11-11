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
package com.hemajoo.commerce.cherry.persistence.test.unit.model.entity.person.persistence;

import com.hemajoo.commerce.cherry.model.document.exception.DocumentContentException;
import com.hemajoo.commerce.cherry.model.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.persistence.base.factory.ServerEntityFactory;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.EmailAddressRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.PersonRandomizer;
import com.hemajoo.commerce.cherry.persistence.test.unit.base.AbstractPostgresUnitTest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the email address server class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
@DirtiesContext
@Testcontainers // Not to be used to keep container alive after the tests!
@SpringBootTest
class EmailAddressUnitTest extends AbstractPostgresUnitTest
{
    /**
     * Entity factory.
     */
    @Autowired
    private ServerEntityFactory entityFactory;

    @Test
    @DisplayName("Create an email address")
    void testCreateEmailAddress() throws EmailAddressException
    {
        ServerPersonEntity person = entityFactory.getServices().getPersonService().save(PersonRandomizer.generateServerEntity(false));

        ServerEmailAddressEntity emailAddress = EmailAddressRandomizer.generateServerEntity(false);
        emailAddress.setPerson(person);
        emailAddress = entityFactory.getServices().getEmailAddressService().save(emailAddress);

        assertThat(emailAddress)
                .as("Email address should not be null!")
                .isNotNull();

        assertThat(emailAddress.getId())
                .as("Email address identifier should not be null!")
                .isNotNull();
    }

    @Test
    @DisplayName("Create an email address with one document")
    void testCreateEmailAddressWithOneDocument() throws EmailAddressException, DocumentContentException
    {
        ServerDocumentEntity document = DocumentRandomizer.generateServerEntity(false);
        ServerPersonEntity person = entityFactory.getServices().getPersonService().save(PersonRandomizer.generateServerEntity(false));

        ServerEmailAddressEntity emailAddress = EmailAddressRandomizer.generateServerEntity(false);
        emailAddress.addDocument(document);
        person.addEmailAddress(emailAddress);
        emailAddress = entityFactory.getServices().getEmailAddressService().save(emailAddress);

        assertThat(emailAddress)
                .as("Email address should not be null!")
                .isNotNull();

        assertThat(emailAddress.getId())
                .as("Email address identifier should not be null!")
                .isNotNull();

        assertThat(emailAddress.getDocuments().size())
                .as("Email address document list should not be empty!")
                .isNotZero();
    }

    @Test
    @DisplayName("Create an email address with several documents")
    void testCreateEmailAddressWithSeveralDocument() throws EmailAddressException, DocumentContentException
    {
        List<ServerDocumentEntity> documents = new ArrayList<>();
        for (int i = 0; i < 5; i++)
        {
            documents.add(DocumentRandomizer.generateServerEntity(false));
        }

        ServerPersonEntity person = entityFactory.getServices().getPersonService().save(PersonRandomizer.generateServerEntity(false));

        ServerEmailAddressEntity emailAddress = EmailAddressRandomizer.generateServerEntity(false);
        documents.forEach(emailAddress::addDocument);
        person.addEmailAddress(emailAddress);
        emailAddress = entityFactory.getServices().getEmailAddressService().save(emailAddress);

        assertThat(emailAddress)
                .as("Email address should not be null!")
                .isNotNull();

        assertThat(emailAddress.getId())
                .as("Email address identifier should not be null!")
                .isNotNull();

        assertThat(emailAddress.getDocuments().size())
                .as("Email address document list should not be empty!")
                .isEqualTo(5);
    }

    @Test
    @DisplayName("Update an email address")
    void testUpdateEmailAddress() throws EmailAddressException
    {
        ServerPersonEntity person = entityFactory.getServices().getPersonService().save(PersonRandomizer.generateServerEntity(false));

        ServerEmailAddressEntity emailAddress = EmailAddressRandomizer.generateServerEntity(false);
        emailAddress.setPerson(person);
        emailAddress = entityFactory.getServices().getEmailAddressService().save(emailAddress);

        assertThat(emailAddress)
                .as("Email address should not be null!")
                .isNotNull();

        assertThat(emailAddress.getId())
                .as("Email address identifier should not be null!")
                .isNotNull();

        String description = emailAddress.getDescription();
        emailAddress.setDescription("Test description for person: " + emailAddress.getId());
        emailAddress = entityFactory.getServices().getEmailAddressService().saveAndFlush(emailAddress);

        ServerEmailAddressEntity updated = entityFactory.getServices().getEmailAddressService().findById(emailAddress.getId());

        assertThat(updated)
                .as("Email address should not be null!")
                .isNotNull();

        assertThat(updated.getDescription())
                .as("Email addresses description should be different!")
                .isNotEqualTo(description);
    }

    @Test
    @DisplayName("Delete an email address")
    void testDeleteEmailAddress() throws EmailAddressException
    {
        ServerPersonEntity person = entityFactory.getServices().getPersonService().save(PersonRandomizer.generateServerEntity(false));

        ServerEmailAddressEntity emailAddress = EmailAddressRandomizer.generateServerEntity(false);
        emailAddress.setPerson(person);
        emailAddress = entityFactory.getServices().getEmailAddressService().save(emailAddress);

        assertThat(emailAddress)
                .as("Email address should not be null!")
                .isNotNull();

        assertThat(emailAddress.getId())
                .as("Email address should not be null!")
                .isNotNull();

        entityFactory.getServices().getEmailAddressService().deleteById(emailAddress.getId());

        assertThat(entityFactory.getServices().getEmailAddressService().findById(emailAddress.getId()))
                .as("Email address should be null!")
                .isNull();
    }

    @Test
    @DisplayName("Delete email address. Orphan document is to be deleted")
    final void testValidateEmailAddressDocumentOrphanRemove() throws DocumentException, DocumentContentException, EmailAddressException
    {
        ServerEmailAddressEntity email = EmailAddressRandomizer.generateServerEntity(false);
        ServerDocumentEntity document = DocumentRandomizer.generateServerEntity(false);
        ServerPersonEntity person = PersonRandomizer.generateServerEntity(false);
        email.addDocument(document);
        person.addEmailAddress(email);
        entityFactory.getServices().getPersonService().save(person);
        UUID documentId = document.getId();

        // By deleting the email address, the child document should be removed!
        person.removeEmailAddress(email);
        entityFactory.getServices().getEmailAddressService().deleteById(email.getId());
        entityFactory.getServices().getPersonService().save(person);

        assertThat(entityFactory.getServices().getEmailAddressService().findById(documentId))
                .as("Child document should have been deleted!")
                .isNull();
    }
}
