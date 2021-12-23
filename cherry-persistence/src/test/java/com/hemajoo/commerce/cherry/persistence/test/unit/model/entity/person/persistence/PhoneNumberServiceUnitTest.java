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

import com.hemajoo.commerce.cherry.commons.time.CherryDateTimeHelper;
import com.hemajoo.commerce.cherry.commons.type.StatusType;
import com.hemajoo.commerce.cherry.model.person.exception.PhoneNumberException;
import com.hemajoo.commerce.cherry.model.person.search.SearchPhoneNumber;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberCategoryType;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberType;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServiceFactoryPerson;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPhoneNumberEntity;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.PersonRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.PhoneNumberRandomizer;
import com.hemajoo.commerce.cherry.persistence.test.unit.base.AbstractPostgresUnitTest;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the phone number server entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
@DirtiesContext
@Testcontainers // Not to be used to keep container alive after the tests!
@SpringBootTest
class PhoneNumberServiceUnitTest extends AbstractPostgresUnitTest
{
    /**
     * Person services.
     */
    @Autowired
    private ServiceFactoryPerson servicePerson;

    /**
     * Number of distinct persons.
     */
    private final static int COUNT_PERSON = 2;

    /**
     * Number of distinct phone numbers (per person).
     */
    private final static int COUNT_PHONE = 10;

    /**
     * Total number of phone numbers.
     */
    private final static int TOTAL_PHONE = COUNT_PERSON * COUNT_PHONE;

    /**
     * Collection of pre-created persons.
     */
    private final List<ServerPersonEntity> persons = new ArrayList<>();

    @BeforeEach
    public void beforeEach() throws PhoneNumberException
    {
        // Pre-create in the underlying database a set of persons with phone numbers that will be used for the tests.

        ServerPersonEntity serverPerson;
        ServerPhoneNumberEntity serverPhoneNumber;
        List<ServerPhoneNumberEntity> phones = new ArrayList<>();

        // Pre-create the 10 persons.
        for (int i = 0; i < COUNT_PERSON; i++)
        {
            serverPerson = PersonRandomizer.generateServerEntity(false);

            for (int j = 0; j < COUNT_PHONE; j++)
            {
                serverPhoneNumber = PhoneNumberRandomizer.generateServerEntity(false);
                serverPerson.addPhoneNumber(serverPhoneNumber);
            }

            servicePerson.getPersonService().save(serverPerson);
            persons.add(serverPerson);
        }
    }

    @AfterEach
    public void afterEach()
    {
        // Delete all the persons.
        servicePerson.getPersonService()
                .findAll()
                .forEach(e -> servicePerson.getPersonService().deleteById(e.getId()));
    }

    @Test
    @DisplayName("Count the number of phone numbers")
    void testServiceCount()
    {
        long count = servicePerson.getPhoneNumberService().count();
        assertThat(count)
                .as(String.format("Phone numbers entities should be equal to: '%s'", TOTAL_PHONE))
                .isEqualTo(TOTAL_PHONE);
    }

    @Test
    @DisplayName("Find all phone numbers")
    void testServiceFindAll()
    {
        List<ServerPhoneNumberEntity> phones = servicePerson.getPhoneNumberService().findAll();
        assertThat(phones.size())
                .as(String.format("Phone number list should contain: '%s' entities", TOTAL_PHONE))
                .isEqualTo(TOTAL_PHONE);
    }

    @Test
    @DisplayName("Find all phone numbers by phone type")
    void testServiceFindByPhoneType()
    {
        List<ServerPhoneNumberEntity> phones = servicePerson.getPhoneNumberService().findByPhoneType(PhoneNumberType.PRIVATE);
        assertThat(phones.size())
                .as("Phone number list should not be empty!")
                .isNotZero();
    }

    @Test
    @DisplayName("Find all phone numbers by phone type")
    void testServiceFindByCategoryType()
    {
        List<ServerPhoneNumberEntity> phones = servicePerson.getPhoneNumberService().findByCategoryType(PhoneNumberCategoryType.FIX);
        assertThat(phones.size())
                .as("Phone number list should not be empty!")
                .isNotZero();
    }

    @Test
    @DisplayName("Find all phone numbers by is default")
    void testServiceFindByIsDefault()
    {
        List<ServerPhoneNumberEntity> phones = servicePerson.getPhoneNumberService().findByIsDefault(true);
        assertThat(phones.size())
                .as("Phone number list should not be empty!")
                .isNotZero();
    }

    @Test
    @DisplayName("Find all phone numbers by status type")
    void testServiceFindByStatus()
    {
        List<ServerPhoneNumberEntity> phones = servicePerson.getPhoneNumberService().findByStatus(StatusType.ACTIVE);
        assertThat(phones.size())
                .as("Phone number list should not be empty!")
                .isNotZero();
    }

    @Test
    @DisplayName("Find all phone numbers by person identifier")
    void testServiceFindByPersonId()
    {
        List<ServerPhoneNumberEntity> phones = servicePerson.getPhoneNumberService().findByPersonId(getRandomPerson().getId());
        assertThat(phones.size())
                .as(String.format("Phone number list should contain: '%s' elements", COUNT_PHONE))
                .isEqualTo(COUNT_PHONE);
    }

    @Test
    @DisplayName("Search for phone numbers")
    void testServiceSearch()
    {
        SearchPhoneNumber search = new SearchPhoneNumber();
        search.setPhoneType(PhoneNumberType.PROFESSIONAL);

        List<ServerPhoneNumberEntity> phones = servicePerson.getPhoneNumberService().search(search);
        assertThat(phones.size())
                .as("Phone number list should not be empty!")
                .isNotZero();
    }

