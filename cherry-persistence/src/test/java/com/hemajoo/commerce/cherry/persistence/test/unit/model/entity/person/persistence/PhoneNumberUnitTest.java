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
import com.hemajoo.commerce.cherry.persistence.base.entity.ServiceFactoryPerson;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPhoneNumberEntity;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.PersonRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.PhoneNumberRandomizer;
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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the phone number server class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
@DirtiesContext
@Testcontainers // Not to be used to keep container alive after the tests!
@SpringBootTest
class PhoneNumberUnitTest extends AbstractPostgresUnitTest
{
    /**
     * Person services.
     */
    @Autowired
    private ServiceFactoryPerson servicePerson;

    @Test
    @DisplayName("Create a phone number")
    void testCreatePhoneNumber()
    {
        ServerPersonEntity person = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        ServerPhoneNumberEntity phoneNumber = PhoneNumberRandomizer.generateServerEntity(false);
        phoneNumber.setPerson(person);

        LOGGER.info(String.format("Saving phone number: %s", phoneNumber.getIdentity()));
        phoneNumber = servicePerson.getPhoneNumberService().save(phoneNumber);

        assertThat(phoneNumber)
                .as("Phone number should not be null!")
                .isNotNull();

        assertThat(phoneNumber.getId())
                .as("Phone number identifier should not be null!")
                .isNotNull();
    }

    @Test
    @DisplayName("Create a phone number with one document")
    void testCreatePhoneNumberWithOneDocument() throws DocumentContentException
    {
        ServerDocumentEntity document = DocumentRandomizer.generateServerEntity(false);
        ServerPersonEntity person = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        ServerPhoneNumberEntity phoneNumber = PhoneNumberRandomizer.generateServerEntity(false);
        phoneNumber.addDocument(document);
        person.addPhoneNumber(phoneNumber);

        LOGGER.info(String.format("Saving phone number: %s", phoneNumber.getIdentity()));
        phoneNumber = servicePerson.getPhoneNumberService().save(phoneNumber);

        assertThat(phoneNumber)
                .as("Phone number should not be null!")
                .isNotNull();

        assertThat(phoneNumber.getId())
                .as("Phone number identifier should not be null!")
                .isNotNull();

        assertThat(phoneNumber.getDocuments().size())
                .as("Phone number document list should not be empty!")
                .isNotZero();
    }

    @Test
    @DisplayName("Create a phone number with several documents")
    void testCreatePhoneNumberWithSeveralDocument() throws DocumentContentException
    {
        List<ServerDocumentEntity> documents = new ArrayList<>();
        for (int i = 0; i < 5; i++)
        {
            documents.add(DocumentRandomizer.generateServerEntity(false));
        }

        ServerPersonEntity person = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        ServerPhoneNumberEntity phoneNumber = PhoneNumberRandomizer.generateServerEntity(false);
        documents.forEach(phoneNumber::addDocument);
        person.addPhoneNumber(phoneNumber);

        LOGGER.info(String.format("Saving phone number: %s", phoneNumber.getIdentity()));
        phoneNumber = servicePerson.getPhoneNumberService().save(phoneNumber);

        assertThat(phoneNumber)
                .as("Phone number should not be null!")
                .isNotNull();

        assertThat(phoneNumber.getId())
                .as("Phone number identifier should not be null!")
                .isNotNull();

        assertThat(phoneNumber.getDocuments().size())
                .as("Phone number document list should not be empty!")
                .isEqualTo(5);
    }

    @Test
    @DisplayName("Update a phone number")
    void testUpdatePhoneNumber()
    {
        ServerPersonEntity person = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        ServerPhoneNumberEntity phoneNumber = PhoneNumberRandomizer.generateServerEntity(false);
        phoneNumber.setPerson(person);
        phoneNumber = servicePerson.getPhoneNumberService().save(phoneNumber);

        assertThat(phoneNumber)
                .as("Phone number should not be null!")
                .isNotNull();

        assertThat(phoneNumber.getId())
                .as("Phone number identifier should not be null!")
                .isNotNull();

        String description = phoneNumber.getDescription();
        phoneNumber.setDescription("Test description for phone number: " + phoneNumber.getId());
        phoneNumber = servicePerson.getPhoneNumberService().saveAndFlush(phoneNumber);

        LOGGER.info(String.format("Updating phone number: %s", phoneNumber.getIdentity()));
        ServerPhoneNumberEntity updated = servicePerson.getPhoneNumberService().findById(phoneNumber.getId());

        assertThat(updated)
                .as("Phone number should not be null!")
                .isNotNull();

        assertThat(updated.getDescription())
                .as("Phone number description should be different!")
                .isNotEqualTo(description);
    }

    @Test
    @DisplayName("Delete a phone number")
    void testDeletePhoneNumber()
    {
        ServerPersonEntity person = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        ServerPhoneNumberEntity phoneNumber = PhoneNumberRandomizer.generateServerEntity(false);
        person.addPhoneNumber(phoneNumber);
        phoneNumber = servicePerson.getPhoneNumberService().save(phoneNumber);
//        person = entityFactory.getPersonService().save(person);

        assertThat(phoneNumber)
                .as("Phone number should not be null!")
                .isNotNull();

        assertThat(phoneNumber.getId())
                .as("Phone number should not be null!")
                .isNotNull();

        LOGGER.info(String.format("Deleting phone number: %s", phoneNumber.getIdentity()));
        servicePerson.getPhoneNumberService().deleteById(phoneNumber.getId());

        assertThat(servicePerson.getPhoneNumberService().findById(phoneNumber.getId()))
                .as("Phone number should be null!")
                .isNull();
    }
}
