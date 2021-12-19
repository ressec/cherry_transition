/*
 * (C) Copyright Hemajoo Systems Inc. 2021 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Hemajoo Inc. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Hemajoo Inc. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Hemajoo Systems Inc.
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.commerce.cherry.rest.config;

import com.hemajoo.commerce.cherry.model.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.persistence.document.content.ProxyContentStore;
import com.hemajoo.commerce.cherry.persistence.document.entity.ServerDocumentEntity;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
public class DocumentHelper
{
    @Autowired
    private ProxyContentStore proxyStore;

    public void saveAs(final @NonNull ServerDocumentEntity document, final @NonNull String outputPath) throws DocumentException
    {
        try
        {
            String outputPathname = document.getOutputFilename(outputPath);

//            if (document.getDocumentType().isImage())
//            {
//                generateImageFile(store.getContent(document), outputPathname);
//            }
//            else
//            {
                generateApplicationFile(proxyStore.getStore().getContent(document), outputPathname);
//            }
        }
        catch (IOException e)
        {
            throw new DocumentException(e.getMessage());
        }
    }

    private void generateImageFile(final @NonNull InputStream is, final @NonNull String outputPathName) throws IOException
    {
        BufferedImage bf = ImageIO.read(is);
        ImageIO.write(bf, "png", new File(outputPathName));
    }

    private void generateApplicationFile(final @NonNull InputStream stream, final @NonNull String outputPathname) throws IOException
    {
        File targetFile = new File(outputPathname);
        FileUtils.copyInputStreamToFile(stream, targetFile);
    }
}
