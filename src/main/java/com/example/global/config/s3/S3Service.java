package com.example.global.config.s3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
public class S3Service {
    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${cloudfront.domain}")
    private String cloudFrontDomain;

    public S3Service(
            @Value("${aws.credentials.accessKey}") String accessKey,
            @Value("${aws.credentials.secretKey}") String secretKey,
            @Value("${aws.s3.region}") String region
    ) {
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    public String uploadFile(String key, MultipartFile multipartFile) throws IOException {
        Path tempFile = Files.createTempFile(key, null);
        multipartFile.transferTo(tempFile);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putObjectRequest, tempFile);
        Files.delete(tempFile);

        return String.format("https://%s/%s", cloudFrontDomain, key);
    }
}
