package org.arpha.dto.user.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDeliveryAddressRequest {

    @NotEmpty(message = "City can't be empty!")
    private String city;

    @NotEmpty(message = "Street can't be empty!")
    private String street;

    @NotEmpty(message = "House number can't be empty!")
    private String houseNumber;

    private String flatNumber;

    private String department;
}