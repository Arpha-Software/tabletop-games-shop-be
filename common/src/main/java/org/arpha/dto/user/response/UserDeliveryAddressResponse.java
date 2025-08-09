package org.arpha.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDeliveryAddressResponse {

    private long id;
    private String city;
    private String street;
    private String houseNumber;
    private String flatNumber;
    private String department;
    private boolean isDefault;
}