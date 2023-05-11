package managementapp.managementapp.services;

import managementapp.managementapp.dtos.project.AddEngineerToProjectRequestDto;
import managementapp.managementapp.models.Project;
import managementapp.managementapp.models.SoftwareEngineer;
import managementapp.managementapp.models.SoftwareEngineerProject;
import managementapp.managementapp.models.UserApp;
import managementapp.managementapp.repositories.ProjectRepository;
import managementapp.managementapp.repositories.SoftwareEngineerProjectRepository;
import managementapp.managementapp.repositories.SoftwareEngineerRepository;
import managementapp.managementapp.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserAppRepository userAppRepository;
    @Autowired
    private SoftwareEngineerRepository softwareEngineerRepository;
    @Autowired
    private SoftwareEngineerProjectRepository softwareEngineerProjectRepository;

    public ResponseEntity<?> getAllProjects(){
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Project> projects = new ArrayList<>();
        switch (loggedInUser.getRole().getName()){
            case "ADMIN":
                projects = projectRepository.findAll();
                break;
            case "SOFTWARE_ENGINEER":
                projects = projectRepository.findAllBySoftwareEngineerId(loggedInUser.getId());
                break;
            case "PROJECT_MANAGER":
                projects = projectRepository.findAllByProjectManagerId(loggedInUser.getId());
                break;
            case "HUMAN_RESOURCES_MANAGER":
                projects = projectRepository.findAll();
                break;
            default:
                break;
        }
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    public ResponseEntity<?> addEngineer(AddEngineerToProjectRequestDto addEngineerToProjectRequestDto){
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        SoftwareEngineer engineerToAdd = softwareEngineerRepository.findById(addEngineerToProjectRequestDto.getSoftwareEngineerId()).orElse(null);
        if(engineerToAdd == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Software engineer with id " + addEngineerToProjectRequestDto.getSoftwareEngineerId() + " does not exist");
        }
        Project project = projectRepository.findById(addEngineerToProjectRequestDto.getProjectId()).orElse(null);
        if(project == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Project with id " + addEngineerToProjectRequestDto.getProjectId() + " does not exist");
        }
        if(!isManagerOnProject(project, loggedInUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You are not allowed to add engineers on project with id " + addEngineerToProjectRequestDto.getProjectId());
        }
        SoftwareEngineerProject softwareEngineerProject = SoftwareEngineerProject.builder()
                .softwareEngineer(engineerToAdd)
                .project(project)
                .startDate(addEngineerToProjectRequestDto.getStartDate())
                .endDate(addEngineerToProjectRequestDto.getEndDate())
                .workDescription(addEngineerToProjectRequestDto.getWorkDescription())
                .build();
        softwareEngineerProjectRepository.save(softwareEngineerProject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean isManagerOnProject(Project project, UserApp userApp){
        if(userApp.getRole().getName().equals("ADMIN")){
            return true;
        }else if(project.getProjectManager().getId() == userApp.getId()){
            return true;
        }
        return false;
    }
}
