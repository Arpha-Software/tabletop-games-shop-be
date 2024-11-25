package org.arpha.dto.order.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateConsignmentDocumentRequest {

    private long orderId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate sendDate;
    private String city;
    private String senderDepartmentName;

}
