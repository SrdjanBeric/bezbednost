package managementapp.managementapp.repositories;

import managementapp.managementapp.models.ActivationToken;
import managementapp.managementapp.models.LoginToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoginTokenRepository extends JpaRepository<LoginToken, UUID> {
    public LoginToken findByHmacValue(String hmacValue);

}
