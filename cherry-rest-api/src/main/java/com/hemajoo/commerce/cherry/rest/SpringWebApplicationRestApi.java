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
package com.hemajoo.commerce.cherry.rest;

import com.hemajoo.commerce.cherry.rest.config.PersistenceConfiguration;
import internal.org.springframework.content.s3.boot.autoconfigure.S3ContentAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * The {@code Cherry}'s REST APIs {@code Spring} (server) application.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Import({ PersistenceConfiguration.class })
@SpringBootApplication(exclude = { S3ContentAutoConfiguration.class })
public class SpringWebApplicationRestApi implements CommandLineRunner
{
    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(SpringWebApplicationRestApi.class.getName());

    @Autowired
    private ApplicationContext appContext;

    public static void main(String[] args)
    {
        SpringApplication.run(SpringWebApplicationRestApi.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        String[] beans = appContext.getBeanDefinitionNames();
        Arrays.sort(beans);
//        for (String bean : beans)
//        {
//            LOG.log(Level.INFO, bean);
//        }
    }
}
