package org.arpha.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class VerificationCodeService {

    private static final SecureRandom random = new SecureRandom();
    private final Cache verificationCodesCache;

    public VerificationCodeService(@Qualifier("userServiceCacheManager") CacheManager cacheManager) {
        this.verificationCodesCache = cacheManager.getCache("verificationCodes");
    }

    public String generateAndStoreCode(String key) {
        String code = String.format("%06d", random.nextInt(1000000));
        verificationCodesCache.put(key, code);
        return code;
    }

    public Optional<String> getCode(String key) {
        return Optional.ofNullable(verificationCodesCache.get(key, String.class));
    }

    public void evictCode(String key) {
        verificationCodesCache.evict(key);
    }
}
