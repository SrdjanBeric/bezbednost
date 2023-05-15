package managementapp.managementapp.repositories;

import managementapp.managementapp.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    public List<Project> findAllByProjectManagerId(Long projectManagerId);

    @Query("SELECT p FROM Project p JOIN p.taskDescriptions tp WHERE tp.softwareEngineer.id = :softwareEngineerId")
    public List<Project> findAllBySoftwareEngineerId(Long softwareEngineerId);

//    public List<Project> findProjectByIdAndSoftwareEngineer(Long projectId,Long SoftwewEngineerId );
}
