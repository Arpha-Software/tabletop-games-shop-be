package org.arpha.configuration;


import lombok.RequiredArgsConstructor;
import org.arpha.property.AwsS3Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@RequiredArgsConstructor
public class AwsS3Config {

    private final AwsS3Properties awsS3Properties;

    @Bean
    public S3Client s3client() {
        AwsCredentials awsCredentials = AwsBasicCredentials.create(awsS3Properties.accessKey(), awsS3Properties.secretKey());
        return S3Client.builder()
                .overrideConfiguration(ClientOverrideConfiguration.builder().build())
                .credentialsProvider(() -> awsCredentials)
                .region(Region.of(awsS3Properties.region()))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.create();
    }

}
