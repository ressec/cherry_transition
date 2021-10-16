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
package com.hemajoo.commerce.cherry.rest.service.schedule;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Environment scheduler is a scheduled service used to dump the environment the Spring server is using..
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Configuration
@EnableScheduling
public class EnvironmentScheduler
{
    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(EnvironmentScheduler.class.getName());

    /**
     * ...
     */
    @Getter
    @Value("${spring.jpa.hibernate.database-platform}")
    private String hibernateDialect;

    /**
     * Initializes the scheduler.
     */
    private void initialize()
    {
//        if (referenceTimeScheduleTaskDelayInMinutes == -2)
//        {
//            try
//            {
//                referenceTimeScheduleTaskDelayInMinutes = Integer.parseInt(ealService.getEalServiceConfig().getTimeScheduleTaskDelay());
//                if (referenceTimeScheduleTaskDelayInMinutes != -1)
//                {
//                    timeScheduleTaskDelayInMinutes = referenceTimeScheduleTaskDelayInMinutes;
//                }
//            }
//            catch (NumberFormatException e)
//            {
//                referenceTimeScheduleTaskDelayInMinutes = -1; // Not activated!
//            }
//        }
    }

    /**
     * Scheduled service used to dump the current environment..
     */
    @Scheduled(cron = "*/30 * * * * *") // Every 30 seconds
    public void scheduleDumpInfo()
    {
        initialize();

        LOG.log(Level.INFO, () -> String.format("Property key: %s, value: %s", "spring.jpa.hibernate.database-platform", hibernateDialect));
    }
}
