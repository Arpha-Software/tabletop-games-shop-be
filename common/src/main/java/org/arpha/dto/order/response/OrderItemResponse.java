package org.arpha.dto.order.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*@Data
@AllArgsConstructor
@NoArgsConstructor*/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemResponse {
}
