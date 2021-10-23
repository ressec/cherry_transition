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

import com.hemajoo.commerce.cherry.model.document.exception.DocumentContentException;
import com.hemajoo.commerce.cherry.model.person.entity.ClientEmailAddressEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.persistence.person.converter.EmailAddressConverter;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.EmailAddressRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.PersonRandomizer;
import com.hemajoo.commerce.cherry.persistence.test.unit.base.AbstractBaseMapperTest;
import lombok.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test the {@link EmailAddressConverter} class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
class EmailAddressConverterUnitTest extends AbstractBaseMapperTest
{
    /**
     * List element count.
     */
    private final int LIST_COUNT = 10;

    @Test
    @DisplayName("Converts a persistent email address to a client email address")
    final void testConvertPersistentToClient()
    {
        ServerEmailAddressEntity persistent = EmailAddressRandomizer.generateServer(true);
        ClientEmailAddressEntity client = EmailAddressConverter.convertServer(persistent);
        checkFields(persistent, client);
    }

    @Test
    @DisplayName("Converts a persistent email address with associated documents to a client email address")
    final void testConvertPersistentWithDocumentsToClient() throws DocumentContentException
    {
        // The purpose of this test is to ensure a document owner (on client side) is mapped to an EntityIdentity (light representation of a server entity instance).
        ServerEmailAddressEntity persistent = EmailAddressRandomizer.generateServerWithDocument(true, 5);
        ClientEmailAddressEntity client = EmailAddressConverter.convertServer(persistent);
        checkFields(persistent, client);
    }

    @Test
    @DisplayName("Converts a list of persistent email addresses to a list of client email addresses")
    final void testConvertListPersistentToListClient() throws EmailAddressException
    {
        List<ServerEmailAddressEntity> persistentList = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            persistentList.add(EmailAddressRandomizer.generateServer(true));
        }

