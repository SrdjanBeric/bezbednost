package managementapp.managementapp.repositories;

import managementapp.managementapp.models.Project;
import managementapp.managementapp.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    public RefreshToken findByHmacValue(String hmacValue);

}
