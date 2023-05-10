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
public class ProjectManager extends UserApp {

    public ProjectManager(UserApp userApp) {
        super(userApp.getId(), userApp.getUsername(), userApp.getEmail(), userApp.getPassword(), userApp.getActive(), userApp.getRole());
    }
}
