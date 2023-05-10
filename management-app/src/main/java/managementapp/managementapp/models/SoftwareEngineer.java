package managementapp.managementapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SoftwareEngineer extends UserApp {
    // Ova anotacija kreira tabelu u bazi Software_engineer_id | Skills
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> skills = new ArrayList<>();

    public SoftwareEngineer(UserApp userApp) {
        super(userApp.getId(), userApp.getUsername(), userApp.getEmail(), userApp.getPassword(), userApp.getActive(), userApp.getRole());
    }
}
