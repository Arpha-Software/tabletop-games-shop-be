package org.arpha.dto.order.novaposhta.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionsSeatData {

    private String weight;
    private String volumetricLength;
    private String volumetricWidth;
    private String volumetricHeight;
    private String volumetricVolume;
    private boolean specialCargo;

    public OptionsSeatData(String weight, String volumetricLength, String volumetricWidth, String volumetricHeight) {
        this.weight = weight;
        this.volumetricVolume = "";
        this.specialCargo = false;
        this.volumetricLength = volumetricLength;
        this.volumetricWidth = volumetricWidth;
        this.volumetricHeight = volumetricHeight;
    }
}
