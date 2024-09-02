package com.task.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import java.net.URI;

@Service
@Slf4j
public class S3Service {
    private final S3Client s3Client;
    private final String bucketName;

    public S3Service(
            @Value("${cloud.yandex.s3.access-key}") String accessKey,
            @Value("${cloud.yandex.s3.secret-key}") String secretKey,
            @Value("${cloud.yandex.s3.bucket-name}") String bucketName) {

        this.bucketName = bucketName;

        this.s3Client = S3Client.builder()
                .endpointOverride(URI.create("https://storage.yandexcloud.net"))
                .region(Region.of("ru-central1"))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    public String generateUploadUrl(String fileName) {
        return String.format("https://%s.%s/%s", bucketName, "storage.yandexcloud.net", fileName);
    }

    public String generateDownloadUrl(String fileName) {
        return String.format("https://%s.%s/%s", bucketName, "storage.yandexcloud.net", fileName);
    }
}