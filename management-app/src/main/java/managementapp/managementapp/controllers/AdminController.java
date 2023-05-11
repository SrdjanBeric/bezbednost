package managementapp.managementapp.controllers;

import managementapp.managementapp.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/usersToActivate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getUsersToActivate(){
        try{
            return adminService.getUsersToActivate();
        }catch (Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/activateUser/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> activateUser(@PathVariable Long userId){
        try{
            return adminService.approveRegistrationRequest(userId);
        }catch (Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
