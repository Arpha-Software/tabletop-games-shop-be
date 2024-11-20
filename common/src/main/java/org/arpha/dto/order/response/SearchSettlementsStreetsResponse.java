package org.arpha.dto.order.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.data.SearchSettlementsStreetsData;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchSettlementsStreetsResponse {

    private boolean success;
    private List<SearchSettlementsStreetsData> data;
    private List<String> errors;
    private List<String> warnings;
    private List<String> info;
    private List<String> messageCodes;
    private List<String> errorCodes;
    private List<String> warningCodes;
    private List<String> infoCodes;
}
