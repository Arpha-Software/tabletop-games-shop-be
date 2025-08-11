package org.arpha.dto.order.novaposhta.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.GetNovaPoshtaTrackingDocument;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetNovaPoshtaTrackingMethodProperties {

    @JsonProperty("Documents")
    List<GetNovaPoshtaTrackingDocument> documents;

}
