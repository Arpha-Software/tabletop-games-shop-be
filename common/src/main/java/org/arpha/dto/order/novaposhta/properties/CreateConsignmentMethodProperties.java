package org.arpha.dto.order.novaposhta.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.data.BackwardDeliveryData;
import org.arpha.dto.order.novaposhta.data.OptionsSeatData;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateConsignmentMethodProperties {

    @JsonProperty("SenderWarehouseIndex")
    private String senderWarehouseIndex;
    @JsonProperty("RecipientWarehouseIndex")
    private String recipientWarehouseIndex;
    @JsonProperty("PayerType")
    private String payerType;
    @JsonProperty("PaymentMethod")
    private String paymentMethod;
    @JsonProperty("DateTime")
    private String dateTime;
    @JsonProperty("CargoType")
    private String cargoType;
    @JsonProperty("ServiceType")
    private String serviceType;
    @JsonProperty("SeatsAmount")
    private String seatsAmount;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Cost")
    private String cost;
    @JsonProperty("CitySender")
    private String citySender;
    @JsonProperty("Sender")
    private String sender;
    @JsonProperty("SenderAddress")
    private String senderAddress;
    @JsonProperty("ContactSender")
    private String contactSender;
    @JsonProperty("SendersPhone")
    private String sendersPhone;
    @JsonProperty("CityRecipient")
    private String cityRecipient;
    @JsonProperty("Recipient")
    private String recipient;
    @JsonProperty("RecipientAddress")
    private String recipientAddress;
    @JsonProperty("ContactRecipient")
    private String contactRecipient;
    @JsonProperty("RecipientsPhone")
    private String recipientsPhone;
    @JsonProperty("OptionsSeat")
    private List<OptionsSeatData> optionsSeat;
    @JsonProperty("BackwardDeliveryData")
    private List<BackwardDeliveryData> backwardDeliveryData;
}
