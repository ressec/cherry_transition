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
package com.hemajoo.commerce.cherry.rest.controller.person;

import com.hemajoo.commerce.cherry.model.person.entity.ClientPhoneNumber;
import com.hemajoo.commerce.cherry.model.person.entity.ClientPhoneNumberEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EntityValidationException;
import com.hemajoo.commerce.cherry.model.person.exception.PhoneNumberException;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServiceFactoryPerson;
import com.hemajoo.commerce.cherry.persistence.person.converter.PhoneNumberConverter;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPhoneNumberEntity;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.PhoneNumberRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.service.PhoneNumberServiceCore;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.PersonCheckId;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.PhoneNumberCheckCreate;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.PhoneNumberCheckId;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.PhoneNumberCheckUpdate;
import com.hemajoo.commerce.cherry.persistence.person.validation.engine.PhoneNumberValidationEngine;
import com.hemajoo.commerce.cherry.persistence.person.validation.validator.PhoneNumberValidatorUpdate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * {@code REST} controller providing service endpoints to manage phone numbers.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Api(tags = "Phone Numbers")
@ComponentScan(basePackageClasses = { PhoneNumberValidatorUpdate.class, PhoneNumberServiceCore.class })
@Validated
@RestController
@RequestMapping("/api/v1/person/phone")
public class PhoneNumberController
{
    /**
     * Server entity factory.
     */
    @Autowired
    private ServiceFactoryPerson servicePerson;

    /**
     * Phone number converter.
     */
    @Autowired
    private PhoneNumberConverter converterPhoneNumber;

    /**
     * Phone number validation engine.
     */
    @Autowired
    private PhoneNumberValidationEngine validation;

    /**
     * Service to count the number of phone numbers.
     * @return Number of phone numbers.
     */
    @ApiOperation(value = "Count phone numbers")
    @GetMapping("/count")
    public long count()
    {
        return servicePerson.getPhoneNumberService().count();
    }

    /**
     * Service to retrieve a phone number.
     * @param phoneNumberId Phone number identifier.
     * @return Phone number matching the given identifier.
     */
    @ApiOperation(value = "Retrieve a phone number")
    @GetMapping(value = "/get/{phoneNumberId}")
    public ResponseEntity<ClientPhoneNumberEntity> get(
            @ApiParam(value = "Phone number identifier", required = true)
            @Valid @PhoneNumberCheckId // Handles phone number id validation automatically, need both annotations!
            @NotNull
            @PathVariable String phoneNumberId)
    {
        ServerPhoneNumberEntity serverPhoneNumber = servicePerson.getPhoneNumberService().findById(UUID.fromString(phoneNumberId));
        return ResponseEntity.ok(converterPhoneNumber.fromServerToClient(serverPhoneNumber));
    }

    /**
     * Service to add a new phone number.
     * @param phoneNumber Phone number.
     * @return Newly created phone number.
     * @throws PhoneNumberException Thrown to indicate an error occurred while trying to create the phone number.
     */
    @ApiOperation(value = "Create a new phone number")
    @PostMapping("/create")
    public ResponseEntity<ClientPhoneNumberEntity> create(
            @ApiParam(value = "Phone number", required = true)
            @Valid @PhoneNumberCheckCreate @RequestBody ClientPhoneNumberEntity phoneNumber) throws PhoneNumberException
    {
        ServerPhoneNumberEntity serverPhoneNumber = converterPhoneNumber.fromClientToServer(phoneNumber);
        serverPhoneNumber = servicePerson.getPhoneNumberService().save(serverPhoneNumber);

        return ResponseEntity.ok(converterPhoneNumber.fromServerToClient(serverPhoneNumber));
    }

