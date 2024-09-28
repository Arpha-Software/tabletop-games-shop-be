package org.arpha.notificationservice.dto;

import lombok.Data;

@Data
public class SmsServiceResponse {

    private String apiVersion;
    private String message;
    private String id;
    private String from;
    private String to;
    private String region;
    private String operator;
    private String dateCreated;
    private String dateSent;
    private String dlrStatus;
    private String statusDescription;
    private String timezone;
    private double price;
    private String priceUnit;
    private int code;
    private int status;

}
