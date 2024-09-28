package org.arpha.notificationservice.dto;

public record SmsSendRequest(String sender, String mobile, String content) {
}
