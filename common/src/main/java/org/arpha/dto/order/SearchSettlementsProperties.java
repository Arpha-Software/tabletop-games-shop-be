package org.arpha.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchSettlementsProperties {

    @JsonProperty("CityName")
    private String cityName;
    @JsonProperty("Limit")
    private String limit;
    @JsonProperty("Page")
    private String page;

}
