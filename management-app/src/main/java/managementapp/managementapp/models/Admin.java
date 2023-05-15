package managementapp.managementapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Admin extends UserApp {

    public Admin(UserApp userApp) {
        super(userApp.getId(), userApp.getUsername(), userApp.getEmail(), userApp.getPassword(), userApp.getAddress(), userApp.getActive(), userApp.getRole());
    }
}
