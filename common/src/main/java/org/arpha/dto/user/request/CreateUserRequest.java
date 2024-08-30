package org.arpha.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.user.validation.PasswordConfirmation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordConfirmation
public class CreateUserRequest {

    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 symbols!")
    private String firstName;
    private String lastName;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "Password must contain at least one number, lower case and upper case letters and be 8 symbols long!")
    private String password;
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{2}-\\d{2}", message = "Phone number in wrong format, should be xxx-xxx-xx-xx!")
    private String phone;
    private String confirmationPassword;
    private boolean isSubscribedToNewsLetter;

}
