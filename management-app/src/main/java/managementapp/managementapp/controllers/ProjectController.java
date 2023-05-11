package managementapp.managementapp.controllers;

import managementapp.managementapp.dtos.project.AddEngineerToProjectRequestDto;
import managementapp.managementapp.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/all")
    public ResponseEntity<?> allUsers(){
        try{
            return projectService.getAllProjects();
        }catch (Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addEngineer")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PROJECT_MANAGER')")
    public ResponseEntity<?> addEngineer(@RequestBody @Valid AddEngineerToProjectRequestDto addEngineerToProjectRequestDto){
        try{
            return projectService.addEngineer(addEngineerToProjectRequestDto);
        }catch (Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
