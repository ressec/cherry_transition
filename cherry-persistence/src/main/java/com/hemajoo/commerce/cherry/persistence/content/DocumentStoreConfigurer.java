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
package com.hemajoo.commerce.cherry.persistence.content;

import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import lombok.NonNull;
import org.springframework.content.fs.config.FilesystemStoreConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;

import java.io.File;

/**
 * Configurer for the content store.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Configuration
public class DocumentStoreConfigurer
{
    @Bean
    public FilesystemStoreConfigurer configurer()
    {
        return new FilesystemStoreConfigurer()
        {
            @Override
            public void configureFilesystemStoreConverters(ConverterRegistry registry)
            {
                registry.addConverter(new Converter<ServerDocumentEntity, String>()
                {
                    @Override
                    public String convert(final @NonNull ServerDocumentEntity document)
                    {
//                        return File.separator + document.getOwner().getId().toString() + File.separator + document.getContentId();
                        return File.separator + document.getContentId();
                    }
                });
            }
        };
    }
}
