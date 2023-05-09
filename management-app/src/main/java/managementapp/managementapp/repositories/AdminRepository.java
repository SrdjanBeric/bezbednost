package managementapp.managementapp.repositories;

import managementapp.managementapp.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
