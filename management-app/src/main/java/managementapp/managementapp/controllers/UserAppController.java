package managementapp.managementapp.controllers;

import managementapp.managementapp.models.UserApp;
import managementapp.managementapp.services.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserAppController {

    @Autowired
    private UserAppService userAppService;

    @GetMapping()
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("success");
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'HUMAN_RESOURCES_MANAGER')")
    public ResponseEntity<?> allUsers(){
        try{
            return userAppService.getAllUsers();
        }catch (Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'HUMAN_RESOURCES_MANAGER', 'SOFTWARE_ENGINEER', 'PROJECT_MANAGER')")
    public ResponseEntity<?>updateUser(@RequestBody UserApp userApp){
        try{
            userAppService.update(userApp);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
