package managementapp.managementapp.repositories;

import managementapp.managementapp.models.RegistrationVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegistrationVerificationRepository extends JpaRepository<RegistrationVerification, UUID> {
}
