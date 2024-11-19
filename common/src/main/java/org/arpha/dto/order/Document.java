package org.arpha.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {

    @JsonProperty("Ref")
    private String ref;
    @JsonProperty("CostOnSite")
    private String costOnSite;
    @JsonProperty("EstimatedDeliveryDate")
    private LocalDate estimatedDeliveryDate;
    @JsonProperty("IntDocNumber")
    private String intDocNumber;
    @JsonProperty("TypeDocument")
    private String typeDocument;

}
