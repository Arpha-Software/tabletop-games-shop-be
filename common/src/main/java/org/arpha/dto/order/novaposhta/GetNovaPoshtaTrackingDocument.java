package org.arpha.dto.order.novaposhta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetNovaPoshtaTrackingDocument {

    @JsonProperty("DocumentNumber")
    private String documentNumber;
    @JsonProperty("Phone")
    private String phone;

}
