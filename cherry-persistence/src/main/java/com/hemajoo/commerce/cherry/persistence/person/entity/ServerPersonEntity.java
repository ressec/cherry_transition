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
import com.hemajoo.commerce.cherry.model.person.exception.PhoneNumberException;
import com.hemajoo.commerce.cherry.model.person.exception.PostalAddressException;
import com.hemajoo.commerce.cherry.model.person.type.AddressType;
import com.hemajoo.commerce.cherry.model.person.type.GenderType;
import com.hemajoo.commerce.cherry.model.person.type.PersonType;
import com.hemajoo.commerce.cherry.model.person.type.PhoneNumberType;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServerBaseEntity;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServerEntity;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @OneToMany(targetEntity = ServerPostalAddressEntity.class, mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServerPostalAddressEntity> postalAddresses = new ArrayList<>();

    /**
     * Phone numbers associated to the person.
     */
    @Getter
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("person")
    @OneToMany(targetEntity = ServerPhoneNumberEntity.class, mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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

    // EMAIL ADDRESS SERVICES

    /**
     * Returns the default email address.
     * @return Default email address, {@code null} otherwise.
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
                .anyMatch(e -> e.getEmail().equalsIgnoreCase(email));
    }

    /**
     * Checks if the given email address already exist?
     * @param emailAddress Email address to check.
     * @return {@code True} if it already exist, {@code false} otherwise.
     */
    public final boolean existEmailAddress(final @NonNull ServerEmailAddressEntity emailAddress)
    {
        return emailAddresses.stream()
                .anyMatch(e -> e.equals(emailAddress));
    }

    /**
     * Returns the email address matching the given identifier.
     * @param id Email address identifier.
     * @return Email address, if one is matching the given identifier, {@code null} otherwise.
     */
    public final ServerEmailAddressEntity getEmailById(final @NonNull UUID id)
    {
        return emailAddresses.stream()
                .filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Retrieves email addresses matching the given {@link AddressType}.
     * @param type Address type.
     * @return List of email addresses matching the given address type.
     */
    public final List<ServerEmailAddressEntity> findEmailAddressByType(final AddressType type)
    {
        return emailAddresses.stream()
                .filter(e -> e.getAddressType() == type)
                .toList();
    }

    /**
     * Retrieves email addresses matching the given {@link StatusType}.
     * @param status Status type.
     * @return List of email addresses matching the given status.
     */
    public final List<ServerEmailAddressEntity> findEmailAddressByStatus(final StatusType status)
    {
        return emailAddresses.stream()
                .filter(e -> e.getStatusType() == status)
                .toList();
    }

    /**
     * Adds an email address.
     * @param emailAddress Email address.
     * @throws EmailAddressException Thrown to indicate the email address already belongs to another person!
     */
    public final void addEmailAddress(final @NonNull ServerEmailAddressEntity emailAddress) throws EmailAddressException
    {
        // An email address cannot be shared!
        if (emailAddress.getPerson() != null)
        {
            throw new EmailAddressException(String.format(
                    "Cannot add email address: '%s' to person: '%s' because it already belongs to person: '%s'!",
                    emailAddress.getEmail(),
                    emailAddress.getPerson().getName(),
                    getName()));
        }

        emailAddress.setPerson(this);
        emailAddresses.add(emailAddress);
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

    // PHONE NUMBER SERVICES

    /**
     * Returns the default phone number.
     * @return Default phone number if one matches, {@code null} otherwise.
     */
    public final ServerPhoneNumberEntity getDefaultPhoneNumber()
    {
        return phoneNumbers.stream()
                .filter(ServerPhoneNumberEntity::getIsDefault).findFirst().orElse(null);
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
     * Checks if the given phone number exist?
     * @param number Phone number to check.
     * @return {@code True} if the given phone number already exist, {@code false} otherwise.
     */
    public final boolean existPhoneNumber(final @NonNull String number)
    {
        return phoneNumbers.stream()
                .anyMatch(element -> element.getNumber().equalsIgnoreCase(number));
    }

    /**
     * Checks if the given phone number already exist?
     * @param phoneNumber Phone number to check.
     * @return {@code True} if the given phone number already exist, {@code false} otherwise.
     */
    public final boolean existPhoneNumber(final @NonNull ServerPhoneNumberEntity phoneNumber)
    {
        return phoneNumbers.stream()
                .anyMatch(e -> e.equals(phoneNumber));
    }

    /**
     * Returns the phone number matching the given identifier.
     * @param phoneNumberId Phone number identifier.
     * @return Phone number, if one is matching the given identifier, {@code null} otherwise.
     */
    public final ServerPhoneNumberEntity getPhoneNumberById(final @NonNull UUID phoneNumberId)
    {
        return phoneNumbers.stream()
                .filter(e -> e.getId().equals(phoneNumberId)).findFirst().orElse(null);
    }

    /**
     * Retrieves a list of phone numbers matching the given {@link PhoneNumberType}.
     * @param phoneNumberType Phone number type.
     * @return List of phone numbers.
     */
    public final List<ServerPhoneNumberEntity> findPhoneNumberByType(final PhoneNumberType phoneNumberType)
    {
        return phoneNumbers.stream()
                .filter(e -> e.getPhoneType() == phoneNumberType)
                .toList();
    }

    /**
     * Retrieves a list of phone numbers matching the given {@link StatusType}.
     * @param status Status type.
     * @return List of phone numbers.
     */
    public final List<ServerPhoneNumberEntity> findPhoneNumberByStatus(final StatusType status)
    {
        return phoneNumbers.stream()
                .filter(e -> e.getStatusType() == status)
                .toList();
    }

    /**
     * Adds a phone number.
     * @param phoneNumber Phone number.
     * @throws PhoneNumberException Thrown to indicate the phone number already belongs to another person!
     */
    public final void addPhoneNumber(final @NonNull ServerPhoneNumberEntity phoneNumber) throws PhoneNumberException
    {
        // A phone number cannot be shared!
        if (phoneNumber.getPerson() != null)
        {
            throw new PhoneNumberException(String.format(
                    "Cannot add phone number: '%s' to person: '%s' because it already belongs to another person: '%s'!",
                    phoneNumber.getNumber(),
                    phoneNumber.getPerson().getName(),
                    this.getName()), HttpStatus.BAD_REQUEST);
        }

        phoneNumber.setPerson(this);
        phoneNumbers.add(phoneNumber);
    }

    /**
     * Removes a phone number.
     * @param phoneNumber Phone number to remove.
     */
    public final void removePhoneNumber(final @NonNull ServerPhoneNumberEntity phoneNumber)
    {
        phoneNumber.setPerson(null);
        phoneNumbers.remove(phoneNumber);
    }

    // POSTAL ADDRESS SERVICES

    /**
     * Returns the default postal address.
     * @return Default postal address.
     */
    public final ServerPostalAddressEntity getDefaultPostalAddress()
    {
        return postalAddresses.stream()
                .filter(ServerPostalAddressEntity::getIsDefault).findFirst().orElse(null);
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
     * Checks if the given postal address already exist?
     * @param postalAddress Postal address to check.
     * @return {@code True} if the postal address already exist, {@code false} otherwise.
     */
    public final boolean existPostalAddress(final @NonNull ServerPostalAddressEntity postalAddress)
    {
        return postalAddresses.stream()
                .anyMatch(e -> e.equals(postalAddress));
    }

    /**
     * Returns the postal address matching the given identifier.
     * @param postalAddressId Postal address identifier.
     * @return Postal address, if one matches the given identifier, {@code null} otherwise.
     */
    public final ServerPostalAddressEntity getPostalAddressById(final @NonNull UUID postalAddressId)
    {
        return postalAddresses.stream()
                .filter(e -> e.getId().equals(postalAddressId)).findFirst().orElse(null);
    }

    /**
     * Retrieves a list of postal addresses matching the given {@link AddressType}.
     * @param type Address type.
     * @return List of postal addresses.
     */
    public final List<ServerPostalAddressEntity> findPostalAddressByType(final AddressType type)
    {
        return postalAddresses.stream()
                .filter(e -> e.getAddressType() == type)
                .toList();
    }

    /**
     * Retrieves a list of postal addresses matching the given {@link StatusType}.
     * @param status Status type.
     * @return List of postal addresses.
     */
    public final List<ServerPostalAddressEntity> findPostalAddressByStatus(final StatusType status)
    {
        return postalAddresses.stream()
                .filter(e -> e.getStatusType() == status)
                .toList();
    }

    /**
     * Adds a postal address.
     * @param postalAddress Postal address.
     * @throws PostalAddressException Thrown to indicate the postal address already belongs to another person!
     */
    public final void addPostalAddress(final @NonNull ServerPostalAddressEntity postalAddress) throws PostalAddressException
    {
        // A postal address cannot be shared!
        if (postalAddress.getPerson() != null)
        {
            throw new PostalAddressException(String.format("Cannot add postal address: '%s' to person: '%s' because it already belongs to person: '%s'!",
                    postalAddress,
                    postalAddress.getPerson().getName(),
                    this.getName()),
                    HttpStatus.BAD_REQUEST);
        }

        postalAddress.setPerson(this);
        postalAddresses.add(postalAddress);
    }

    /**
     * Removes a postal address.
     * @param postalAddress Postal address to remove.
     */
    public final void removePostalAddress(final @NonNull ServerPostalAddressEntity postalAddress)
    {
        postalAddress.setPerson(null);
        postalAddresses.remove(postalAddress);
    }
}
