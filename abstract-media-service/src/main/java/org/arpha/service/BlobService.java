package org.arpha.service;

import org.arpha.dto.media.enums.AccessType;

public interface BlobService {

    String generateLink(String fileName, AccessType accessType);
}
