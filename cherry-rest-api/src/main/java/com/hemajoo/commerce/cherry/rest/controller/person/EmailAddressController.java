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

import com.hemajoo.commerce.cherry.model.base.converter.GenericEntityConverter;
import com.hemajoo.commerce.cherry.model.person.entity.ClientEmailAddressEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.model.person.exception.EntityException;
import com.hemajoo.commerce.cherry.model.person.exception.EntityValidationException;
import com.hemajoo.commerce.cherry.model.person.search.SearchEmailAddress;
import com.hemajoo.commerce.cherry.persistence.base.entity.ServiceFactoryPerson;
import com.hemajoo.commerce.cherry.persistence.person.converter.EmailAddressConverter;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.EmailAddressRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.service.EmailAddressServiceCore;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.EmailAddressCheckCreate;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.EmailAddressCheckId;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.EmailAddressCheckUpdate;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.PersonCheckId;
import com.hemajoo.commerce.cherry.persistence.person.validation.engine.EmailAddressValidationEngine;
import com.hemajoo.commerce.cherry.persistence.person.validation.validator.EmailAddressValidatorUpdate;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * REST controller providing service endpoints to manage the email addresses.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Api(tags = "Email Addresses")
@ComponentScan(basePackageClasses = { EmailAddressValidatorUpdate.class, EmailAddressServiceCore.class })
@Validated
@RestController
@RequestMapping("/api/v1/person/email")
public class EmailAddressController
{
    /**
     * Server entity factory.
     */
    @Autowired
    private ServiceFactoryPerson servicePerson;

    /**
     * Email address converter.
     */
    @Autowired
    private EmailAddressConverter converterEmailAddress;

    /**
     * Email address validation engine.
     */
    @Autowired
    private EmailAddressValidationEngine validationEmailAddress;

    /**
     * Service to count the number of email addresses.
     * @return Number of email addresses.
     */
    @ApiOperation(value = "Count email addresses")
    @GetMapping("/count")
    public long count()
    {
        return servicePerson.getEmailAddressService().count();
    }

    /**
     * Service to retrieve an email address.
     * @param id Email address identifier.
     * @return Email address matching the given identifier.
     */
    @ApiOperation(value = "Retrieve an email address")
    @GetMapping("/get/{id}")
    public ResponseEntity<ClientEmailAddressEntity> get(
            @ApiParam(value = "Email address identifier", required = true)
            @Valid @EmailAddressCheckId // Handles email id validation automatically, need both annotations!
            @NotNull
            @PathVariable String id)
    {
        ServerEmailAddressEntity serverEmailAddress = servicePerson.getEmailAddressService().findById(UUID.fromString(id));
        return ResponseEntity.ok(converterEmailAddress.fromServerToClient(serverEmailAddress));
    }

    /**
     * Service to add a new email address.
     * @param emailAddress Email address.
     * @return Newly created email address.
     * @throws EmailAddressException Thrown to indicate an error occurred while trying to create the email address.
     */
    @ApiOperation(value = "Create a new email address")
    @PostMapping("/create")
    public ResponseEntity<ClientEmailAddressEntity> create(
            @ApiParam(value = "Email address", required = true)
            @Valid @EmailAddressCheckUpdate @RequestBody ClientEmailAddressEntity emailAddress) throws EntityException
    {
        ServerEmailAddressEntity serverEmailAddress = converterEmailAddress.fromClientToServer(emailAddress);
        serverEmailAddress = servicePerson.getEmailAddressService().save(serverEmailAddress);

        return ResponseEntity.ok(converterEmailAddress.fromServerToClient(serverEmailAddress));
    }

