package org.arpha.dto.order.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateConsignmentDocumentResponse {

    private boolean success;
    private List<Document> date;
    private List<String> errors;
    private List<String> warnings;
    private List<String> info;
    private List<String> messageCodes;
    private List<String> errorCodes;
    private List<String> warningCodes;
    private List<String> infoCodes;

}
