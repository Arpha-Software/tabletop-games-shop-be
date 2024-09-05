package org.arpha.service;

import lombok.RequiredArgsConstructor;
import org.arpha.entity.File;
import org.arpha.exception.FileUploadException;
import org.arpha.property.AwsS3Properties;
import org.arpha.utils.Boxed;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AwsRequestService {

    private final AwsS3Properties awsS3Properties;
    private final S3Presigner s3Presigner;


    public PutObjectRequest getPutObjectRequest(String fileName, MimeType mimeType) {
        return PutObjectRequest
                .builder()
                .bucket(awsS3Properties.bucket())
                .key(fileName)
                .metadata(mimeType.getParameters())
                .build();
    }

    public PutObjectPresignRequest getPutObjectPresignRequest(File file) {
        return PutObjectPresignRequest
                .builder()
                .signatureDuration(Duration.ofMillis(awsS3Properties.expiration()))
                .putObjectRequest(getPutObjectRequest(file.getName(), file.getType()))
                .build();
    }

    public PresignedPutObjectRequest sendS3Request(File file) {
        return Boxed
                .of(file)
                .mapToBoxed(this::getPutObjectPresignRequest)
                .mapToBoxed(s3Presigner::presignPutObject)
                .orElseThrow(() -> new FileUploadException("Unexpected error happened"));

    }
    public GetObjectRequest getObjectRequest(File file) {
        return GetObjectRequest
                .builder()
                .bucket(awsS3Properties.bucket())
                .key(file.getName())
                .build();
    }

    public GetObjectPresignRequest getObjectPresignRequest(File file) {
        return GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMillis(awsS3Properties.expiration()))
                .getObjectRequest(getObjectRequest(file))
                .build();

    }

    public DeleteObjectRequest getDeleteObjectRequest(File file) {
        return DeleteObjectRequest
                .builder()
                .bucket(awsS3Properties.bucket())
                .key(file.getName())
                .build();
    }

}
