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
        switch (loggedInUser.getRole().getName()) {
            case "ADMIN", "HUMAN_RESOURCES_MANAGER" -> projects = projectRepository.findAll();
            case "SOFTWARE_ENGINEER" -> projects = projectRepository.findAllBySoftwareEngineerId(loggedInUser.getId());
            case "PROJECT_MANAGER" -> projects = projectRepository.findAllByProjectManagerId(loggedInUser.getId());
            default -> {
            }
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

    public ResponseEntity<?> removeEngineer(Long projectId, Long softwareEngineerId){
        try{
            UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            SoftwareEngineer engineerToRemove = softwareEngineerRepository.findById(softwareEngineerId).orElse(null);
            if(engineerToRemove == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Software engineer with id " + softwareEngineerId + " does not exist");
            }
            Project project = projectRepository.findById(projectId).orElse(null);
            if(project == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Project with id " + projectId + " does not exist");
            }
            if(!isManagerOnProject(project, loggedInUser)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You are not allowed to remove engineers on project with id " + projectId);
            }
            SoftwareEngineerProject softwareEngineerProject = softwareEngineerProjectRepository.findByProjectIdAndSoftwareEngineerId(projectId, softwareEngineerId);
            if(softwareEngineerProject == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Engineer does not work on this project.");
            }
            softwareEngineerProject.setActive(false);
            softwareEngineerProjectRepository.save(softwareEngineerProject);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while removing engineer from project.");
        }
    }

    private boolean isManagerOnProject(Project project, UserApp userApp){
        if(userApp.getRole().getName().equals("ADMIN")){
            return true;
        }else if(project.getProjectManager().getId() == userApp.getId()){
            return true;
        }
        return false;
    }

    public ResponseEntity<?> getAvailableEngineersForProject(Long projectId){
        try{
            UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            Project project = projectRepository.findById(projectId).orElse(null);
            if(project == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Project does not exist.");
            }
            if(loggedInUser.getRole().getName().equals("PROJECT_MANAGER") && project.getProjectManager().getId() != loggedInUser.getId()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You do not have permissions to access this project.");
            }
            List<UserApp> allUsers = userAppRepository.findUserAppsByActive(true);
            List<UserApp> availableUsers = new ArrayList<>();
            for (UserApp userIterator : allUsers){
                boolean exists = false;
                if(!userIterator.getRole().getName().equals("SOFTWARE_ENGINEER")){
                    continue;
                }
                for(SoftwareEngineerProject task : project.getTaskDescriptions()){
                    if(task.getSoftwareEngineer().getId() == userIterator.getId()){
                        exists = true;
                        break;
                    }
                }
                if(!exists){
                    availableUsers.add(userIterator);
                }
            }
            return new ResponseEntity<>(availableUsers, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while removing engineer from project.");
        }
    }
}
