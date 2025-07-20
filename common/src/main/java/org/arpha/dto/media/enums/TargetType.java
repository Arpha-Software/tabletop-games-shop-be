package org.arpha.dto.media.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TargetType {
    PRODUCT("products/%s/"),
    PRODUCT_MAIN_IMG("products/%s/"),
    POST_IMAGE("posts/%s/"),
    POST_MAIN_IMAGE("posts/%s/main/");

    private final String folder;
}
