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

    public ResponseEntity<?> getUsersToActivate(){
        try{
            List<UserApp> usersToActivate = userAppRepository.findUserAppsByActive(false);
            return new ResponseEntity<>(usersToActivate, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while getting all users to activate.");
        }
    }

    public ResponseEntity<?> approveRegistrationRequest(Long userId){
        try{
            UserApp userApp = userAppRepository.findById(userId).orElse(null);
            if(userApp == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User with id: " + userId + " does not exist.");
            }
            if(userApp.getActive()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User with id: " + userId + " is already active.");
            }
            authenticationService.sendVerificationEmail(userApp);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while activating user with id: " + userId);
        }
    }


    //da li to treba ovako
    public ResponseEntity<?> getActiveUsers(){
        try{
            List<UserApp> usersToActivate = userAppRepository.findUserAppsByActive(true);
            return new ResponseEntity<>(usersToActivate, HttpStatus.OK);
        }catch (Exception e){
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
            ResponseEntity.status(HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during create admin.");
        }


    }

}
