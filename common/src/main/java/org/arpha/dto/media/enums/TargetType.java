package org.arpha.dto.media.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TargetType {
    PRODUCT("products/%s/"),
    PRODUCT_MAIN_IMG("products/%s/");

    private final String folder;
}
