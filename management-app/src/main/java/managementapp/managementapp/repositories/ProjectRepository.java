package managementapp.managementapp.repositories;

import managementapp.managementapp.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    public List<Project> findAllByProjectManagerId(Long projectManagerId);

    @Query("SELECT p FROM Project p JOIN p.taskDescriptions tp WHERE tp.softwareEngineer.id = :softwareEngineerId")
    public List<Project> findAllBySoftwareEngineerId(Long softwareEngineerId);

    @Query("SELECT p FROM Project p WHERE p.name = :name")
    Optional<Project> findByName(String name);

//    public List<Project> findProjectByIdAndSoftwareEngineer(Long projectId,Long SoftwewEngineerId );
}
