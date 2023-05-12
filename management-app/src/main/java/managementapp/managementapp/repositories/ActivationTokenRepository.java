package managementapp.managementapp.repositories;

import managementapp.managementapp.models.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken, UUID> {
    public ActivationToken findByHmacValue(String hmacValue);
}
