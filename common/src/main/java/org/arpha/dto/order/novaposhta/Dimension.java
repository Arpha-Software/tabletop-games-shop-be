package org.arpha.dto.order.novaposhta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dimension {

    @JsonProperty("Width")
    private String width;
    @JsonProperty("Height")
    private String height;
    @JsonProperty("Length")
    private String length;

}
