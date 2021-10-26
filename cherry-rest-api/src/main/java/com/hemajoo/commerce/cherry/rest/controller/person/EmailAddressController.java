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
import com.hemajoo.commerce.cherry.persistence.base.factory.ServerEntityFactory;
import com.hemajoo.commerce.cherry.persistence.person.converter.EmailAddressConverter;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.persistence.person.randomizer.EmailAddressRandomizer;
import com.hemajoo.commerce.cherry.persistence.person.service.EmailAddressServiceCore;
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
     * Server entity factory.
     */
    @Autowired
    private ServerEntityFactory entityFactory;

    /**
     * Returns the total number of email addresses.
     * @return Number of email addresses.
     */
    @ApiOperation(value = "Count the number of email addresses.", notes = "Count the total number of email addresses.")
    @GetMapping("/count")
    public long count()
    {
        return entityFactory.getEmailAddressService().count();
    }

    /**
     * Retrieves an email address given its unique identifier.
     * @param id Email address identifier.
     * @return Email address matching the given identifier.
     */
    @ApiOperation(value = "Retrieve an email address given its identifier.", notes = "Retrieve an email address given its identifier.")
    @GetMapping("/get/{id}")
    public ResponseEntity<ClientEmailAddressEntity> get(
            @ApiParam(value = "Email address identifier", required = true)
            @Valid @ValidEmailAddressId // Handles email id validation automatically, need both annotations!
            @NotNull
            @PathVariable String id)
    {
        ServerEmailAddressEntity serverEmailAddress = entityFactory.getEmailAddressService().findById(UUID.fromString(id));
        return ResponseEntity.ok(EmailAddressConverter.convertServer(serverEmailAddress));
    }

    /**
     * Endpoint service to add a new email address.
     * @param emailAddress Email address to add.
     * @return Response.
     */
    @ApiOperation(value = "Create a new email address."/*,
            notes = "Notes: <i>No need to set the postal address identifier (id) as it is automatically generated. If set, it will be ignored!</i>"*/)
    @PostMapping("/create")
    public ResponseEntity<ClientEmailAddressEntity> create(
            @ApiParam(value = "Email address", required = true)
            @Valid @ValidEmailAddressForCreation @RequestBody ClientEmailAddressEntity emailAddress)
    {
        ServerEmailAddressEntity serverEmailAddress = EmailAddressConverter.convertClient(emailAddress, entityFactory);
        entityFactory.getEmailAddressService().save(serverEmailAddress);

        return ResponseEntity.ok(EmailAddressConverter.convertServer(serverEmailAddress));
    }

    /**
     * Endpoint service to create a random email address for a given person identifier.
     * @param personId Person identifier.
     * @return Response entity containing the randomly generated {@link ClientEmailAddressEntity}.
     */
    @ApiOperation(value = "Create a new random email address for the given person identifier.")
    @PostMapping("/random")
    public ResponseEntity<ClientEmailAddressEntity> random(
            @ApiParam(value = "Person identifier (UUID)", name = "personId", required = true)
            @Valid @ValidPersonId @NotNull @RequestParam String personId)
    {
        ServerEmailAddressEntity serverEmail = EmailAddressRandomizer.generateServer(false);

        ServerPersonEntity person = entityFactory.getPersonService().findById(UUID.fromString(personId));
        serverEmail.setPerson(person);
        entityFactory.getEmailAddressService().save(serverEmail);

        return ResponseEntity.ok(EmailAddressConverter.convertServer(serverEmail));
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
            ServerEmailAddressEntity emailAddress = entityFactory.getEmailAddressService().save(EmailAddressConverter.convertClient(email, entityFactory));
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
    public ResponseEntity<?> delete(
            @ApiParam(value = "Email address identifier", required = true)
            @NotNull @Valid @ValidEmailAddressId @PathVariable String id)
    {
        entityFactory.getEmailAddressService().deleteById(UUID.fromString(id));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Searches for email addresses given criteria.
     * @param search Email address search object.
     * @return List of email identifiers matching the given criteria if some have been found, an empty list otherwise.
     */
    @ApiOperation(value = "Search for email addresses.", notes = "Search for email addresses matching the given predicates. Fill only the fields you want to be taken into account for the search.")
    @GetMapping("/search")
    public ResponseEntity<List<String>> search(final @RequestBody @NonNull SearchEmailAddress search)
    {
        List<ClientEmailAddressEntity> entities = EmailAddressConverter.convertServerList(entityFactory.getEmailAddressService().search(search));

        return ResponseEntity.ok(GenericEntityConverter.toIdList(entities));
    }

    /**
     * Endpoint service to query email addresses given criteria.
     * @param search Email address search object.
     * @return List of client email addresses if some have been found, an empty list otherwise.
     */
    @ApiOperation(value = "Query email addresses", notes = "Returns a list of email addresses matching the given criteria.")
    @GetMapping("/query")
    public ResponseEntity<List<ClientEmailAddressEntity>> query(final @RequestBody @NonNull SearchEmailAddress search)
    {
        return ResponseEntity.ok(EmailAddressConverter.convertServerList(entityFactory.getEmailAddressService().search(search)));
    }
}
