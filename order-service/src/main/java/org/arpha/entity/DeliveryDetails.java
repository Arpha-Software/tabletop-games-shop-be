package org.arpha.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.enums.DeliveryType;
import org.arpha.dto.order.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "delivery_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type")
    private DeliveryType deliveryType;
    @Column(name = "delivery_price")
    private BigDecimal deliveryPrice;
    @Column(name = "expected_delivery_time")
    private OffsetDateTime expectedDeliveryTime;
    @Column(name = "doc_number")
    private String docNumber;
    @Column(name = "document_ref")
    private String documentRef;
    @OneToOne
    @JoinColumn(name = "delivery_address_id")
    private DeliveryAddress deliveryAddress;
}
