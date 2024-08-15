package org.arpha.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.user.validation.FieldsValueMatch;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldsValueMatch(
        field = "newPassword",
        fieldMatch = "confirmationPassword",
        message = "User's new password and confirmation password are different!"
)
public class ChangePasswordRequest {

    private String password;
    private String confirmationPassword;
    private String newPassword;

}