    /**
     * Service to create a random email address.
     * @param personId Person identifier being the owner of the email address to create.
     * @return Randomly generated email address.
     * @throws EmailAddressException Thrown to indicate an error occurred while trying to create a random email address.
     */
    @ApiOperation(value = "Create a new random email address")
    @PostMapping("/random")
    public ResponseEntity<ClientEmailAddressEntity> random(
            @ApiParam(value = "Person identifier (UUID) being the owner of the new random email address", name = "personId", required = true)
            @Valid @PersonCheckId @NotNull @RequestParam String personId) throws EmailAddressException
    {
        ServerEmailAddressEntity serverEmail = EmailAddressRandomizer.generateServerEntity(false);

        ServerPersonEntity person = servicePerson.getPersonService().findById(UUID.fromString(personId));
        serverEmail.setPerson(person);
        serverEmail = servicePerson.getEmailAddressService().save(serverEmail);

        return ResponseEntity.ok(converterEmailAddress.fromServerToClient(serverEmail));
    }

    /**
     * Service to update an email address.
     * @param email Email address to update.
     * @return Updated email address.
     */
    @ApiOperation(value = "Update an email address"/*, notes = "Update an email address given the new values."*/)
    @PutMapping("/update")
    //@Transactional
    public ResponseEntity<ClientEmailAddressEntity> update(
            @NotNull @Valid @EmailAddressCheckCreate @RequestBody ClientEmailAddressEntity email) throws EntityValidationException
    {
        validationEmailAddress.isValidForUpdate(email);

        ServerEmailAddressEntity source = converterEmailAddress.fromClientToServer(email);
        ServerEmailAddressEntity updated = servicePerson.getEmailAddressService().update(source);
        ClientEmailAddressEntity client = converterEmailAddress.fromServerToClient(updated);

        return ResponseEntity.ok(client);
    }

    /**
     * Service to delete an email address given its identifier.
     * @param id Email address identifier.
     * @return Confirmation message.
     */
    @ApiOperation(value = "Delete an email address")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(
            @ApiParam(value = "Email address identifier (UUID)", required = true)
            @NotNull @Valid @EmailAddressCheckId @PathVariable String id)
    {
        servicePerson.getEmailAddressService().deleteById(UUID.fromString(id));

        return ResponseEntity.ok(String.format("Successfully deleted email address with id: '%s'", id));
    }

    /**
     * Service to search for email addresses given some criteria.
     * @param search Email address specification object.
     * @return List of matching email addresses.
     * @throws EntityValidationException Thrown to indicate an error occurred while trying to search for email addresses.
     */
    @ApiOperation(value = "Search for email addresses", notes = "Search for email addresses matching the given predicates. Fill only the fields to be taken into account.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 404, message = "No email address found matching the given criteria"),
            @ApiResponse(code = 400, message = "Missing or invalid request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @PatchMapping(value = "/search") // PATCH method Because a GET method cannot have a request body!
    public ResponseEntity<List<ClientEmailAddressEntity>> search(final @NotNull SearchEmailAddress search) throws EntityValidationException
    {
        validationEmailAddress.isSearchValid(search);

        List<ClientEmailAddressEntity> clients = servicePerson.getEmailAddressService().search(search)
                .stream()
                .map(element -> converterEmailAddress.fromServerToClient(element))
                .toList();

        return ResponseEntity.ok(clients);
    }

    /**
     * Service to query for email addresses identifiers matching some criteria.
     * @param search Email address specification criteria.
     * @return List of matching email address identifiers.
     * @throws EntityValidationException Thrown to indicate an error occurred while trying to query for email addresses.
     */
    @ApiOperation(value = "Query email addresses", notes = "Returns a list of email addresses matching the given criteria.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful query"),
            @ApiResponse(code = 404, message = "No email address found matching the given criteria"),
            @ApiResponse(code = 400, message = "Missing or invalid request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @GetMapping("/query")
    public ResponseEntity<List<String>> query(final @NotNull SearchEmailAddress search) throws EntityValidationException
    {
        validationEmailAddress.isSearchValid(search);

        List<ClientEmailAddressEntity> clients = servicePerson.getEmailAddressService().search(search)
                .stream()
                .map(element -> converterEmailAddress.fromServerToClient(element))
                .toList();

        return ResponseEntity.ok(GenericEntityConverter.toIdList(clients));
    }
}
