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
import com.hemajoo.commerce.cherry.model.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.model.person.entity.ClientEmailAddressEntity;
import com.hemajoo.commerce.cherry.model.person.exception.EmailAddressException;
import com.hemajoo.commerce.cherry.model.person.search.SearchEmailAddress;
import com.hemajoo.commerce.cherry.persistence.person.converter.EmailAddressConverter;
import com.hemajoo.commerce.cherry.persistence.person.entity.EmailAddressServerEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.EmailAddressRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.service.EmailAddressService;
import com.hemajoo.commerce.cherry.persistence.person.service.EmailAddressServiceCore;
import com.hemajoo.commerce.cherry.persistence.person.service.PersonService;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.ValidEmailAddressForCreation;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.ValidEmailAddressForUpdate;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.ValidEmailAddressId;
import com.hemajoo.commerce.cherry.persistence.person.validation.constraint.ValidPersonId;
import com.hemajoo.commerce.cherry.persistence.person.validation.validator.EmailAddressValidatorForUpdate;
import com.hemajoo.commerce.cherry.rest.error.RestApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * A {@code REST API} controller providing endpoints to manage email addresses.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Api(tags = "Email Addresses")
@ComponentScan(basePackageClasses = { EmailAddressValidatorForUpdate.class, EmailAddressServiceCore.class })
//@GroupSequence( { EmailAddressController.class, BasicValidation.class, ExtendedValidation.class } )
@Validated
@RestController
@RequestMapping("/api/v1/person/email")
public class EmailAddressController
{
    /**
     * Person persistence service.
     */
    @Autowired
    private PersonService personService;

    /**
     * Email address persistence service.
     */
    @Autowired
    private EmailAddressService emailAddressService;

//    /**
//     * Email address validator.
//     */
//    @Autowired
//    private EmailAddressValidatorForUpdate emailAddressValidator;

    /**
     * {@code REST API} service to retrieve the total number of email addresses.
     * @return Number of email addresses.
     */
    @ApiOperation(value = "Count the number of email addresses.", notes = "Count the total number of email addresses.")
    @GetMapping("/count")
    public long count()
    {
        return emailAddressService.count();
    }

    /**
     * Retrieves an email address given its unique identifier.
     * @param id Email address identifier.
     * @return Email address matching the given identifier.
     */
    @ApiOperation(value = "Retrieve an email address given its identifier.", notes = "Retrieve an email address given its identifier.")
    @GetMapping("/get/{id}")
    public ResponseEntity<ClientEmailAddressEntity> get(
            @ApiParam(value = "Email address identifier", required = true, example = "356fb9b0-61c8-11de-99e1-4b55c7f2e1b5")
            @Valid @ValidEmailAddressId // Handles email id validation automatically
            @NotNull
            @PathVariable String id)
    {
        EmailAddressServerEntity serverEmailAddress = emailAddressService.findById(UUID.fromString(id));
        return ResponseEntity.ok(EmailAddressConverter.convertPersistence(serverEmailAddress));
    }

    /**
     * Endpoint service to add a new email address.
     * @param emailAddress Email address to add.
     * @return Response.
     */
    @ApiOperation(value = "Create a new email address.",
            notes = "Notes: <i>No need to set the postal address identifier (id) as it is automatically generated. If set, it will be ignored!</i>")
    @PostMapping("/create")
    public ResponseEntity<ClientEmailAddressEntity> create(
            @ApiParam(value = "Email address", required = true)
            @Valid @ValidEmailAddressForCreation @RequestBody ClientEmailAddressEntity emailAddress)
    {
        EmailAddressServerEntity serverEmailAddress = EmailAddressConverter.convertClient(emailAddress);
        emailAddressService.save(serverEmailAddress);

        return ResponseEntity.ok(EmailAddressConverter.convertPersistence(serverEmailAddress));
    }

