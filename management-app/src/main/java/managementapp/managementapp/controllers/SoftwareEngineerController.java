package managementapp.managementapp.controllers;

import managementapp.managementapp.dtos.project.UserAppDto;
import managementapp.managementapp.models.Project;
import managementapp.managementapp.models.SoftwareEngineerProject;
import managementapp.managementapp.services.SoftwareEngineerProjectService;
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

    @Autowired
    public  SoftwareEngineerProjectService softwareEngineerProjectService;


    public SoftwareEngineerController(SoftwareEngineerService softwareEngineerService) {
        this.softwareEngineerService = softwareEngineerService;
    }

    @GetMapping("/allSkills")
    @PreAuthorize("hasAnyAuthority('SOFTWARE_ENGINEER')")
    public ResponseEntity<?> allSkills(){
        try {
            List<String> skills = softwareEngineerService.getSkillsList();
            System.out.println(skills);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }

    @PutMapping("/updateSkills")
    @PreAuthorize("hasAnyAuthority('SOFTWARE_ENGINEER')")
    public ResponseEntity<?>updateSkills(@RequestBody List<String> updateSkills){
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

    @PutMapping("/updateEngineer")
    @PreAuthorize("hasAnyAuthority('SOFTWARE_ENGINEER')")
    public ResponseEntity<?>updateUser(@RequestBody UserAppDto userAppDto){
        try{
            softwareEngineerService.update(userAppDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allEngineerProject")
    @PreAuthorize("hasAnyAuthority('SOFTWARE_ENGINEER')")
    public List<Project> getAllEngineerProjectProject(){
        return softwareEngineerProjectService.getAllEngineerProject();
    }
}
