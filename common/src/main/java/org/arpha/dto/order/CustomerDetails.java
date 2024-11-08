package org.arpha.dto.order;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetails {

    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    @Email
    private String email;

}
