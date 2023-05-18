package managementapp.managementapp.repositories;

import managementapp.managementapp.models.Project;
import managementapp.managementapp.models.SoftwareEngineer;
import managementapp.managementapp.models.SoftwareEngineerProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SoftwareEngineerProjectRepository extends JpaRepository<SoftwareEngineerProject, Long> {
    public SoftwareEngineerProject findByProjectIdAndSoftwareEngineerId(Long projectId, Long softwareEngineerId);

    @Query("SELECT p FROM Project p JOIN p.taskDescriptions tp WHERE tp.softwareEngineer = :engineer")
    List<Project> findAllByProjectEngineer(SoftwareEngineer engineer);
}
