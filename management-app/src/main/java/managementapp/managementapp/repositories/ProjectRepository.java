package managementapp.managementapp.repositories;

import managementapp.managementapp.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