    @Test
    @DisplayName("Find a phone number by its identifier")
    void testServiceFindById()
    {
        // Get a random person
        ServerPersonEntity person = getRandomPerson();

        // Get a random phone number
        ServerPhoneNumberEntity phoneNumber = getRandomPhoneNumber(person.getPhoneNumbers());

        LOGGER.info(String.format("First   createdDate: '%s'", phoneNumber.getCreatedDate()));
        LOGGER.info(String.format("First  modifiedDate: '%s'", phoneNumber.getModifiedDate()));


        ServerPhoneNumberEntity other = servicePerson.getPhoneNumberService().findById(phoneNumber.getId());
        LOGGER.info(String.format("Second  createdDate: '%s'", other.getCreatedDate()));
        LOGGER.info(String.format("Second modifiedDate: '%s'", other.getModifiedDate()));

        assertThat(other)
                .as("Phone numbers entities should be equal!")
                .isEqualTo(phoneNumber);
    }

    @Test
    @DisplayName("Save a phone number")
    void testServiceSave()
    {
        // Already covered by the beforeEach service

        ServerPersonEntity person = getRandomPerson();
        assertThat(person)
                .as("Person should not be null!")
                .isNotNull();
        assertThat(person.getPhoneNumbers())
                .as("Phone list should not be null!")
                .isNotNull();
        assertThat(person.getPhoneNumbers().size())
                .as(String.format("Phone list should contain: '%s' elements!", COUNT_PHONE))
                .isEqualTo(COUNT_PHONE);
    }

    @Test
    @DisplayName("Update a phone number")
    void testServiceSaveAndFlush()
    {
        UUID reference = UUID.randomUUID(); // Generate a random UUID that will be used to update one of the phone number name.
        UUID uuidPhone;

        // Get a random person
        ServerPersonEntity person = getRandomPerson();

        // Get a random phone number
        ServerPhoneNumberEntity phoneNumber = getRandomPhoneNumber(person.getPhoneNumbers());

        // Update the phone number name
        uuidPhone = phoneNumber.getId();
        phoneNumber.setName(reference.toString());
        servicePerson.getPersonService().saveAndFlush(person);

        // Reload the phone number
        ServerPhoneNumberEntity other = servicePerson.getPhoneNumberService().findById(uuidPhone);
        assertThat(other.getName())
                .as(String.format("Phone number name should be: '%s'", reference.toString()))
                .isEqualTo(reference.toString());
    }

    @Test
    @DisplayName("Delete a phone number (using the person service)")
    void testServiceDeletePhoneNumberUsingPersonService()
    {
        LocalDateTime now = CherryDateTimeHelper.now();

        // Get a random person
        ServerPersonEntity person = getRandomPerson();

        // Get a random phone number
        ServerPhoneNumberEntity phoneNumber = getRandomPhoneNumber(person.getPhoneNumbers());
        UUID uuidPhone = phoneNumber.getId();

        // Orphan phone numbers should be automatically removed
        person.removePhoneNumber(phoneNumber);
        servicePerson.getPersonService().saveAndFlush(person);

        // Re-load the removed orphan phone number
        assertThat(servicePerson.getPhoneNumberService().findById(uuidPhone))
                .as(String.format("Phone number with id: '%s' should be null!", uuidPhone))
                .isNull();
    }

    @Test
    @DisplayName("Delete a phone number by its identifier")
    void testServiceDeleteById()
    {
        // Get a random person
        ServerPersonEntity person = getRandomPerson();

        // Get a random phone number
        ServerPhoneNumberEntity phoneNumber = getRandomPhoneNumber(person.getPhoneNumbers());
        UUID uuidPhone = phoneNumber.getId();

        // Remove the phone number and delete it
        person.removePhoneNumber(phoneNumber);
        servicePerson.getPhoneNumberService().deleteById(uuidPhone);

        // Reload the deleted phone number
        assertThat(servicePerson.getPhoneNumberService().findById(uuidPhone))
                .as(String.format("Phone number with id: '%s' should be null!", uuidPhone))
                .isNull();
    }

    @Test
    @DisplayName("Delete multiple phone numbers")
    void testDeletePhoneNumbers()
    {
        // Get a random person
        ServerPersonEntity person = getRandomPerson();
        UUID uuidPerson = person.getId();

        // Remove all the phone numbers
        person.removeAllPhoneNumber();
        servicePerson.getPersonService().saveAndFlush(person);

        // Reload the person
        ServerPersonEntity other = servicePerson.getPersonService().findById(uuidPerson);
        List<ServerPhoneNumberEntity> phones = other.getPhoneNumbers();
        assertThat(phones.size())
                .as("Phone number list size should be empty!")
                .isZero();
    }

    /**
     * Returns a random person.
     * @return Person.
     */
    private ServerPersonEntity getRandomPerson()
    {
        return persons.get(getRandom().nextInt(persons.size()));
    }

    /**
     * Returns a random phone number from a given list of phone numbers.
     * @param phones List of phone numbers.
     * @return Phone number.
     */
    private ServerPhoneNumberEntity getRandomPhoneNumber(final @NonNull List<ServerPhoneNumberEntity> phones)
    {
        return phones.get(getRandom().nextInt(phones.size()));
    }
}
