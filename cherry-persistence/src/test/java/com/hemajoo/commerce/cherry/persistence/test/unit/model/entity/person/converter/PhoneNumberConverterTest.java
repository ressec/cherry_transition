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
package com.hemajoo.commerce.cherry.persistence.test.unit.model.entity.person.converter;

import com.hemajoo.commerce.cherry.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.model.person.entity.ClientPhoneNumberEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EntityException;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServiceFactoryPerson;
import com.hemajoo.commerce.cherry.persistence.person.converter.PhoneNumberConverter;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPhoneNumberEntity;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.PersonRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.PhoneNumberRandomizer;
import com.hemajoo.commerce.cherry.persistence.test.unit.base.AbstractPostgresUnitTest;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for the {@link PhoneNumberConverter} class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@DirtiesContext
@Testcontainers // Not to be used to keep container alive after the tests!
@SpringBootTest
class PhoneNumberConverterTest extends AbstractPostgresUnitTest
{
    /**
     * Phone number converter.
     */
    @Autowired
    private PhoneNumberConverter phoneNumberConverter;

    /**
     * Person service helper.
     */
    @Autowired
    private ServiceFactoryPerson personServiceHelper;

    /**
     * List element count.
     */
    private final int LIST_COUNT = 10;

    @Test
    @DisplayName("Convert a server phone number to an entity identity")
    final void testPhoneNumberConvertServerToIdentity()
    {
        ServerPhoneNumberEntity server = PhoneNumberRandomizer.generateServerEntity(true);
        EntityIdentity identity = phoneNumberConverter.fromServerToIdentity(server);

        assertThat(identity)
                .as("Identity entity should not be null!")
                .isNotNull();
        assertThat(identity.getId())
                .as("Identity entity identifier should match server entity identifier!")
                .isEqualTo(server.getId());
    }

    @Test
    @DisplayName("Convert an entity identity to a server phone number")
    final void testPhoneNumberConvertIdentityToServer() throws EntityException
    {
        // For an entity identity to be mapped to a server entity, the server entity must exist in the underlying database!
        ServerPersonEntity owner = personServiceHelper.getPersonService().save(PersonRandomizer.generateServerEntity(false));
        ServerPhoneNumberEntity reference = PhoneNumberRandomizer.generateServerEntity(false);
        reference.setPerson(owner);
        reference = personServiceHelper.getPhoneNumberService().save(reference);

        EntityIdentity identity = new EntityIdentity(reference.getIdentity());
        ServerPhoneNumberEntity server = phoneNumberConverter.fromIdentityToServer(identity);

        assertThat(server)
                .as("Server entity should not be null!")
                .isNotNull();
        assertThat(identity.getId())
                .as("Identity identifier should match server identifier!")
                .isEqualTo(server.getId());
    }

    @Test
    @DisplayName("Convert an entity identity to a server phone number (phone number does not exist ... should raise an exception)")
    @SuppressWarnings("java:S5977")
    final void testPhoneNumberNotExistingConvertIdentityToServer()
    {
        EntityIdentity identity = new EntityIdentity(UUID.randomUUID(), EntityType.PHONE_NUMBER);
        assertThatThrownBy(() -> phoneNumberConverter.fromIdentityToServer(identity))
                .isInstanceOf(EntityException.class);
    }

    @Test
    @DisplayName("Convert a client phone number to a server phone number")
    final void testPhoneNumberConvertClientToServer()
    {
        ClientPhoneNumberEntity client = PhoneNumberRandomizer.generateClientEntity(true);
        ServerPhoneNumberEntity server = phoneNumberConverter.fromClientToServer(client);

        assertThat(server)
                .as("Server entity should not be null!")
                .isNotNull();
        assertThat(client.getId())
                .as("Client entity identifier should match server entity identifier!")
                .isEqualTo(server.getId());
    }

    @Test
    @DisplayName("Convert a client phone number to a server phone number (with owner not existing)")
    @SuppressWarnings("java:S5977")
    final void testPhoneNumberConvertClientToServerWithOwnerNotExisting()
    {
        ClientPhoneNumberEntity client = PhoneNumberRandomizer.generateClientEntity(true);
        client.setPerson(new EntityIdentity(EntityType.PHONE_NUMBER,UUID.randomUUID()));

        // If the owner of the client entity to convert does not exist, ensure an exception is thrown!
        assertThatThrownBy(() -> phoneNumberConverter.fromClientToServer(client))
                .isInstanceOf(EntityException.class);
    }

    @Test
    @DisplayName("Convert a list of server phone numbers to a list of client phone numbers")
    final void testPhoneNumberConvertServerListToClientList()
    {
        List<ServerPhoneNumberEntity> entities = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            entities.add(PhoneNumberRandomizer.generateServerEntity(true));
        }

