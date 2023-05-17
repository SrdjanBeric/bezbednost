package managementapp.managementapp.services;

import managementapp.managementapp.dtos.project.UserAppDto;
import managementapp.managementapp.models.*;
import managementapp.managementapp.repositories.RoleRepository;
import managementapp.managementapp.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ResponseEntity<?> getById(Long userId){
        UserApp userApp = userAppRepository.findById(userId).orElse(null);
        if(userApp == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with id '" + userId + "' does not exist.");
        }
        if(!userApp.getActive()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with id '" + userId + "' is not active.");
        }
        return new ResponseEntity<>(userApp, HttpStatus.OK);
    }

    //update nesto zeza
    ///i skroz naopacke radi
    // pogledati jos malo
    public void update(UserAppDto updateduserApp){
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        Role role = roleRepository.findByName(updateduserApp.getRoleName());
            loggedInUser.setUsername(updateduserApp.getUsername());
            loggedInUser.setPassword(passwordEncoder.encode((updateduserApp.getPassword() + loggedInUser.getPasswordSalt())));
            loggedInUser.setEmail(updateduserApp.getEmail());
            loggedInUser.setAddress(updateduserApp.getAddress());
//            loggedInUser.setRole(role);
            loggedInUser.setActive(updateduserApp.getActive());
            userAppRepository.save(loggedInUser);
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
