package org.arpha.dto.media.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessType {

    READ("r"), WRITE("w"), DELETE("d");

    private final String permission;

}