        List<ClientPhoneNumberEntity> clients = entities.stream()
                .map(email -> phoneNumberConverter.fromServerToClient(email))
                .toList();

        assertThat(clients.size())
                .as("Server and client list should have the same size!")
                .isEqualTo(clients.size());
        clients.forEach(client -> assertThat(client)
                .as("Client entity should not be null!")
                .isNotNull());
    }

    @Test
    @DisplayName("Convert a list of client phone numbers to a list of server phone numbers")
    final void testPhoneNumberConvertClientListToServerList()
    {
        List<ServerPhoneNumberEntity> entities = new ArrayList<>();

        // Create some random server email addresses.
        for (int i = 0; i < LIST_COUNT; i++)
        {
            entities.add(PhoneNumberRandomizer.generateServerEntity(true));
        }

        // Convert from server list to client list.
        List<ClientPhoneNumberEntity> clients = entities.stream()
                .map(element -> phoneNumberConverter.fromServerToClient(element))
                .toList();

        assertThat(clients.size())
                .as("Server list and client list should have the same size!")
                .isEqualTo(entities.size());
        clients.forEach(element -> assertThat(element)
                .as("Client entity should not be null!")
                .isNotNull());

        // Convert back from client list to server list.
        List<ServerPhoneNumberEntity> servers = clients.stream()
                .map(element -> phoneNumberConverter.fromClientToServer(element))
                .toList();

        assertThat(clients.size())
                .as("Server list and client list should have the same size!")
                .isEqualTo(servers.size());
        servers.forEach(element -> assertThat(element)
                .as("Server entity should not be null!")
                .isNotNull());
    }

    @Test
    @DisplayName("Convert a list of server email address to a list of entity identities")
    final void testPhoneNumberConvertFromServerListToIdentityList()
    {
        List<ServerPhoneNumberEntity> entities = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            entities.add(PhoneNumberRandomizer.generateServerEntity(true));
        }

        // Convert from server to identity list.
        List<EntityIdentity> identities = entities.stream()
                .map(element -> phoneNumberConverter.fromServerToIdentity(element))
                .toList();
        assertThat(identities.size())
                .as("Both lists should have the same size!")
                .isEqualTo(entities.size());
    }

    @Test
    @DisplayName("Convert a list of identities list to a list of server phone numbers")
    final void testPhoneNumberConvertIdentityListToServerList() throws EntityException
    {
        ServerPhoneNumberEntity entity;
        ServerPersonEntity owner = personServiceHelper.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        // When testing identities, we must ensure the objects exist in the underlying database!
        List<ServerPhoneNumberEntity> emails = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            entity = PhoneNumberRandomizer.generateServerEntity(false);
            entity.setPerson(owner);
            emails.add(personServiceHelper.getPhoneNumberService().save(entity));
        }

        List<EntityIdentity> identities = emails.stream()
                .map(element -> phoneNumberConverter.fromServerToIdentity(element))
                .toList();
        assertThat(identities.size())
                .as("Both lists should have the same size!")
                .isEqualTo(emails.size());

        List<ServerPhoneNumberEntity> servers = new ArrayList<>();
        for (EntityIdentity identity : identities)
        {
            servers.add(phoneNumberConverter.fromIdentityToServer(identity));
        }
        assertThat(servers.size())
                .as("Both lists should have the same size!")
                .isEqualTo(identities.size());
    }

    @Test
    @DisplayName("Copy a server phone number entity")
    final void testPhoneNumberCopyServer()
    {
        ServerPhoneNumberEntity original = PhoneNumberRandomizer.generateServerEntity(true);
        ServerPhoneNumberEntity copy = PhoneNumberConverter.copy(original);
        assertThat(original)
                .as("Both server entities should be equal!")
                .isEqualTo(copy);

//        Diff diff = EntityComparator.getJavers().compare(original, copy);
//        assertThat(diff.getChanges().size())
//                .as("Both server entities should be equal!")
//                .isZero();
    }

    @Test
    @DisplayName("Copy a client phone number entity")
    final void testCopyClientEmailAddress()
    {
        ClientPhoneNumberEntity original = PhoneNumberRandomizer.generateClientEntity(true);
        ClientPhoneNumberEntity copy = PhoneNumberConverter.copy(original);
        assertThat(original)
                .as("Both client entities should be equal!")
                .isEqualTo(copy);

//        Diff diff = EntityComparator.getJavers().compare(original, copy);
//        assertThat(diff.getChanges().size())
//                .as("Both client entities should be equal!")
//                .isZero();
    }
}
