package managementapp.managementapp.services;

import managementapp.managementapp.dtos.authentication.LoginRequestDto;
import managementapp.managementapp.dtos.authentication.RegistrationRequestDto;
import managementapp.managementapp.dtos.authentication.UserTokenState;
import managementapp.managementapp.models.*;
import managementapp.managementapp.repositories.RoleRepository;
import managementapp.managementapp.repositories.UserAppRepository;
import managementapp.managementapp.security.TokenBasedAuthentication;
import managementapp.managementapp.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserAppService userAppService;
    @Autowired
    private UserAppRepository userAppRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private ActivationTokenService activationTokenService;
    @Autowired
    private LoginTokenService loginTokenService;
    @Autowired
    private UserDetailsService userDetailsService;

    public ResponseEntity<?> registerUser(RegistrationRequestDto registrationRequest){
        try{
            if(userAppService.userExistsByUsernameOrEmail(registrationRequest.getUsername(), registrationRequest.getEmail())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("A user with that username or email already exists.");
            }
            Role role = roleRepository.findByName(registrationRequest.getRoleName());
            if(role == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid role name.");
            }
            UserApp userToRegister = UserApp.builder()
                    .email(registrationRequest.getEmail())
                    .username(registrationRequest.getUsername())
                    .password(passwordEncoder.encode(registrationRequest.getPassword()))
                    .role(role)
                    .active(false)
                    .build();
            switch (role.getName()){
                case "SOFTWARE_ENGINEER":
                    userAppService.save(new SoftwareEngineer(userToRegister));
                    break;
                case "PROJECT_MANAGER":
                    userAppService.save(new ProjectManager(userToRegister));
                    break;
                case "HUMAN_RESOURCES_MANAGER":
                    userAppService.save(new HumanResourcesManager(userToRegister));
                    break;
                default:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Invalid role name.");
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during registration.");
        }
    }

    public ResponseEntity<?> login(LoginRequestDto loginRequestDto){
        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se AuthenticationException
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getUsername(), loginRequestDto.getPassword()));
            // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security kontekst
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Kreiraj token za tog korisnika
            UserApp user = (UserApp) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user.getUsername(), user.getRole().getName());
            int expiresIn = tokenUtils.getExpiredIn();
            // Vrati token kao odgovor na uspesnu autentifikaciju
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Bad credentials.");
        }
    }

    public ResponseEntity<?> sendLoginTokenToEmail(String email){
        try{
            UserApp userApp = userAppRepository.findByEmail(email);
            if(userApp == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User with does not exist with email: " + email);
            }
            if(!userApp.getActive()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Account with this email is not active.");
            }
            UUID loginToken = loginTokenService.generateLoginToken(userApp);
            //TODO send this token via email
            String loginLink = "http://localhost:8081/auth/login?token="+loginToken;
            return ResponseEntity.status(HttpStatus.OK)
                    .body(loginLink);
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while trying to send a token for login.");
        }
    }

    public String sendVerificationEmail(UserApp userApp){
        UUID verificationToken = activationTokenService.generateActivationToken(userApp);
        //TODO send this token via email
        return "http://localhost:8081/auth/activate?token="+verificationToken;
    }

    public ResponseEntity<?> activate(UUID token){
        try{
            return activationTokenService.activate(token);
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during activation of account.");
        }
    }

    public ResponseEntity<?> loginViaToken(UUID token){
        try{
            UserApp userToLogin = loginTokenService.validateTokenAndGetUser(token);
            if(userToLogin == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token is not valid.");
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(userToLogin.getUsername());
            TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserApp user = (UserApp) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user.getUsername(), user.getRole().getName());
            int expiresIn = tokenUtils.getExpiredIn();
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during login via token.");
        }
    }
}
