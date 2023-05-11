package managementapp.managementapp.services;

import managementapp.managementapp.models.RegistrationVerification;
import managementapp.managementapp.models.UserApp;
import managementapp.managementapp.repositories.RegistrationVerificationRepository;
import managementapp.managementapp.repositories.UserAppRepository;
import managementapp.managementapp.security.HashingAlogorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RegistrationVerificationService {
    @Autowired
    private RegistrationVerificationRepository registrationVerificationRepository;
    @Autowired
    private UserAppRepository userAppRepository;

    public UUID generateActivationToken(UserApp userToRegister){
        UUID token = UUID.randomUUID();
        RegistrationVerification registrationVerification = RegistrationVerification.builder()
                .id(UUID.randomUUID())
                .hmacValue(HashingAlogorithm.calculateHmac(token.toString()))
                .userApp(userToRegister)
                .dateTimeStart(LocalDateTime.now())
                .dateTimeEnd(LocalDateTime.now().plusDays(1))
                .activated(false)
                .build();
        return token;
    }


    public ResponseEntity<?> activate(UUID token){
        RegistrationVerification registrationVerification = registrationVerificationRepository.findByHmacValue(HashingAlogorithm.calculateHmac(token.toString()));
        if(registrationVerification == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid token");
        }
        if(registrationVerification.getActivated() == true){
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Account is already active.");
        }
        if(isExpired(registrationVerification)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Token has expired");
        }
        UserApp userApp = userAppRepository.findById(registrationVerification.getUserApp().getId()).orElse(null);
        if(userApp == null){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User does not exist.");
        }
        userApp.setActive(true);
        userAppRepository.save(userApp);
        registrationVerification.setActivated(true);
        registrationVerificationRepository.save(registrationVerification);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean isExpired(RegistrationVerification registrationVerification){
        return LocalDateTime.now().isAfter(registrationVerification.getDateTimeEnd());
    }
}
