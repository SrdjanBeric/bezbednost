package managementapp.managementapp.dtos.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDto {
    @NotBlank
    @Email
    private String email;
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username can only contain letters, digits and underscore")
    @Size(min = 4, message = "Username must be at least 4 characters long")
    private String username;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, one uppercase letter and one digit")
    private String password;
    @NotBlank
    private String roleName;
    @NotBlank
    private String adress;
}
