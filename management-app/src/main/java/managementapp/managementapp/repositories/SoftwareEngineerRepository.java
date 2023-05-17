package managementapp.managementapp.repositories;

import managementapp.managementapp.models.SoftwareEngineer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SoftwareEngineerRepository extends JpaRepository<SoftwareEngineer, Long> {

//    @Query("SELECT skills FROM software_engineer_skills WHERE id = :id")
//    List<String> findSkillsById(@Param("id") Long id);
}
