package org.arpha.dto.order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetails {

    @NotEmpty(message = "First name can't empty!")
    private String firstName;
    @NotEmpty(message = "Middle name can't empty!")
    private String middleName;
    @NotEmpty(message = "Last name can't empty!")
    private String lastName;
    @NotEmpty(message = "Phone number can't empty!")
    private String phoneNumber;
    @Email(message = "Email is in wrong format!")
    @NotNull(message = "Email can't be empty!")
    private String email;

}