        List<ClientEmailAddressEntity> clientList = EmailAddressConverter.convertServerList(persistentList);
        checkFields(persistentList, clientList);
    }

    @Test
    @DisplayName("Converts a list of persistent email addresses with associated documents to a list of client email addresses")
    final void testConvertListPersistentWithDocumentsToListClient() throws EmailAddressException, DocumentContentException
    {
        List<ServerEmailAddressEntity> persistentList = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            persistentList.add(EmailAddressRandomizer.generateServerWithDocument(true, 3));
        }

        List<ClientEmailAddressEntity> clientList = EmailAddressConverter.convertServerList(persistentList);
        checkFields(persistentList, clientList);
    }

    @Test
    @DisplayName("Converts a client email address to a persistent email address")
    final void testConvertClientToPersistent()
    {
        ClientEmailAddressEntity client = EmailAddressRandomizer.generateClient(true);
        ServerEmailAddressEntity persistent = EmailAddressConverter.convertClient(client);
        checkFields(persistent, client);
    }

    @Test
    @DisplayName("Converts a client email address with documents to a persistent email address")
    final void testConvertClientWithDocumentToPersistent() throws DocumentContentException
    {
        ClientEmailAddressEntity client = EmailAddressRandomizer.generateClientWithDocument(true, 3);
        ServerEmailAddressEntity persistent = EmailAddressConverter.convertClient(client);
        checkFields(persistent, client);
    }

    @Test
    @DisplayName("Converts a list of client email addresses to a list of persistent email addresses")
    final void testConvertListClientToListPersistent() throws EmailAddressException
    {
        List<ClientEmailAddressEntity> clientList = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            clientList.add(EmailAddressRandomizer.generateClient(true));
        }

        List<ServerEmailAddressEntity> persistentList = EmailAddressConverter.convertClientList(clientList);
        checkFields(persistentList, clientList);
    }

    @Test
    @DisplayName("Converts a list of client email addresses with documents to a list of persistent email addresses")
    final void testConvertListClientWithDocumentToListPersistent() throws EmailAddressException, DocumentContentException
    {
        List<ClientEmailAddressEntity> clientList = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            clientList.add(EmailAddressRandomizer.generateClientWithDocument(true, 5));
        }

        List<ServerEmailAddressEntity> persistentList = EmailAddressConverter.convertClientList(clientList);
        checkFields(persistentList, clientList);
    }

    @Test
    @DisplayName("Create a deep copy of a client email address")
    final void testCopyClient()
    {
        ClientEmailAddressEntity client = EmailAddressRandomizer.generateClient(true);
        ClientEmailAddressEntity copy = EmailAddressConverter.copy(client);
        checkFields(client, copy);
    }

    @Test
    @DisplayName("Create a deep copy of a persistent email address")
    final void testCopyPersistent()
    {
        ServerEmailAddressEntity persistent = EmailAddressRandomizer.generateServer(true);
        ServerEmailAddressEntity copy = EmailAddressConverter.copy(persistent);
        checkFields(persistent, copy);
    }

    @Test
    @DisplayName("Cannot associate an email address to multiple persistent persons")
    final void testCannotSetEmailAddressToMultiplePersistentPerson() throws EmailAddressException
    {
        ServerPersonEntity person1 = PersonRandomizer.generateServer(true);
        ServerPersonEntity person2 = PersonRandomizer.generateServer(true);
        ServerEmailAddressEntity email = EmailAddressRandomizer.generateServer(true);

        // Cannot associate the same email address to multiple persons!
        person1.addEmailAddress(email);
        assertThatThrownBy(() -> {
            person2.addEmailAddress(email);
        }).isInstanceOf(EmailAddressException.class);
    }

    /**
     * Checks equality of fields between a persistent and a client entities.
     * @param persistent Persistent entity.
     * @param client Client entity.
     */
    private void checkFields(final @NonNull ServerEmailAddressEntity persistent, final @NonNull ClientEmailAddressEntity client)
    {
        checkBaseFields(persistent, client);

        // Do not test equality for transient fields and for owner!
        assertThat(persistent.getEmail())
                .as("Emails should be equal!")
                .isEqualTo(client.getEmail());
        assertThat(persistent.getAddressType())
                .as("Address types should be equal!")
                .isEqualTo(client.getAddressType());
        assertThat(persistent.getIsDefaultEmail())
                .as("Is default should be equal!")
                .isEqualTo(client.getIsDefaultEmail());
    }

    /**
     * Checks equality of fields between two client person entities.
     * @param client Client entity.
     * @param copy Client entity.
     */
    private void checkFields(final @NonNull ClientEmailAddressEntity client, final @NonNull ClientEmailAddressEntity copy)
    {
        checkBaseFields(client, copy);

        // Do not test equality for transient fields and for owner!
        assertThat(copy.getEmail())
                .as("Emails should be equal!")
                .isEqualTo(client.getEmail());
        assertThat(copy.getAddressType())
                .as("Address types should be equal!")
                .isEqualTo(client.getAddressType());
        assertThat(copy.getIsDefaultEmail())
                .as("Is default should be equal!")
                .isEqualTo(client.getIsDefaultEmail());
    }

    /**
     * Checks equality of fields between two persistent person entities.
     * @param persistent Persistent entity.
     * @param copy Persistent entity.
     */
    private void checkFields(final @NonNull ServerEmailAddressEntity persistent, final @NonNull ServerEmailAddressEntity copy)
    {
        checkBaseFields(persistent, copy);

        // Do not test equality for transient fields and for owner!
        assertThat(copy.getEmail())
                .as("Emails should be equal!")
                .isEqualTo(persistent.getEmail());
        assertThat(copy.getAddressType())
                .as("Address types should be equal!")
                .isEqualTo(persistent.getAddressType());
        assertThat(copy.getIsDefaultEmail())
                .as("Is default should be equal!")
                .isEqualTo(persistent.getIsDefaultEmail());
    }

    /**
     * Checks equality of fields between email address entities.
     * @param persistentList List of persistent email addresses.
     * @param clientList List of client email addresses.
     * @throws EmailAddressException Thrown in case an error occurred finding a client email address!
     */
    private void checkFields(final @NonNull List<ServerEmailAddressEntity> persistentList, final @NonNull List<ClientEmailAddressEntity> clientList) throws EmailAddressException
    {
        Optional<ClientEmailAddressEntity> client;
        for (ServerEmailAddressEntity persistent : persistentList)
        {
            client = clientList.stream().filter(element -> element.getId().equals(persistent.getId())).findFirst();
            if (client.isPresent())
            {
                checkBaseFields(persistent, client.get());
            }
            else
            {
                throw new EmailAddressException(String.format("Cannot find client email address with id: '%s'!", persistent.getId()));
            }
        }
    }
}
