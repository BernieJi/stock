package com.boin.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class StorageConfig {

    @Value("${google.cloud.storage.credentials.file}")
    private String credentialsFilePath;

    @Bean
    public Storage storage() throws IOException {

        ClassPathResource resource = new ClassPathResource(credentialsFilePath);
        if (!resource.exists()) {
            throw new IOException("Credentials file not found: " + credentialsFilePath);
        }

        InputStream credentialsStream = resource.getInputStream();
        return StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(credentialsStream))
                .build()
                .getService();
    }
}
