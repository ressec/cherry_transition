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
package com.hemajoo.commerce.cherry.persistence.test.unit.model.entity.person;

import com.hemajoo.commerce.cherry.model.person.entity.ClientPersonEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.model.person.exception.PersonException;
import com.hemajoo.commerce.cherry.persistence.person.converter.PersonConverter;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.PersonRandomizer;
import com.hemajoo.commerce.cherry.persistence.test.unit.base.AbstractBaseMapperTest;
import lombok.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test the {@link PersonConverter} class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
class PersonConverterUnitTest extends AbstractBaseMapperTest
{
    /**
     * List element count.
     */
    private final int LIST_COUNT = 10;

    @Test
    @DisplayName("Map a persistent person to a client person")
    final void testMapPersistentToClient()
    {
        ServerPersonEntity persistent = PersonRandomizer.generatePersistent(true);
        ClientPersonEntity client = PersonConverter.convertServer(persistent);
        checkFields(persistent, client);
    }

    @Test
    @DisplayName("Map a list of persistent persons to a list of client persons")
    final void testMapListPersistentToListClient() throws PersonException
    {
        List<ServerPersonEntity> persistentList = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            persistentList.add(PersonRandomizer.generatePersistent(true));
        }

        List<ClientPersonEntity> clientList = PersonConverter.convertServerList(persistentList);
        checkFields(persistentList, clientList);
    }

    @Test
    @DisplayName("Map a client person to a persistent person")
    final void testMapClientToPersistent() throws PersonException, EmailAddressException
    {
        ClientPersonEntity client = PersonRandomizer.generateClient(true);
        ServerPersonEntity persistent = PersonConverter.convertClient(client);
        checkFields(persistent, client);
    }

    @Test
    @DisplayName("Map a list of client persons to a list of persistent persons")
    final void testMapListClientToListPersistent() throws PersonException, EmailAddressException
    {
        List<ClientPersonEntity> clientList = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            clientList.add(PersonRandomizer.generateClient(true));
        }

        List<ServerPersonEntity> persistentList = PersonConverter.convertClientList(clientList);
        checkFields(persistentList, clientList);
    }

    @Test
    @DisplayName("Create a deep copy of a client person")
    final void testCopyClient()
    {
        ClientPersonEntity client = PersonRandomizer.generateClient(true);
        ClientPersonEntity copy = PersonConverter.copy(client);
        checkFields(client, copy);
    }

    @Test
    @DisplayName("Create a deep copy of a persistent person")
    final void testCopyPersistent()
    {
        ServerPersonEntity persistent = PersonRandomizer.generatePersistent(true);
        ServerPersonEntity copy = PersonConverter.copy(persistent);
        checkFields(persistent, copy);
    }

    /**
     * Checks equality of fields between a persistent and a client entities.
     * @param persistent Persistent entity.
     * @param client Client entity.
     */
    private void checkFields(final @NonNull ServerPersonEntity persistent, final @NonNull ClientPersonEntity client)
    {
        checkBaseFields(persistent, client);

        // Do not test equality for transient fields and for owner!
        assertThat(persistent.getLastName())
                .as("Last names should be equal!")
                .isEqualTo(client.getLastName());
        assertThat(persistent.getFirstName())
                .as("First names should be equal!")
                .isEqualTo(client.getFirstName());
        assertThat(persistent.getPersonType())
                .as("Person types should be equal!")
                .isEqualTo(client.getPersonType());
        assertThat(persistent.getBirthDate())
                .as("Birth dates should be equal!")
                .isEqualTo(client.getBirthDate());
        assertThat(persistent.getGenderType())
                .as("Gender types should be equal!")
                .isEqualTo(client.getGenderType());
        assertThat(persistent.getEmailAddresses().size())
                .as("Email address list sizes should be equal!")
                .isEqualTo(client.getEmailAddresses().size());
    }

    /**
     * Checks equality of fields between two client person entities.
     * @param client Client entity.
     * @param copy Client entity.
     */
    private void checkFields(final @NonNull ClientPersonEntity client, final @NonNull ClientPersonEntity copy)
    {
        checkBaseFields(client, copy);

        // Do not test equality for transient fields and for owner!
        assertThat(copy.getLastName())
                .as("Last names should be equal!")
                .isEqualTo(client.getLastName());
        assertThat(copy.getFirstName())
                .as("First names should be equal!")
                .isEqualTo(client.getFirstName());
        assertThat(copy.getPersonType())
                .as("Person types should be equal!")
                .isEqualTo(client.getPersonType());
        assertThat(copy.getBirthDate())
                .as("Birth dates should be equal!")
                .isEqualTo(client.getBirthDate());
        assertThat(copy.getGenderType())
                .as("Gender types should be equal!")
                .isEqualTo(client.getGenderType());
        assertThat(copy.getEmailAddresses().size())
                .as("Email address list sizes should be equal!")
                .isEqualTo(client.getEmailAddresses().size());
    }

    /**
     * Checks equality of fields between two persistent person entities.
     * @param persistent Persistent entity.
     * @param copy Persistent entity.
     */
    private void checkFields(final @NonNull ServerPersonEntity persistent, final @NonNull ServerPersonEntity copy)
    {
        checkBaseFields(persistent, copy);

        // Do not test equality for transient fields and for owner!
        assertThat(copy.getLastName())
                .as("Last names should be equal!")
                .isEqualTo(persistent.getLastName());
        assertThat(copy.getFirstName())
                .as("First names should be equal!")
                .isEqualTo(persistent.getFirstName());
        assertThat(copy.getPersonType())
                .as("Person types should be equal!")
                .isEqualTo(persistent.getPersonType());
        assertThat(copy.getBirthDate())
                .as("Birth dates should be equal!")
                .isEqualTo(persistent.getBirthDate());
        assertThat(copy.getGenderType())
                .as("Gender types should be equal!")
                .isEqualTo(persistent.getGenderType());
        assertThat(copy.getEmailAddresses().size())
                .as("Email address list sizes should be equal!")
                .isEqualTo(persistent.getEmailAddresses().size());
    }

    /**
     * Checks equality of fields between person entities.
     * @param persistentList List of persistent persons.
     * @param clientList List of client persons.
     * @throws PersonException Thrown in case an error occurred finding a client person!
     */
    private void checkFields(final @NonNull List<ServerPersonEntity> persistentList, final @NonNull List<ClientPersonEntity> clientList) throws PersonException
    {
        Optional<ClientPersonEntity> client;
        for (ServerPersonEntity persistent : persistentList)
        {
            client = clientList.stream().filter(element -> element.getId().equals(persistent.getId())).findFirst();
            if (client.isPresent())
            {
                checkBaseFields(persistent, client.get());
            }
            else
            {
                throw new PersonException(String.format("Cannot find client person with id: '%s'!", persistent.getId()));
            }
        }
    }
}
