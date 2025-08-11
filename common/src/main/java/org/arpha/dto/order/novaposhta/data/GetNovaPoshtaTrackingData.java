package org.arpha.dto.order.novaposhta.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetNovaPoshtaTrackingData {

    @JsonProperty("PossibilityCreateReturn")
    private boolean possibilityCreateReturn;
    @JsonProperty("PossibilityCreateRefusal")
    private boolean possibilityCreateRefusal;
    @JsonProperty("PossibilityChangeEW")
    private boolean possibilityChangeEW;
    @JsonProperty("PossibilityCreateRedirecting")
    private boolean possibilityCreateRedirecting;
    @JsonProperty("Number")
    private String number;
    @JsonProperty("Redelivery")
    private String redelivery;
    @JsonProperty("RedeliverySum")
    private String redeliverySum;
    @JsonProperty("RedeliveryNum")
    private String redeliveryNum;
    @JsonProperty("RedeliveryPayer")
    private String redeliveryPayer;
    @JsonProperty("OwnerDocumentType")
    private String ownerDocumentType;
    @JsonProperty("LastCreatedOnTheBasisDocumentType")
    private String lastCreatedOnTheBasisDocumentType;
    @JsonProperty("LastCreatedOnTheBasisPayerType")
    private String lastCreatedOnTheBasisPayerType;
    @JsonProperty("LastCreatedOnTheBasisDateTime")
    private String lastCreatedOnTheBasisDateTime;
    @JsonProperty("LastTransactionStatusGM")
    private String lastTransactionStatusGM;
    @JsonProperty("LastTransactionDateTimeGM")
    private String lastTransactionDateTimeGM;
    @JsonProperty("LastAmountTransferGM")
    private String lastAmountTransferGM;
    @JsonProperty("DateCreated")
    private String dateCreated;
    @JsonProperty("DocumentWeight")
    private String documentWeight;
    @JsonProperty("FactualWeight")
    private String factualWeight;
    @JsonProperty("VolumeWeight")
    private String volumeWeight;
    @JsonProperty("CheckWeight")
    private String checkWeight;
    @JsonProperty("CheckWeightMethod")
    private String checkWeightMethod;
    @JsonProperty("DocumentCost")
    private String documentCost;
    @JsonProperty("CalculatedWeight")
    private String calculatedWeight;
    @JsonProperty("SumBeforeCheckWeight")
    private String sumBeforeCheckWeight;
    @JsonProperty("PayerType")
    private String payerType;
    @JsonProperty("RecipientFullName")
    private String recipientFullName;
    @JsonProperty("RecipientDateTime")
    private String recipientDateTime;
    @JsonProperty("ScheduledDeliveryDate")
    private String scheduledDeliveryDate;
    @JsonProperty("PaymentMethod")
    private String paymentMethod;
    @JsonProperty("CargoDescriptionString")
    private String cargoDescriptionString;
    @JsonProperty("CargoType")
    private String cargoType;
    @JsonProperty("CitySender")
    private String citySender;
    @JsonProperty("CityRecipient")
    private String cityRecipient;
    @JsonProperty("WarehouseRecipient")
    private String warehouseRecipient;
    @JsonProperty("CounterpartyType")
    private String counterpartyType;
    @JsonProperty("AfterpaymentOnGoodsCost")
    private String afterpaymentOnGoodsCost;
    @JsonProperty("ServiceType")
    private String serviceType;
    @JsonProperty("UndeliveryReasonsSubtypeDescription")
    private String undeliveryReasonsSubtypeDescription;
    @JsonProperty("WarehouseRecipientNumber")
    private String warehouseRecipientNumber;
    @JsonProperty("LastCreatedOnTheBasisNumber")
    private String lastCreatedOnTheBasisNumber;
    @JsonProperty("PhoneRecipient")
    private String phoneRecipient;
    @JsonProperty("RecipientFullNameEW")
    private String recipientFullNameEW;
    @JsonProperty("WarehouseRecipientInternetAddressRef")
    private String warehouseRecipientInternetAddressRef;
    @JsonProperty("MarketplacePartnerToken")
    private String marketplacePartnerToken;
    @JsonProperty("ClientBarcode")
    private String clientBarcode;
    @JsonProperty("RecipientAddress")
    private String recipientAddress;
    @JsonProperty("CounterpartyRecipientDescription")
    private String counterpartyRecipientDescription;
    @JsonProperty("CounterpartySenderType")
    private String counterpartySenderType;
    @JsonProperty("DateScan")
    private String dateScan;
    @JsonProperty("PaymentStatus")
    private String paymentStatus;
    @JsonProperty("PaymentStatusDate")
    private String paymentStatusDate;
    @JsonProperty("AmountToPay")
    private String amountToPay;
    @JsonProperty("AmountPaid")
    private String amountPaid;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("StatusCode")
    private String statusCode;
    @JsonProperty("RefEW")
    private String refEW;
    @JsonProperty("BackwardDeliverySubTypesActions")
    private String backwardDeliverySubTypesActions;
    @JsonProperty("BackwardDeliverySubTypesServices")
    private String backwardDeliverySubTypesServices;
    @JsonProperty("UndeliveryReasons")
    private String undeliveryReasons;
    @JsonProperty("DatePayedKeeping")
    private String datePayedKeeping;
    @JsonProperty("InternationalDeliveryType")
    private String internationalDeliveryType;
    @JsonProperty("SeatsAmount")
    private String seatsAmount;
    @JsonProperty("CardMaskedNumber")
    private String cardMaskedNumber;
    @JsonProperty("ExpressWaybillPaymentStatus")
    private String expressWaybillPaymentStatus;
    @JsonProperty("ExpressWaybillAmountToPay")
    private String expressWaybillAmountToPay;
    @JsonProperty("PhoneSender")
    private String phoneSender;
    @JsonProperty("TrackingUpdateDate")
    private String trackingUpdateDate;
    @JsonProperty("WarehouseSender")
    private String warehouseSender;
    @JsonProperty("DateReturnCargo")
    private String dateReturnCargo;
    @JsonProperty("DateMoving")
    private String dateMoving;
    @JsonProperty("DateFirstDayStorage")
    private String dateFirstDayStorage;
    @JsonProperty("RefCityRecipient")
    private String refCityRecipient;
    @JsonProperty("RefCitySender")
    private String refCitySender;
    @JsonProperty("RefSettlementRecipient")
    private String refSettlementRecipient;
    @JsonProperty("RefSettlementSender")
    private String refSettlementSender;
    @JsonProperty("SenderAddress")
    private String senderAddress;
    @JsonProperty("SenderFullNameEW")
    private String senderFullNameEW;
    @JsonProperty("AnnouncedPrice")
    private String announcedPrice;
    @JsonProperty("AdditionalInformationEW")
    private String additionalInformationEW;
    @JsonProperty("ActualDeliveryDate")
    private String actualDeliveryDate;
    @JsonProperty("PostomatV3CellReservationNumber")
    private String postomatV3CellReservationNumber;
    @JsonProperty("OwnerDocumentNumber")
    private String ownerDocumentNumber;
    @JsonProperty("LastAmountReceivedCommissionGM")
    private String lastAmountReceivedCommissionGM;
    @JsonProperty("DeliveryTimeframe")
    private String deliveryTimeframe;
    @JsonProperty("UndeliveryReasonsDate")
    private String undeliveryReasonsDate;
    @JsonProperty("RecipientWarehouseTypeRef")
    private String recipientWarehouseTypeRef;
    @JsonProperty("WarehouseRecipientRef")
    private String warehouseRecipientRef;
    @JsonProperty("CategoryOfWarehouse")
    private String categoryOfWarehouse;
    @JsonProperty("WarehouseRecipientAddress")
    private String warehouseRecipientAddress;
    @JsonProperty("WarehouseSenderInternetAddressRef")
    private String warehouseSenderInternetAddressRef;
    @JsonProperty("WarehouseSenderAddress")
    private String warehouseSenderAddress;
    @JsonProperty("AviaDelivery")
    private String aviaDelivery;
    @JsonProperty("BarcodeRedBox")
    private String barcodeRedBox;
    @JsonProperty("CargoReturnRefusal")
    private String cargoReturnRefusal;
    @JsonProperty("DaysStorageCargo")
    private String daysStorageCargo;
    @JsonProperty("SecurePayment")
    private String securePayment;
    @JsonProperty("PossibilityChangeCash2Card")
    private boolean possibilityChangeCash2Card;
    @JsonProperty("PossibilityChangeDeliveryIntervals")
    private boolean possibilityChangeDeliveryIntervals;
    @JsonProperty("PossibilityTermExtensio")
    private boolean possibilityTermExtensio;
    @JsonProperty("StorageAmount")
    private String storageAmount;
    @JsonProperty("StoragePrice")
    private String storagePrice;
    @JsonProperty("FreeShipping")
    private String freeShipping;
    @JsonProperty("LoyaltyCardRecipient")
    private String loyaltyCardRecipient;

}
