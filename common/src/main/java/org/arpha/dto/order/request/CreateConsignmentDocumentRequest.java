package org.arpha.dto.order.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateConsignmentDocumentRequest {

    @Min(1)
    private long orderId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate sendDate;
    @NotEmpty(message = "City field can't be empty!")
    private String city;
    private String cityCode;
    private String senderDepartment;
    private String senderDepartmentCode;

}
