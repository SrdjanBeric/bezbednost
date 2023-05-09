package managementapp.managementapp.repositories;

import managementapp.managementapp.models.ProjectManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectManagerRepository extends JpaRepository<ProjectManager, Long> {
}
