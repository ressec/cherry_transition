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
package com.hemajoo.commerce.cherry.persistence.person.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hemajoo.commerce.cherry.commons.type.EntityType;
import com.hemajoo.commerce.cherry.commons.type.StatusType;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import com.hemajoo.commerce.cherry.model.person.type.GenderType;
import com.hemajoo.commerce.cherry.model.person.type.PersonType;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberType;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServerBaseEntity;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServerEntity;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a person.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Hidden
@Table(name = "PERSON")
@EntityListeners(AuditingEntityListener.class)
public class ServerPersonEntity extends ServerBaseEntity implements ServerPerson, ServerEntity
{
    /**
     * Minimal birthdate.
     */
    public static final LocalDate MIN_BIRTHDATE = LocalDate.of(1500, Month.JANUARY, 1);

    public static final String FIELD_LASTNAME       = "lastName";
    public static final String FIELD_FIRSTNAME      = "firstName";
    public static final String FIELD_BIRTHDATE      = "birthDate";
    public static final String FIELD_GENDER_TYPE    = "genderType";
    public static final String FIELD_PERSON_TYPE    = "personType";

    /**
     * Person last name.
     */
    @Getter
    @NotNull(message = "Person: 'lastName' cannot be null!")
    @Column(name = "LASTNAME")
    private String lastName;

    /**
     * Person first name.
     */
    @Getter
    @NotNull(message = "Person: 'firstName' cannot be null!")
    @Column(name = "FIRSTNAME")
    private String firstName;

    /**
     * Person birthdate.
     */
    @Getter
    @Setter
    @NotNull(message = "Person: 'birthDate' cannot be null!")
    @Column(name = "BIRTHDATE")
    private Date birthDate;

