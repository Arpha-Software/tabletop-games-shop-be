package org.arpha.dto.order.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.novaposhta.PaginationInfo;
import org.arpha.dto.order.novaposhta.data.SearchWarehousesData;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchWarehousesResponse {

    private boolean success;
    private List<SearchWarehousesData> data;
    private List<String> errors;
    private List<String> warnings;
    private PaginationInfo info;
    private List<String> messageCodes;
    private List<String> errorCodes;
    private List<String> warningCodes;
    private List<String> infoCodes;
}
