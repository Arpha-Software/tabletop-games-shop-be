package org.arpha.service.helper;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.common.sas.SasProtocol;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.arpha.dto.media.enums.AccessType;
import org.arpha.dto.media.request.FileUploadRequest;
import org.arpha.exception.GenerateFileLinkException;
import org.arpha.property.AzureStorageProperties;
import org.arpha.repository.UserRepository;
import org.arpha.utils.Boxed;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class MediaServiceHelper {

    private final UserRepository userRepository;
    private final AzureStorageProperties azureStorageProperties;
    private final BlobContainerClient blobContainerClient;

    public boolean validate(FileUploadRequest fileUploadRequest, BlobClient blobClient) {
        return !blobClient.exists() && switch (fileUploadRequest.getTargetType()) {
            case USER -> userRepository.existsById(fileUploadRequest.getTargetId());
            case PRODUCT -> true; //ToDo Add productRepository;
        };
    }

    @SneakyThrows
    public MultipartFile upload(BlobClient blobClient, MultipartFile multipartFile) {
        blobClient.upload(multipartFile.getInputStream(), multipartFile.getSize());
        return multipartFile;
    }

    public String generateLink(String fileName, AccessType accessType) {
        return Boxed
                .of(accessType)
                .mapToBoxed(accessType1 -> BlobSasPermission.parse(accessType1.getPermission()))
                .mapToBoxed(blobSasPermission -> new BlobServiceSasSignatureValues(OffsetDateTime.now().plusSeconds(
                        azureStorageProperties.linkExpiration()), blobSasPermission))
                .mapToBoxed(blobSignature -> blobSignature.setProtocol(SasProtocol.HTTPS_ONLY))
                .mapToBoxed(blobSignature -> blobSignature.setCacheControl("no-cache"))
                .mapToBoxed(blobSasPermission -> Pair.of(blobContainerClient.getBlobClient(fileName), blobSasPermission))
                .mapToBoxed(pair -> pair.getLeft().getBlobUrl() + "?" + pair.getLeft().generateSas(pair.getRight()))
                .orElseThrow(() -> new GenerateFileLinkException("Could not generate link for file " + fileName));
    }

}
