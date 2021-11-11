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

import com.hemajoo.commerce.cherry.model.person.entity.ClientPersonEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.persistence.base.factory.ServerEntityFactory;
import com.hemajoo.commerce.cherry.persistence.person.converter.PersonConverter;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.PersonRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.ValidPersonId;
import com.hemajoo.commerce.cherry.persistence.person.validation.engine.EmailAddressValidationEngine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Controller providing service endpoints to manage the persons.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Api(tags = "Persons")
//@ComponentScan(basePackageClasses = { EmailAddressValidatorForUpdate.class, EmailAddressServiceCore.class })
@Validated
@RestController
@RequestMapping("/api/v1/person")
public class PersonController
{
    /**
     * Server entity factory.
     */
    @Autowired
    private ServerEntityFactory entityFactory;

    /**
     * Person converter.
     */
    @Autowired
    private PersonConverter personConverter;

    /**
     * Email address validation engine.
     */
    @Autowired
    private EmailAddressValidationEngine emailAddressRuleEngine;

    /**
     * Service to count the number of persons.
     * @return Number of persons.
     */
    @ApiOperation(value = "Count the number of persons")
    @GetMapping("/count")
    public long count()
    {
        return entityFactory.getServices().getPersonService().count();
    }

    /**
     * Service to retrieve a person.
     * @param id Email address identifier.
     * @return Email address matching the given identifier.
     */
    @ApiOperation(value = "Retrieve an email address")
    @GetMapping("/get/{id}")
    public ResponseEntity<ClientPersonEntity> get(
            @ApiParam(value = "Person identifier", required = true)
            @Valid @ValidPersonId // Handles person id validation automatically, need both annotations!
            @NotNull
            @PathVariable String id)
    {
        ServerPersonEntity person = entityFactory.getServices().getPersonService().findById(UUID.fromString(id));
        return ResponseEntity.ok(personConverter.fromServerToClient(person));
    }

//    /**
//     * Service to add a new person.
//     * @param person Email address.
//     * @return Newly created email address.
//     * @throws EmailAddressException Thrown to indicate an error occurred while trying to create the email address.
//     */
//    @ApiOperation(value = "Create a new email address")
//    @PostMapping("/create")
//    public ResponseEntity<ClientPersonEntity> create(
//            @ApiParam(value = "Person", required = true)
//            @Valid @ValidPersonForCreation @RequestBody ClientPersonEntity person) throws PersonException
//    {
//        ServerPersonEntity serverPerson = personConverter.fromClientToServer(person);
//        serverPerson = entityFactory.getServices().getPersonService().save(serverPerson);
//
//        return ResponseEntity.ok(personConverter.fromServerToClient(serverPerson));
//    }

    /**
     * Service to create a random person.
     * @return Randomly generated person.
     * @throws EmailAddressException Thrown to indicate an error occurred while trying to create a random email address.
     */
    @ApiOperation(value = "Create a new random person")
    @PostMapping("/random")
    public ResponseEntity<ClientPersonEntity> random() throws EmailAddressException
    {
        ServerPersonEntity person = PersonRandomizer.generateServerEntity(false);
        person = entityFactory.getServices().getPersonService().save(person);

        return ResponseEntity.ok(personConverter.fromServerToClient(person));
    }

//    /**
//     * Service to update a person.
//     * @param person Person to update.
//     * @return Updated person.
//     */
//    @ApiOperation(value = "Update a person")
//    @PutMapping("/update")
//    public ResponseEntity<ClientPersonEntity> update(
//            @NotNull @Valid @ValidPersonForUpdate @RequestBody ClientPersonEntity person) throws PersonException
//    {
////        personRuleEngine.validateForUpdate(person);
//
//        ServerPersonEntity updated = entityFactory.getServices().getPersonService().update(personConverter.fromClientToServer(person));
//        ClientPersonEntity client = personConverter.fromServerToClient(updated);
//
//        return ResponseEntity.ok(client);
//    }

    /**
     * Service to delete a person given its identifier.
     * @param id Person identifier.
     * @return Confirmation message.
     */
    @ApiOperation(value = "Delete a person")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(
            @ApiParam(value = "Person identifier (UUID)", required = true)
            @NotNull @Valid @ValidPersonId @PathVariable String id)
    {
        entityFactory.getServices().getPersonService().deleteById(UUID.fromString(id));

        return ResponseEntity.ok(String.format("Person id: '%s' has been deleted successfully!", id));
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
//    @PatchMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<ClientEmailAddressEntity>> search(final @RequestBody @NotNull SearchEmailAddress search) throws EmailAddressException
//    {
//        EmailAddressValidationEngine.isSearchValid(search);
//
//        List<ClientEmailAddressEntity> clients = entityFactory.getServices().getEmailAddressService().search(search)
//                .stream()
//                .map(element -> converter.fromServerToClient(element))
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
//        List<ClientEmailAddressEntity> clients = entityFactory.getServices().getEmailAddressService().search(search)
//                .stream()
//                .map(element -> converter.fromServerToClient(element))
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(GenericEntityConverter.toIdList(clients));
//    }
}