    /**
     * Person type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "PERSON_TYPE", length = 50)
    private PersonType personType;

    /**
     * Person gender type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER_TYPE", length = 50)
    private GenderType genderType;

    /**
     * Postal addresses associated to the person.
     */
    @Getter
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("person")
    @OneToMany(targetEntity = ServerPostalAddressEntity.class, mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ServerPostalAddressEntity> postalAddresses = new ArrayList<>();

    /**
     * Phone numbers associated to the person.
     */
    @Getter
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("person")
    @OneToMany(targetEntity = ServerPhoneNumberEntity.class, mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ServerPhoneNumberEntity> phoneNumbers = new ArrayList<>();

    /**
     * Email addresses associated to the person.
     */
    @Getter
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("owner")
    @OneToMany(targetEntity = ServerEmailAddressEntity.class, mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServerEmailAddressEntity> emailAddresses = new ArrayList<>();

    /**
     * Creates a new person.
     */
    public ServerPersonEntity()
    {
        super(EntityType.PERSON);
    }

    /**
     * Sets the person last name.
     * @param lastName Last name.
     */
    public void setLastName(final @NonNull String lastName)
    {
        this.lastName = lastName;
        setName(this.lastName + ", " + this.firstName);
    }

    /**
     * Sets the person first name.
     * @param firstName First name.
     */
    public void setFirstName(final @NonNull String firstName)
    {
        this.firstName = firstName;
        setName(this.lastName + ", " + this.firstName);
    }

    /**
     * Returns the default email address.
     * @return Optional default email address.
     */
    public final ServerEmailAddressEntity getDefaultEmailAddress()
    {
        return emailAddresses.stream()
                .filter(ServerEmailAddressEntity::getIsDefaultEmail).findFirst().orElse(null);
    }

    /**
     * Checks if the person has a default email address?
     * @return {@code True} if the person has a default email address, {@code false} otherwise.
     */
    public final boolean hasDefaultEmailAddress()
    {
        return emailAddresses.stream().anyMatch(ServerEmailAddressEntity::getIsDefaultEmail);
    }

    /**
     * Checks if the given email already exist?
     * @param email Email to check.
     * @return {@code True} if it already exist, {@code false} otherwise.
     */
    public final boolean existEmail(final @NonNull String email)
    {
        return emailAddresses.stream()
                .anyMatch(emailAddress -> emailAddress.getEmail().equalsIgnoreCase(email));
    }

    /**
     * Checks if the given email address already exist?
     * @param emailAddress Email address to check.
     * @return {@code True} if it already exist, {@code false} otherwise.
     */
    public final boolean existEmailAddress(final @NonNull ServerEmailAddressEntity emailAddress) // TODO Not sure it is necessary? Provide a test case.
    {
        return emailAddresses.stream()
                .anyMatch(e -> e.equals(emailAddress));
    }

    /**
     * Returns the email address matching the given identifier.
     * @param id Email address identifier.
     * @return Email address if one is matching the given identifier, null otherwise.
     */
    public final ServerEmailAddressEntity getEmailById(final @NonNull UUID id)
    {
        return emailAddresses.stream()
                .filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Retrieves email addresses matching the given {@link AddressType}.
     * @param type Address type.
     * @return List of email addresses.
     */
    public final List<ServerEmailAddressEntity> findEmailAddressByType(final AddressType type)
    {
        return emailAddresses.stream()
                .filter(emailAddress -> emailAddress.getAddressType() == type)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves email addresses matching the given {@link StatusType}.
     * @param status Status type.
     * @return List of email addresses.
     */
    public final List<ServerEmailAddressEntity> findEmailAddressByStatus(final StatusType status)
    {
        return emailAddresses.stream()
                .filter(emailAddress -> emailAddress.getStatusType() == status)
                .collect(Collectors.toList());
    }

    /**
     * Adds an email address.
     * @param emailAddress Email address.
     * @throws EmailAddressException Raised if the email address already belongs to another person!
     */
    public final void addEmailAddress(final @NonNull ServerEmailAddressEntity emailAddress) throws EmailAddressException
    {
        // An email address cannot be shared!
        if (emailAddress.getPerson() != null)
        {
            throw new EmailAddressException(String.format(
                    "Cannot add email address: <%s> to person: <%s> because it already belongs to person: <%s>!",
                    emailAddress.getEmail(),
                    emailAddress.getPerson().getName(),
                    getName()));
        }

        emailAddress.setPerson(this);
        emailAddresses.add(emailAddress);
    }

    /**
     * Returns the default postal address.
     * @return Default postal address.
     */
    public final Optional<ServerPostalAddressEntity> getDefaultPostalAddress()
    {
        return postalAddresses.stream()
                .filter(ServerPostalAddressEntity::getIsDefault).findFirst();
    }

    /**
     * Checks if the person has a default postal address?
     * @return {@code True} if the person has a default postal address, {@code false} otherwise.
     */
    public final boolean hasDefaultPostalAddress()
    {
        return postalAddresses.stream().anyMatch(ServerPostalAddressEntity::getIsDefault);
    }

    /**
     * Checks if a postal address matches the given one exist?
     * @param address Postal address.
     * @return {@code True} if a postal address matches, {@code false} otherwise.
     */
    public final boolean existPostalAddress(final @NotNull ServerPostalAddressEntity address)
    {
        return postalAddresses.stream().anyMatch(e -> e.equals(address));
    }

    /**
     * Retrieves postal addresses matching the given {@link AddressType}.
     * @param type Address type.
     * @return List of postal addresses.
     */
    public final List<ServerPostalAddressEntity> findPostalAddressByType(final AddressType type)
    {
        return postalAddresses.stream()
                .filter(e -> e.getAddressType() == type)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves postal addresses matching the given {@link StatusType}.
     * @param status Status type.
     * @return List of postal addresses.
     */
    public final List<ServerPostalAddressEntity> findPostalAddressByStatus(final StatusType status)
    {
        return postalAddresses.stream()
                .filter(e -> e.getStatusType() == status)
                .collect(Collectors.toList());
    }

    /**
     * Adds an postal address.
     * @param postalAddress Postal address.
     */
    public final void addPostalAddress(final @NonNull ServerPostalAddressEntity postalAddress)
    {
        postalAddress.setPerson(this);
        postalAddresses.add(postalAddress);
    }

    /**
     * Returns the default phone number.
     * @return Optional default phone number.
     */
    public final Optional<ServerPhoneNumberEntity> getDefaultPhoneNumber()
    {
        return phoneNumbers.stream()
                .filter(ServerPhoneNumberEntity::getIsDefault).findFirst();
    }

    /**
     * Checks if the person has a default phone number?
     * @return {@code True} if the person has a default phone number, {@code false} otherwise.
     */
    public final boolean hasDefaultPhoneNumber()
    {
        return phoneNumbers.stream().anyMatch(ServerPhoneNumberEntity::getIsDefault);
    }

    /**
     * Checks if the given phone number (only the number) already exist?
     * @param phoneNumber Phone number to check.
     * @return {@code True} if it already exist, {@code false} otherwise.
     */
    public final boolean existPhoneNumber(final @NonNull String phoneNumber)
    {
        return phoneNumbers.stream()
                .anyMatch(e -> e.getNumber().equalsIgnoreCase(phoneNumber));
    }

    /**
     * Checks if the given phone number already exist?
     * @param phoneNumber Phone number to check.
     * @return {@code True} if it already exist, {@code false} otherwise.
     */
    public final boolean existPhoneNumber(final @NonNull ServerPhoneNumberEntity phoneNumber) // TODO Not sure it is necessary? Provide a test case.
    {
        return phoneNumbers.stream()
                .anyMatch(e -> e.equals(phoneNumber));
    }

    /**
     * Retrieves phone numbers matching the given {@link PhoneNumberType}.
     * @param type Address type.
     * @return List of phone numbers.
     */
    public final List<ServerPhoneNumberEntity> findPhoneNumberByType(final PhoneNumberType type)
    {
        return phoneNumbers.stream()
                .filter(e -> e.getPhoneType() == type)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves phone numbers matching the given {@link StatusType}.
     * @param status Status type.
     * @return List of phone numbers.
     */
    public final List<ServerPhoneNumberEntity> findPhoneNumberByStatus(final StatusType status)
    {
        return phoneNumbers.stream()
                .filter(e -> e.getStatusType() == status)
                .collect(Collectors.toList());
    }

    /**
     * Adds a phone number.
     * @param phoneNumber Phone number.
     */
    public final void addPhoneNumber(final @NonNull ServerPhoneNumberEntity phoneNumber)
    {
        phoneNumber.setPerson(this);
        phoneNumbers.add(phoneNumber);
    }

    /**
     * Removes an email address.
     * @param email Email address to remove.
     */
    public final void removeEmailAddress(final @NonNull ServerEmailAddressEntity email)
    {
        email.setPerson(null);
        emailAddresses.remove(email);
    }
}
