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
import com.hemajoo.commerce.cherry.model.person.entity.ClientEmailAddressEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EntityException;
import com.hemajoo.commerce.cherry.persistence.base.entity.EntityComparator;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServiceFactoryPerson;
import com.hemajoo.commerce.cherry.persistence.person.converter.EmailAddressConverter;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.EmailAddressRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.PersonRandomizer;
import com.hemajoo.commerce.cherry.persistence.test.unit.base.AbstractPostgresUnitTest;
import org.javers.core.diff.Diff;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for the email address converter class.
 * <hr>
 * Unit tests are supposed to be executed with the Maven 'db-test' profile activated.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@DirtiesContext
@Testcontainers // Not to be used to keep container alive after the tests!
@SpringBootTest
class EmailAddressConverterUnitTest extends AbstractPostgresUnitTest
{
    /**
     * Email address converter facility service.
     */
    @Autowired
    private EmailAddressConverter converterEmailAddress;

    /**
     * Entity factory.
     */
    @Autowired
    private ServiceFactoryPerson servicePerson;

    /**
     * List element count.
     */
    private final int LIST_COUNT = 10;

    @Test
    @DisplayName("Convert a server email address to an entity identity")
    final void testConvertServerEmailAddressToIdentity()
    {
        ServerEmailAddressEntity server = EmailAddressRandomizer.generateServerEntity(true);
        EntityIdentity identity = converterEmailAddress.fromServerToIdentity(server);

        assertThat(identity)
                .as("Entity identity should not be null!")
                .isNotNull();
    }

