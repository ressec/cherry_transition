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
    @DisplayName("Converts a server email address to a client email address")
    final void testConvertServerToClient()
    {
        ServerEmailAddressEntity server = EmailAddressRandomizer.generateServer(true);
        ServerPersonEntity person = PersonRandomizer.generateServer(true);
        server.setPerson(person);

        ClientEmailAddressEntity client = EmailAddressConverter.convertServer(server);
        checkFields(server, client);
    }

    @Test
    @DisplayName("Converts a server email address with associated documents to a client email address")
    final void testConvertServerWithDocumentsToClient() throws DocumentContentException
    {
        // The purpose of this test is to ensure a document owner (on client side) is mapped to an EntityIdentity (light representation of a server entity instance).
        ServerEmailAddressEntity server = EmailAddressRandomizer.generateServerWithDocument(true, 5);
        ClientEmailAddressEntity client = EmailAddressConverter.convertServer(server);
        checkFields(server, client);
    }

    @Test
    @DisplayName("Converts a list of server email addresses to a list of client email addresses")
    final void testConvertListServerToListClient() throws EmailAddressException
    {
        List<ServerEmailAddressEntity> serverList = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            serverList.add(EmailAddressRandomizer.generateServer(true));
        }

        List<ClientEmailAddressEntity> clientList = EmailAddressConverter.convertServerList(serverList);
        checkFields(serverList, clientList);
    }

    @Test
    @DisplayName("Converts a list of server email addresses with associated documents to a list of client email addresses")
    final void testConvertListServerWithDocumentsToListClient() throws EmailAddressException, DocumentContentException
    {
        List<ServerEmailAddressEntity> serverList = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            serverList.add(EmailAddressRandomizer.generateServerWithDocument(true, 3));
        }

        List<ClientEmailAddressEntity> clientList = EmailAddressConverter.convertServerList(serverList);
        checkFields(serverList, clientList);
    }

    @Test
    @DisplayName("Converts a client email address to a server email address")
    final void testConvertClientToServer()
    {
        ClientEmailAddressEntity client = EmailAddressRandomizer.generateClient(true);
        ServerEmailAddressEntity server = EmailAddressConverter.convertClient(client, null);
        checkFields(server, client);
    }

    @Test
    @DisplayName("Converts a client email address with documents to a server email address")
    final void testConvertClientWithDocumentToServer() throws DocumentContentException
    {
        ClientEmailAddressEntity client = EmailAddressRandomizer.generateClientWithDocument(true, 3);
        ServerEmailAddressEntity server = EmailAddressConverter.convertClient(client, null);
        checkFields(server, client);
    }

    @Test
    @DisplayName("Converts a list of client email addresses to a list of server email addresses")
    final void testConvertListClientToListServer() throws EmailAddressException
    {
        List<ClientEmailAddressEntity> clientList = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            clientList.add(EmailAddressRandomizer.generateClient(true));
        }

        List<ServerEmailAddressEntity> serverList = EmailAddressConverter.convertClientList(clientList, null);
        checkFields(serverList, clientList);
    }

    @Test
    @DisplayName("Converts a list of client email addresses with documents to a list of server email addresses")
    final void testConvertListClientWithDocumentToListServer() throws EmailAddressException, DocumentContentException
    {
        List<ClientEmailAddressEntity> clientList = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            clientList.add(EmailAddressRandomizer.generateClientWithDocument(true, 5));
        }

        List<ServerEmailAddressEntity> serverList = EmailAddressConverter.convertClientList(clientList, null);
        checkFields(serverList, clientList);
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
    @DisplayName("Create a deep copy of a server email address")
    final void testCopyPersistent()
    {
        ServerEmailAddressEntity server = EmailAddressRandomizer.generateServer(true);
        ServerEmailAddressEntity copy = EmailAddressConverter.copy(server);
        checkFields(server, copy);
    }

    @Test
    @DisplayName("Cannot associate an email address to multiple server persons")
    final void testCannotSetEmailAddressToMultipleServerPerson() throws EmailAddressException
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
     * @param server Server entity.
     * @param client Client entity.
     */
    private void checkFields(final @NonNull ServerEmailAddressEntity server, final @NonNull ClientEmailAddressEntity client)
    {
        checkBaseFields(server, client);

        // Do not test equality for transient fields and for owner!
        assertThat(server.getEmail())
                .as("Emails should be equal!")
                .isEqualTo(client.getEmail());
        assertThat(server.getAddressType())
                .as("Address types should be equal!")
                .isEqualTo(client.getAddressType());
        assertThat(server.getIsDefaultEmail())
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
     * @param server Server entity.
     * @param copy Copy entity.
     */
    private void checkFields(final @NonNull ServerEmailAddressEntity server, final @NonNull ServerEmailAddressEntity copy)
    {
        checkBaseFields(server, copy);

        // Do not test equality for transient fields and for owner!
        assertThat(copy.getEmail())
                .as("Emails should be equal!")
                .isEqualTo(server.getEmail());
        assertThat(copy.getAddressType())
                .as("Address types should be equal!")
                .isEqualTo(server.getAddressType());
        assertThat(copy.getIsDefaultEmail())
                .as("Is default should be equal!")
                .isEqualTo(server.getIsDefaultEmail());
    }

    /**
     * Checks equality of fields between email address entities.
     * @param serverList List of persistent email addresses.
     * @param clientList List of client email addresses.
     * @throws EmailAddressException Thrown in case an error occurred finding a client email address!
     */
    private void checkFields(final @NonNull List<ServerEmailAddressEntity> serverList, final @NonNull List<ClientEmailAddressEntity> clientList) throws EmailAddressException
    {
        Optional<ClientEmailAddressEntity> client;
        for (ServerEmailAddressEntity server : serverList)
        {
            client = clientList.stream().filter(element -> element.getId().equals(server.getId())).findFirst();
            if (client.isPresent())
            {
                checkBaseFields(server, client.get());
            }
            else
            {
                throw new EmailAddressException(String.format("Cannot find client email address with id: '%s'!", server.getId()));
            }
        }
    }
}
