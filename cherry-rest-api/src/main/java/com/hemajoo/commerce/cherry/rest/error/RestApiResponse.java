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
package com.hemajoo.commerce.cherry.rest.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * A response object following a call to a REST API.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class RestApiResponse
{
    /**
     * API call timestamp (UTC) of the response generation.
     */
    @Getter
    @JsonProperty("timestamp")
    private final String timestamp;

    /**
     * API call duration (on backend side).
     */
    @Getter
    @JsonProperty("duration")
    private String duration;

    /**
     * API call http status code.
     */
    @Getter
    private final HttpStatus status;

    /**
     * API call response body.
     */
    @Getter
    @JsonProperty("body")
    private final Object body;

    /**
     * API call errors.
     */
    @Getter
    @JsonProperty("errors")
    private final List<String> errors = new ArrayList<>();

    @Builder(setterPrefix = "with")
    public RestApiResponse(final HttpStatus status, final Object body, final String error)
    {
        ZonedDateTime zTime = ZonedDateTime.now().withZoneSameInstant( ZoneId.of("UTC"));
        this.timestamp = zTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        this.body = body;
        this.status = status;
        if (error != null)
        {
            addError(error);
        }
    }

    /**
     * Returns a successful API response.
     * @return Successful API response.
     */
    public static RestApiResponse ok()
    {
        return RestApiResponse.builder()
                .withStatus(HttpStatus.OK)
                .build();
    }

    /**
     * Returns a successful API response.
     * @param body API response body.
     * @return Successful API response.
     */
    public static RestApiResponse ok(final @NonNull Object body)
    {
        return RestApiResponse.builder()
                .withStatus(HttpStatus.OK)
                .withBody(body)
                .build();
    }

    /**
     * Returns an un-successful API response.
     * @param status Response http status code.
     * @return Un-successful API response.
     */
    public static RestApiResponse ko(final HttpStatus status)
    {
        return RestApiResponse.builder()
                .withStatus(status)
                .build();
    }

    /**
     * Returns an un-successful API response.
     * @param status Response http status code.
     * @param error Response error message.
     * @return Un-successful API response.
     */
    public static RestApiResponse ko(final HttpStatus status, final @NonNull String error)
    {
        return RestApiResponse.builder()
                .withStatus(status)
                .withError(error)
                .build();
    }

    /**
     * Returns an un-successful API response.
     * @param status Response http status code.
     * @param errors List of errors.
     * @return Un-successful API response.
     */
    public static RestApiResponse ko(final HttpStatus status, final List<String> errors)
    {
        RestApiResponse response = RestApiResponse.ko(status);
        for (String error : errors)
        {
            response.addError(error);
        }

        return response;
    }

    /**
     * Adds an error message.
     * @param error Error message.
     */
    public void addError(final @NonNull String error)
    {
        errors.add(error);
    }

    /**
     * Returns the response entity for this REST API response.
     * @return Response entity.
     */
    public final ResponseEntity<RestApiResponse> getEntity()
    {
        return ResponseEntity.status(this.getStatus()).body(this);
    }
}

