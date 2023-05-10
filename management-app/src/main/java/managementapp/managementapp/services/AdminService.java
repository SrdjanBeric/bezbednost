package managementapp.managementapp.services;

import managementapp.managementapp.dtos.common.UserAppDto;
import managementapp.managementapp.models.UserApp;
import managementapp.managementapp.repositories.AdminRepository;
import managementapp.managementapp.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserAppRepository userAppRepository;

    public ResponseEntity<?> getUsersToActivate(){
        try{
            List<UserApp> usersToActivate = userAppRepository.findUserAppsByActive(false);
            List<UserAppDto> userAppDtoList = new ArrayList<>();
            for (UserApp userApp: usersToActivate) {
                UserAppDto userAppDto = UserAppDto.builder()
                        .id(userApp.getId())
                        .email(userApp.getEmail())
                        .username(userApp.getUsername())
                        .active(userApp.getActive())
                        .roleName(userApp.getRole().getName())
                        .build();
                userAppDtoList.add(userAppDto);
            }
            return new ResponseEntity<>(userAppDtoList, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while getting all users to activate.");
        }
    }

    public ResponseEntity<?> activateUser(Long userId){
        try{
            UserApp userApp = userAppRepository.findById(userId).orElse(null);
            if(userApp == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User with id: " + userId + " does not exist");
            }
            userApp.setActive(true);
            userAppRepository.save(userApp);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while activating user with id: " + userId);
        }
    }
}
