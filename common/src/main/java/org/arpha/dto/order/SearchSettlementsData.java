package org.arpha.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchSettlementsData {

    @JsonProperty("TotalCount")
    private String totalCount;
    @JsonProperty("Addresses")
    private List<SettlementAddress> addresses;
    @JsonUnwrapped
    private SettlementAddress address;
}
