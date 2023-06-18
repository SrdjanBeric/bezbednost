package managementapp.managementapp.repositories;

import managementapp.managementapp.models.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByUserAppIdOrderByCreatedDateDesc(Long userAppId);

    @Query("SELECT l FROM Log l WHERE l.logLevel = 'ERROR' or l.logLevel = 'FATAL'")
    List<Log> findCriticalLogs();
}
