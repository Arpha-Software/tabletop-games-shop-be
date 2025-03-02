package org.arpha.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsServiceResponse {

    private String msgId;
    private String reservedSmsSegments;

}
