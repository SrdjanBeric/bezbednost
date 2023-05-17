package managementapp.managementapp.controllers;

import managementapp.managementapp.dtos.project.AddEngineerToProjectRequestDto;
import managementapp.managementapp.dtos.project.ProjectDTO;
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

    @GetMapping("{projectId}/engineer/available")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PROJECT_MANAGER')")
    public ResponseEntity<?> getAvailableEngineers(@PathVariable Long projectId){
        try{
            return projectService.getAvailableEngineersForProject(projectId);
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

    @DeleteMapping("{projectId}/removeEngineer/{softwareEngineerId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PROJECT_MANAGER')")
    public ResponseEntity<?> removeEngineer(@PathVariable Long projectId, @PathVariable Long softwareEngineerId){
        try{
            return projectService.removeEngineer(projectId, softwareEngineerId);
        }catch (Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addProject")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> addProject(@RequestBody ProjectDTO projectDTO){
        try{
            projectService.createProject(projectDTO);
        }catch (Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Project is successfully  created.");
    }
}
