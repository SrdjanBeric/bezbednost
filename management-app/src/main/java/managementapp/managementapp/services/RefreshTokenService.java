package managementapp.managementapp.services;

import managementapp.managementapp.models.LoginToken;
import managementapp.managementapp.models.RefreshToken;
import managementapp.managementapp.models.UserApp;
import managementapp.managementapp.repositories.RefreshTokenRepository;
import managementapp.managementapp.security.HashingAlogorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    LogService logService;
    public UUID generateRefreshToken(UserApp userApp){
        UUID token = UUID.randomUUID();
        RefreshToken refreshToken = RefreshToken.builder()
                .id(UUID.randomUUID())
                .hmacValue(HashingAlogorithm.calculateHmac(token.toString()))
                .userApp(userApp)
                .dateTimeStart(LocalDateTime.now())
                .dateTimeEnd(LocalDateTime.now().plusDays(1))
                .build();
        refreshTokenRepository.save(refreshToken);
        logService.INFO("Generated refresh token for user: " + userApp.getUsername());
        return token;
    }

    public UserApp validateTokenAndGetUser(UUID token){
        RefreshToken refreshToken = refreshTokenRepository.findByHmacValue(HashingAlogorithm.calculateHmac(token.toString()));
        if(refreshToken == null || refreshToken.isExpired()){
            logService.ERROR("Invalid or expired refresh token.");
            return null;
        }
        logService.INFO("Validated refresh token for user: " + refreshToken.getUserApp().getUsername());
        return refreshToken.getUserApp();
    }
}
