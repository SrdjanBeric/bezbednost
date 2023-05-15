package managementapp.managementapp.services;

import managementapp.managementapp.dtos.authentication.RegistrationRequestDto;
import managementapp.managementapp.models.*;
import managementapp.managementapp.repositories.RoleRepository;
import managementapp.managementapp.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAppService implements UserDetailsService {
    @Autowired
    private UserAppRepository userAppRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> getAllUsers(){
        List<UserApp> allUsers = userAppRepository.findAll();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    public void update(UserApp updateduserApp){
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(loggedInUser!=null) {
            loggedInUser.setUsername(updateduserApp.getUsername());
            loggedInUser.setPassword(updateduserApp.getPassword());
            if(!loggedInUser.getRole().equals("SOFTWARE_ENGINEER")) {
                loggedInUser.setEmail(updateduserApp.getEmail());
            }
            loggedInUser.setAddress(updateduserApp.getAddress());
            loggedInUser.setRole(updateduserApp.getRole());
            loggedInUser.setActive(updateduserApp.getActive());

            userAppRepository.save(loggedInUser);
        }else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with id" + loggedInUser.getId() + "does not found");
        }



        //        Optional<UserApp> userApp = userAppRepository.findById(loggedInUser.getId());
//        if(userApp.isPresent()){
//            UserApp userApp1 = new UserApp();
//            userApp1.setUsername(updateduserApp.getUsername());
//            userApp1.setPassword(updateduserApp.getPassword());
//            userApp1.setEmail(updateduserApp.getEmail());
//            userApp1.setAddress(updateduserApp.getAddress());
//            userApp1.setRole(updateduserApp.getRole());
//            userApp1.setActive(updateduserApp.getActive());
//
//            return userAppRepository.save(userApp1);
//        }
//        else{
//             ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("User with id" + loggedInUser.getId() + "does not found");
//        }
//
//        return updateduserApp;
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
