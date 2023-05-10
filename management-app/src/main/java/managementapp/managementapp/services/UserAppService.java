package managementapp.managementapp.services;

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