    /**
     * Endpoint service to create a random email address for a given person identifier.
     * @param personId Person identifier.
     * @return Response entity containing the randomly generated {@link ClientEmailAddressEntity}.
     */
    @ApiOperation(value = "Create a new random email address for the given person identifier.")
    @PostMapping("/random")
    public ResponseEntity<ClientEmailAddressEntity> random(
            @ApiParam(value = "Person identifier (UUID)", name = "personId", required = true, example = "523cd226-49e4-4034-85dd-d0768af295da")
            @Valid @ValidPersonId @NotNull @RequestParam String personId)
    {
        EmailAddressServerEntity serverEmail = EmailAddressRandomizer.generatePersistent(false);

        ServerPersonEntity person = personService.findById(UUID.fromString(personId));
        serverEmail.setPerson(person);
        emailAddressService.save(serverEmail);

        return ResponseEntity.ok(EmailAddressConverter.convertPersistence(serverEmail));
    }

    /**
     * Endpoint service to update an email address.
     * @param email Email address to update.
     * @return Response.
     */
    @ApiOperation(value = "Update an email address.", notes = "Update an email address given the new values.")
    @PutMapping("/update")
    @Transactional
    public ResponseEntity<RestApiResponse> update(@Valid @ValidEmailAddressForUpdate @RequestBody ClientEmailAddressEntity email)
    {
        RestApiResponse response;

        // TODO See JavaVers (https://www.baeldung.com/javers)
        // Should avoid having to manipulate all fields one by one!
//        ChangeDetector detector = element.hasChanged(dbEmailAddress);
//        if (detector.hasChanged())
//        {
//            element = detector.update(EmailAddressEntity.class, email);
//        }

        try
        {
            EmailAddressServerEntity emailAddress = emailAddressService.save(EmailAddressConverter.convertClient(email));
            response = RestApiResponse.ok();
//            response = RestApiResponse.ok(String.format("Successfully updated email address with id: %s", emailAddress.getId().toString()));
        }
        catch (EmailAddressException | DocumentException e)
        {
            response = RestApiResponse.ko(e.getStatus(), e.getMessage());
        }

        return response.getEntity();
    }

    /**
     * Endpoint service to delete an email address.
     * @param id Email address identifier.
     * @return Response.
     */
    @ApiOperation(value = "Delete an email address.", notes = "Delete an email address given its identifier.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RestApiResponse> delete(
            @ApiParam(value = "Email address identifier", required = true, example = "523cd226-49e4-4034-85dd-d0768af29512")
            @NotNull @Valid @ValidEmailAddressId @PathVariable String id)
    {
        RestApiResponse response;

        try
        {
            emailAddressService.deleteById(UUID.fromString(id));
            response = RestApiResponse.ok();
        }
        catch (Exception e)
        {
            response = RestApiResponse.ko(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return response.getEntity();
    }

    /**
     * Endpoint service to retrieve all email addresses.
     * @return Response.
     */
    @ApiOperation(value = "Find all email addresses.", notes = "Find all email addresses.")
    @GetMapping("/findAll")
    public List<ClientEmailAddressEntity> findAll()
    {

        // TODO We should only return a list of the email address ids!

//        RestApiResponse response = RestApiResponse.ok();
//        ResponseEntity<RestApiResponse> entity = response.getEntity();

        List<ClientEmailAddressEntity> list = EmailAddressConverter.convertPersistenceList(emailAddressService.findAll());

        return list;
    }

    /**
     * Searches for email addresses given criteria.
     * @param search Email address search object.
     * @return List of email identifiers matching the given criteria.
     */
    @ApiOperation(value = "Search for email addresses.", notes = "Search for email addresses matching the given predicates. Fill only the fields you want to be taken into account for the search.")
    @GetMapping("/search")
    public ResponseEntity<List<String>> search(final @NonNull SearchEmailAddress search)
    {
        List<ClientEmailAddressEntity> entities = EmailAddressConverter.convertPersistenceList(emailAddressService.search(search));
        List<String> ids = GenericEntityConverter.toIdList(entities);

        return ResponseEntity.ok(ids);
    }

    /**
     * Endpoint service to query email addresses given criteria.
     * @param search Email address search object.
     * @return Response.
     */
    @ApiOperation(value = "Query email addresses", notes = "Query email addresses matching the given predicates.")
    @GetMapping("/query")
    public ResponseEntity<RestApiResponse> query(final @NonNull SearchEmailAddress search)
    {
        RestApiResponse response;

        try
        {
            response = RestApiResponse.ok(EmailAddressConverter.convertPersistenceList(emailAddressService.search(search)));
        }
        catch (Exception e)
        {
            response = RestApiResponse.ko(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return response.getEntity();
    }
}
