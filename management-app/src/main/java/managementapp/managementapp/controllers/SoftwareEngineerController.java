package managementapp.managementapp.controllers;

import managementapp.managementapp.models.UserApp;
import managementapp.managementapp.services.SoftwareEngineerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/softwareEngineer")
public class SoftwareEngineerController {

    @Autowired
    public final SoftwareEngineerService softwareEngineerService;


    public SoftwareEngineerController(SoftwareEngineerService softwareEngineerService) {
        this.softwareEngineerService = softwareEngineerService;
    }

    @GetMapping("/allSkills")
    @PreAuthorize("hasAnyAuthority('SOFTWARE_ENGINEER')")
    public ResponseEntity<?> allSkills(){
        try{
           softwareEngineerService.skillsList();
           return new  ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return  new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateSkills")
    @PreAuthorize("hasAnyAuthority('SOFTWARE_ENGINEER')")
    public ResponseEntity<?>updateUser(@RequestBody List<String> updateSkills){
        try{
            softwareEngineerService.skillsUpdate(updateSkills);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createSkills")
    @PreAuthorize("hasAnyAuthority('SOFTWARE_ENGINEER')")
    public ResponseEntity<?>createSkills(@RequestBody List<String> createSkills){
        try{
            softwareEngineerService.skillsCreate(createSkills);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
