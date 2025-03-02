package org.arpha.notificationservice.dto;

public record SmsSendRequest(String from, String to, String text) {
}
