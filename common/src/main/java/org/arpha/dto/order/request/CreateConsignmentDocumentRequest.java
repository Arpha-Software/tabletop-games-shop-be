package org.arpha.dto.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.order.CreateConsignmentMethodProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateConsignmentDocumentRequest {

    private String apiKey;
    private String modelName;
    private String calledMethod;
    private CreateConsignmentMethodProperties methodProperties;

}
