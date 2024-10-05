package org.arpha.service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.common.sas.SasProtocol;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.arpha.dto.media.enums.AccessType;
import org.arpha.exception.GenerateFileLinkException;
import org.arpha.property.AzureStorageProperties;
import org.arpha.utils.Boxed;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class BlobServiceImpl implements BlobService {

    private final AzureStorageProperties azureStorageProperties;
    private final BlobContainerClient blobContainerClient;

    @Override
    public String generateLink(String fileName, AccessType accessType) {
        return Boxed
                .of(accessType)
                .mapToBoxed(accessType1 -> BlobSasPermission.parse(accessType1.getPermission()))
                .mapToBoxed(this::getSasSignature)
                .mapToBoxed(blobSasPermission -> Pair.of(blobContainerClient.getBlobClient(fileName), blobSasPermission))
                .mapToBoxed(pair -> pair.getLeft().getBlobUrl() + "?" + pair.getLeft().generateSas(pair.getRight()))
                .orElseThrow(() -> new GenerateFileLinkException("Could not generate link for file " + fileName));
    }

    private BlobServiceSasSignatureValues getSasSignature(BlobSasPermission blobSasPermission) {
        return new BlobServiceSasSignatureValues(OffsetDateTime.now().plusSeconds(
                azureStorageProperties.linkExpiration()), blobSasPermission)
                .setProtocol(SasProtocol.HTTPS_ONLY)
                .setCacheControl("no-cache");
    }

}