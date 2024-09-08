package org.arpha.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AwsS3Properties.class)
@ConfigurationProperties("aws.services.s3storage")
public record AwsS3Properties(String accessKey, String secretKey, String bucket, String region, long expiration) {

}
