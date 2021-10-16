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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * An error response returned when a REST API endpoint call has failed.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class ErrorResponse
{
    /**
     * API call timestamp (UTC) of the response generation.
     */
    @Getter
    @JsonProperty("timestamp")
    private final String timestamp;

    /**
     * API call http status code.
     */
    @Getter
    private final HttpStatus status;

    /**
     * Errors.
     */
    @Getter
    @JsonProperty("errors")
    private final List<String> errors = new ArrayList<>();

    @Builder(setterPrefix = "with")
    public ErrorResponse(final HttpStatus status, final String error)
    {
        ZonedDateTime zTime = ZonedDateTime.now().withZoneSameInstant( ZoneId.of("UTC"));
        this.timestamp = zTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        this.status = status;
        if (error != null)
        {
            addError(error);
        }
    }

    /**
     * Returns an error response.
     * @param status Http status code.
     * @return Error response.
     */
    public static ErrorResponse ko(final HttpStatus status)
    {
        return ErrorResponse.builder()
                .withStatus(status)
                .build();
    }

    /**
     * Returns an error response.
     * @param status Http status code.
     * @param error Error message.
     * @return Error response.
     */
    public static ErrorResponse ko(final HttpStatus status, final @NonNull String error)
    {
        return ErrorResponse.builder()
                .withStatus(status)
                .withError(error)
                .build();
    }

    /**
     * Returns an error response.
     * @param status Http status code.
     * @param errors List of errors.
     * @return Error response.
     */
    public static ErrorResponse ko(final HttpStatus status, final List<String> errors)
    {
        ErrorResponse response = ErrorResponse.ko(status);
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
}

