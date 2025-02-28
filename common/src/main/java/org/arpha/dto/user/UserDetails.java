package org.arpha.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arpha.dto.user.response.UserResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetails {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    public static UserDetails of(UserResponse userResponse) {
        return new UserDetails(
                userResponse.getId(),
                userResponse.getFirstName(),
                userResponse.getLastName(),
                userResponse.getEmail(),
                userResponse.getRole()
        );
    }

}