    /**
     * Service to create a random phone number.
     * @param personId Person identifier being the owner of the phone number to create.
     * @return Randomly generated phone number.
     * @throws PhoneNumberException Thrown to indicate an error occurred while trying to create a random phone number.
     */
    @ApiOperation(value = "Create a new random phone number")
    @PostMapping("/random")
    public ResponseEntity<ClientPhoneNumberEntity> random(
            @ApiParam(value = "Person identifier (UUID) being the owner of the new random phone number", name = "personId", required = true)
            @Valid @PersonCheckId @NotNull @RequestParam String personId) throws PhoneNumberException
    {
        ServerPhoneNumberEntity serverPhoneNumber = PhoneNumberRandomizer.generateServerEntity(false);

        ServerPersonEntity person = servicePerson.getPersonService().findById(UUID.fromString(personId));
        serverPhoneNumber.setPerson(person);
        serverPhoneNumber = servicePerson.getPhoneNumberService().save(serverPhoneNumber);

        return ResponseEntity.ok(converterPhoneNumber.fromServerToClient(serverPhoneNumber));
    }

    /**
     * Service to update a phone number.
     * @param phoneNumber Phone number to update.
     * @return Updated phone number.
     */
    @ApiOperation(value = "Update a phone number")
    @PutMapping("/update")
    public ResponseEntity<ClientPhoneNumber> update(
            @NotNull @Valid @PhoneNumberCheckUpdate @RequestBody ClientPhoneNumberEntity phoneNumber) throws EntityValidationException
    {
        validation.isValidForUpdate(phoneNumber);

        ServerPhoneNumberEntity source = converterPhoneNumber.fromClientToServer(phoneNumber);
        ServerPhoneNumberEntity updated = servicePerson.getPhoneNumberService().save(source);
        ClientPhoneNumberEntity client = converterPhoneNumber.fromServerToClient(updated);

        return ResponseEntity.ok(client);
    }

    /**
     * Service to delete a phone number given its identifier.
     * @param phoneNumberId Phone number identifier.
     * @return Confirmation message.
     */
    @ApiOperation(value = "Delete a phone number")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(
            @ApiParam(value = "phone number identifier (UUID)", required = true)
            @NotNull @Valid @PhoneNumberCheckId @PathVariable String phoneNumberId)
    {
        servicePerson.getPhoneNumberService().deleteById(UUID.fromString(phoneNumberId));

        return ResponseEntity.ok(String.format("Phone number id: '%s' has been deleted successfully!", phoneNumberId));
    }

//    /**
//     * Service to search for email addresses given some criteria.
//     * @param search Email address specification object.
//     * @return List of matching email addresses.
//     * @throws EmailAddressException Thrown to indicate an error occurred while trying to search for email addresses.
//     */
//    @ApiOperation(value = "Search for email addresses", notes = "Search for email addresses matching the given predicates. Fill only the fields to be taken into account.")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successful operation"),
//            @ApiResponse(code = 404, message = "No email address found matching the given criteria"),
//            @ApiResponse(code = 400, message = "Missing or invalid request"),
//            @ApiResponse(code = 500, message = "Internal server error")})
//    @PatchMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) // PATCH method Because a GET method cannot have a request body!
//    public ResponseEntity<List<ClientEmailAddressEntity>> search(final @RequestBody @NotNull SearchEmailAddress search) throws EmailAddressException
//    {
//        EmailAddressValidationEngine.isSearchValid(search);
//
//        List<ClientEmailAddressEntity> clients = servicePerson.getEmailAddressService().search(search)
//                .stream()
//                .map(element -> converterEmailAddress.fromServerToClient(element))
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(clients);
//    }
//
//    /**
//     * Service to query for email addresses identifiers matching some criteria.
//     * @param search Email address specification criteria.
//     * @return List of matching email address identifiers.
//     * @throws EmailAddressException Thrown to indicate an error occurred while trying to query for email addresses.
//     */
//    @ApiOperation(value = "Query email addresses", notes = "Returns a list of email addresses matching the given criteria.")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successful query"),
//            @ApiResponse(code = 404, message = "No email address found matching the given criteria"),
//            @ApiResponse(code = 400, message = "Missing or invalid request"),
//            @ApiResponse(code = 500, message = "Internal server error")})
//    @GetMapping("/query")
//    public ResponseEntity<List<String>> query(final @NotNull SearchEmailAddress search) throws EmailAddressException
//    {
//        EmailAddressValidationEngine.isSearchValid(search);
//
//        List<ClientEmailAddressEntity> clients = servicePerson.getEmailAddressService().search(search)
//                .stream()
//                .map(element -> converterEmailAddress.fromServerToClient(element))
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(GenericEntityConverter.toIdList(clients));
//    }
}
