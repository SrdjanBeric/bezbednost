package managementapp.managementapp.dtos.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAppDto {
    private Long id;
    private String username;
    private String email;
    private Boolean active;
    private String roleName;
}
