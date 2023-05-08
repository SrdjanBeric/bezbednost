package managementapp.managementapp.repositories;

import managementapp.managementapp.models.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAppRepository extends JpaRepository<UserApp, Long> {
    public UserApp findByUsername(String username);
}
