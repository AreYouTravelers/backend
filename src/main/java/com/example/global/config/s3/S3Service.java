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
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

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
        // 임시 파일 생성 시 유효한 접두사와 접미사를 설정합니다.
        String prefix = "temp-";
        String suffix = "-" + multipartFile.getOriginalFilename();

        // 임시 파일 생성
        Path tempFile = Files.createTempFile(prefix, suffix);
        multipartFile.transferTo(tempFile);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key) // S3에 저장될 파일의 경로
                .build();

        // S3에 파일 업로드
        PutObjectResponse response = s3Client.putObject(putObjectRequest, tempFile);
        log.info("S3 Upload Response: {}", response);

        // 임시 파일 삭제
        Files.delete(tempFile);

        return String.format("https://%s/%s", cloudFrontDomain, key);
    }
}
