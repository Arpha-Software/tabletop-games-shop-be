package org.arpha.dto.order.novaposhta.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.SettlementsStreetsAddress;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchSettlementsStreetsData {

    @JsonProperty("TotalCount")
    private String totalCount;
    @JsonProperty("Addresses")
    private List<SettlementsStreetsAddress> addresses;
}
