package org.arpha.notificationservice.mapper;

import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.communication.sms.models.SmsSendResult;
import org.arpha.domain.type.notification.configuration.EmailNotificationProperties;
import org.arpha.domain.type.notification.configuration.SmsNotificationProperties;
import org.arpha.notificationservice.domain.Notification;
import org.arpha.notificationservice.mapper.helper.NotificationMapperHelper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = NotificationMapperHelper.class
)
public interface NotificationMapper {

    @Mapping(target = "notificationType", source = "properties.notificationType")
    @Mapping(target = "recipient", source = "properties.recipientEmail")
    @Mapping(target = "deliveryType", constant = "EMAIL")
    @Mapping(target = "status", source = "properties", qualifiedByName = "mapToEmailNotificationStatus")
    Notification mapToNotification(EmailNotificationProperties properties, @Context EmailSendResult result);

    @Mapping(target = "notificationType", source = "properties.notificationType")
    @Mapping(target = "recipient", source = "properties.recipientPhoneNumber")
    @Mapping(target = "deliveryType", constant = "SMS")
    @Mapping(target = "status", source = "properties", qualifiedByName = "mapToSmsNotificationStatus")
    Notification mapToNotification(SmsNotificationProperties properties, @Context SmsSendResult result);


    @Mapping(target = "subject", source = "properties.subject")
    @Mapping(target = "bodyHtml", source = "properties.body")
    @Mapping(target = "toRecipients", source = "properties.recipientEmail", qualifiedByName = "mapToEmailAddress")
    @Mapping(target = "senderAddress", source = "properties", qualifiedByName = "mapToSenderAddress")
    EmailMessage mapToEmailMessage(EmailNotificationProperties properties);

}
