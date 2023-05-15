package managementapp.managementapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class HumanResourcesManager extends UserApp {

    public HumanResourcesManager(UserApp userApp) {
        super(userApp.getId(), userApp.getUsername(), userApp.getEmail(), userApp.getPassword(), userApp.getAddress(), userApp.getActive(), userApp.getRole());
    }
}
