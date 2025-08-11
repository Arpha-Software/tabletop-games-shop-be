package org.arpha.dto.order.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.data.GetNovaPoshtaTrackingData;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetNovaPoshtaTrackingResponse {

    private boolean success;
    private List<GetNovaPoshtaTrackingData> data;
    private List<String> errors;
    private List<Map<String, String>> warnings;
    private List<String> messageCodes;
    private List<String> errorCodes;
    private List<String> warningCodes;
    private List<String> infoCodes;

}
