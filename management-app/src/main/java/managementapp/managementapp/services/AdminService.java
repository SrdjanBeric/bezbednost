package managementapp.managementapp.services;

import managementapp.managementapp.dtos.authentication.RegistrationRequestDto;
import managementapp.managementapp.dtos.project.ProjectDTO;
import managementapp.managementapp.dtos.project.UserAppDto;
import managementapp.managementapp.models.*;
import managementapp.managementapp.repositories.AdminRepository;
import managementapp.managementapp.repositories.RoleRepository;
import managementapp.managementapp.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserAppRepository userAppRepository;
    @Autowired
    private ActivationTokenService activationTokenService;
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserAppService userAppService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LogService logService;
    public ResponseEntity<?> getUsersToActivate(){
        try{
            List<UserApp> usersToActivate = userAppRepository.findUserAppsByActive(false);
            logService.INFO("Retrieved users to activate");
            return new ResponseEntity<>(usersToActivate, HttpStatus.OK);
        }catch (Exception e){
            logService.ERROR("An error occurred while getting all users to activate.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while getting all users to activate.");
        }
    }

    public ResponseEntity<?> approveRegistrationRequest(Long userId){
        try{
            UserApp userApp = userAppRepository.findById(userId).orElse(null);
            if(userApp == null){
                logService.ERROR("User with id: " + userId + " does not exist.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User with id: " + userId + " does not exist.");
            }
            if(userApp.getActive()){
                logService.ERROR("User with id: " + userId + " is already active.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User with id: " + userId + " is already active.");
            }
            String activationLink = authenticationService.sendVerificationEmail(userApp);
            logService.INFO("Approved registration request for user with id: " + userId);
            return new ResponseEntity<>(activationLink, HttpStatus.OK);
        }catch (Exception e){
            logService.ERROR("An error occurred while activating user with id: " + userId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while activating user with id: " + userId);
        }
    }


    //da li to treba ovako
    public ResponseEntity<?> getActiveUsers(){
        try{
            List<UserApp> usersToActivate = userAppRepository.findUserAppsByActive(true);
            logService.INFO("Retrieved active users");
            return new ResponseEntity<>(usersToActivate, HttpStatus.OK);
        }catch (Exception e){
            logService.ERROR("An error occurred while getting active users.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while getting all users to activate.");
        }
    }

    public void adminCreate(RegistrationRequestDto registrationRequest) {
        try {
            if (userAppService.userExistsByUsernameOrEmail(registrationRequest.getUsername(), registrationRequest.getEmail())) {
                ResponseEntity.status(HttpStatus.CONFLICT);
            }
            Role role = roleRepository.findByName("ADMIN");
            UserApp userToRegister = UserApp.builder()
                    .email(registrationRequest.getEmail())
                    .username(registrationRequest.getUsername())
                    .password(passwordEncoder.encode(registrationRequest.getPassword()))
                    .role(role)
                    .address(registrationRequest.getAddress())
                    .active(true)
                    .build();
            userAppService.save(new Admin(userToRegister));
            logService.INFO("Admin created: " + userToRegister.getUsername());
            ResponseEntity.status(HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during create admin.");
        }


    }

}