    @Test
    @DisplayName("Convert an entity identity to a server email address")
    final void testConvertIdentityToServerEmailAddress() throws EntityException
    {
        // For an entity identity to be mapped to a server entity, the server entity must exist in the underlying database!
        ServerPersonEntity owner = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));
        ServerEmailAddressEntity reference = EmailAddressRandomizer.generateServerEntity(false);
        reference.setPerson(owner);
        reference = servicePerson.getEmailAddressService().save(reference);

        EntityIdentity identity = new EntityIdentity(reference.getIdentity());
        ServerEmailAddressEntity server = converterEmailAddress.fromIdentityToServer(identity);

        assertThat(server)
                .as("Server email address should not be null!")
                .isNotNull();
    }

    @Test
    @DisplayName("Convert a fake (not existing) entity identity to a server email address. Should raise an exception!")
    @SuppressWarnings("java:S5977")
    final void testConvertIdentityToServerEmailAddressNotExisting()
    {
        EntityIdentity identity = new EntityIdentity(UUID.randomUUID(), EntityType.EMAIL_ADDRESS);

        assertThatThrownBy(() -> converterEmailAddress.fromIdentityToServer(identity))
                .isInstanceOf(EntityException.class);
    }

    @Test
    @DisplayName("Convert a client email address to a server email address")
    final void testConvertClientToServerEmailAddressWithOwner()
    {
        ClientEmailAddressEntity client = EmailAddressRandomizer.generateClientEntity(true);
        ServerEmailAddressEntity server = converterEmailAddress.fromClientToServer(client);

        assertThat(server)
                .as("Server email address should not be null!")
                .isNotNull();
    }

    @Test
    @DisplayName("Convert a client email address (owner person does not exist) to a server email address. Should raise an exception!")
    @SuppressWarnings("java:S5977")
    final void testConvertClientToServerEmailAddressWithNonExistentOwner()
    {
        ClientEmailAddressEntity client = EmailAddressRandomizer.generateClientEntity(true);
        client.setPerson(new EntityIdentity(EntityType.EMAIL_ADDRESS,UUID.randomUUID()));

        // If the owner of the client email address to convert does not exist, ensure an exception is thrown!
        assertThatThrownBy(() -> converterEmailAddress.fromClientToServer(client))
                .isInstanceOf(EntityException.class);
    }

    @Test
    @DisplayName("Convert a list of server email addresses to a list of client email addresses")
    final void testConvertServerToClientEmailAddressList()
    {
        List<ServerEmailAddressEntity> emails = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            emails.add(EmailAddressRandomizer.generateServerEntity(true));
        }

        List<ClientEmailAddressEntity> clients = emails.stream()
                .map(email -> converterEmailAddress.fromServerToClient(email))
                .collect(Collectors.toList());

        assertThat(clients.size())
                .as("Email address server and client list should have the same size!")
                .isEqualTo(clients.size());

        clients.forEach(client -> assertThat(client)
                .as("Client email address should not be null!")
                .isNotNull());
    }

    @Test
    @DisplayName("Convert a list of client email addresses to a list of server email addresses")
    final void testConvertClientToServerEmailAddressList()
    {
        List<ServerEmailAddressEntity> emails = new ArrayList<>();

        // Create some random server email addresses.
        for (int i = 0; i < LIST_COUNT; i++)
        {
            emails.add(EmailAddressRandomizer.generateServerEntity(true));
        }

        // Convert from server to client email address list.
        List<ClientEmailAddressEntity> clients = emails.stream()
                .map(element -> converterEmailAddress.fromServerToClient(element))
                .collect(Collectors.toList());

        assertThat(clients.size())
                .as("Email address server and client list should have the same size!")
                .isEqualTo(emails.size());

        clients.forEach(element -> assertThat(element)
                .as("Client email address should not be null!")
                .isNotNull());

        // Convert back from client to server email address list.
        List<ServerEmailAddressEntity> servers = clients.stream()
                .map(element -> converterEmailAddress.fromClientToServer(element))
                .collect(Collectors.toList());

        assertThat(clients.size())
                .as("Email address server and client list should have the same size!")
                .isEqualTo(servers.size());

        servers.forEach(element -> assertThat(element)
                .as("Server email address should not be null!")
                .isNotNull());
    }

    @Test
    @DisplayName("Convert a list of server email address to a list of entity identities")
    final void testConvertServerToIdentityEmailAddressList()
    {
        List<ServerEmailAddressEntity> emails = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            emails.add(EmailAddressRandomizer.generateServerEntity(true));
        }

        // Convert from server to identity list.
        List<EntityIdentity> identities = emails.stream()
                .map(element -> converterEmailAddress.fromServerToIdentity(element))
                .collect(Collectors.toList());

        assertThat(identities.size())
                .as("Both lists should have the same size!")
                .isEqualTo(emails.size());
    }

    @Test
    @DisplayName("Convert a list entity identities to a list of server email address")
    final void testConvertIdentityToServerEmailAddressList() throws EntityException
    {
        ServerEmailAddressEntity email;
        ServerPersonEntity owner = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        // When testing identities, we must ensure the objects exist in the underlying database.
        List<ServerEmailAddressEntity> emails = new ArrayList<>();
        for (int i = 0; i < LIST_COUNT; i++)
        {
            email = EmailAddressRandomizer.generateServerEntity(false);
            email.setPerson(owner);
            emails.add(servicePerson.getEmailAddressService().save(email));
        }

        List<EntityIdentity> identities = emails.stream()
                .map(element -> converterEmailAddress.fromServerToIdentity(element))
                .collect(Collectors.toList());

        assertThat(identities.size())
                .as("Both lists should have the same size!")
                .isEqualTo(emails.size());

        List<ServerEmailAddressEntity> servers = new ArrayList<>();
        for (EntityIdentity identity : identities)
        {
            servers.add(converterEmailAddress.fromIdentityToServer(identity));
        }

        assertThat(servers.size())
                .as("Both lists should have the same size!")
                .isEqualTo(identities.size());
    }

    @Test
    @DisplayName("Compare a server email address and its copy are equal")
    final void testCopyServerEmailAddress()
    {
        ServerEmailAddressEntity original = EmailAddressRandomizer.generateServerEntity(true);
        ServerEmailAddressEntity copy = EmailAddressConverter.copy(original);
        assertThat(original)
                .as("Both server email addresses should be equal!")
                .isEqualTo(copy);

        Diff diff = EntityComparator.getJavers().compare(original, copy);
        assertThat(diff.getChanges().size())
                .as("Both server email addresses should be equal!")
                .isZero();
    }

    @Test
    @DisplayName("Compare a client email address and its copy are equal")
    final void testCopyClientEmailAddress()
    {
        ClientEmailAddressEntity original = EmailAddressRandomizer.generateClientEntity(true);
        ClientEmailAddressEntity copy = EmailAddressConverter.copy(original);
        assertThat(original)
                .as("Both client email addresses should be equal!")
                .isEqualTo(copy);

        Diff diff = EntityComparator.getJavers().compare(original, copy);
        assertThat(diff.getChanges().size())
                .as("Both client email addresses should be equal!")
                .isZero();
    }
}
