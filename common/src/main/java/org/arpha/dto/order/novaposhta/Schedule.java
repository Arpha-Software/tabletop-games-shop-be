package org.arpha.dto.order.novaposhta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    @JsonProperty("Monday")
    private String monday;
    @JsonProperty("Tuesday")
    private String tuesday;
    @JsonProperty("Wednesday")
    private String wednesday;
    @JsonProperty("Thursday")
    private String thursday;
    @JsonProperty("Friday")
    private String friday;
    @JsonProperty("Saturday")
    private String saturday;
    @JsonProperty("Sunday")
    private String sunday;

}
