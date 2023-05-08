package managementapp.managementapp.services;

import managementapp.managementapp.dtos.authentication.RegistrationRequestDto;
import managementapp.managementapp.models.*;
import managementapp.managementapp.repositories.RoleRepository;
import managementapp.managementapp.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class UserAppService implements UserDetailsService {
    @Autowired
    private UserAppRepository userAppRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> registerUser(RegistrationRequestDto registrationRequest){
        try{
            if(userExistsByUsernameOrEmail(registrationRequest.getUsername(), registrationRequest.getEmail())){
                return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);
            }
            UserApp userToRegister = new UserApp();
            userToRegister.setEmail(registrationRequest.getEmail());
            userToRegister.setUsername(registrationRequest.getUsername());
            userToRegister.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            userToRegister.setRole(roleRepository.findByName(registrationRequest.getRoleName()));

            switch (registrationRequest.getRoleName()){
                case "SOFTWARE_ENGINEER":
                    save(new SoftwareEngineer(userToRegister));
                    return new ResponseEntity<>(null, HttpStatus.CREATED);
                case "PROJECT_MANAGER":
                    save(new ProjectManager(userToRegister));
                    return new ResponseEntity<>(null, HttpStatus.CREATED);
                case "HUMAN_RESOURCES_MANAGER":
                    save(new HumanResourcesManager(userToRegister));
                    return new ResponseEntity<>(null, HttpStatus.CREATED);
                default:
                    return new ResponseEntity<>("Role doesn't exist",HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            throw e;
        }
    }

    public UserApp save(UserApp userApp){
        return userAppRepository.save(userApp);
    }

    public boolean userExistsByUsernameOrEmail(String username, String email) {
        return userAppRepository.existsByUsernameOrEmail(username, email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAppRepository.findByUsername(username);
    }
}
