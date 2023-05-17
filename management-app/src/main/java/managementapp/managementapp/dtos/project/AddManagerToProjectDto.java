package managementapp.managementapp.dtos.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddManagerToProjectDto {

    @NotBlank
    private Long projectId;
    @NotBlank
    private Long managerId;
}
