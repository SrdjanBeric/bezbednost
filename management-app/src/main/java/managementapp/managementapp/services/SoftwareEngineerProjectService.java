package managementapp.managementapp.services;

import managementapp.managementapp.models.*;
import managementapp.managementapp.repositories.ProjectRepository;
import managementapp.managementapp.repositories.SoftwareEngineerProjectRepository;
import managementapp.managementapp.repositories.SoftwareEngineerRepository;
import managementapp.managementapp.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SoftwareEngineerProjectService {

    @Autowired
    private SoftwareEngineerProjectRepository softwareEngineerProjectRepository;

    @Autowired
    private UserAppRepository userAppRepository;

    @Autowired
    private  ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SoftwareEngineerRepository softwareEngineerRepository;
    @Autowired
    LogService logService;
    public List<Project> getAllProjects() {
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Project> projects = new ArrayList<>();

        if (loggedInUser.getRole().getName().equals("SOFTWARE_ENGINEER")) {
            projects = projectRepository.findAllBySoftwareEngineerId(loggedInUser.getId());
        } else {
            projects = projectRepository.findAll();
        }
        logService.INFO("Retrieved all projects for user: " + loggedInUser.getUsername());
        return projects;
    }

    public List<Project> getAllEngineerProject() {
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Project> projectList = new ArrayList<>();
        Optional<SoftwareEngineer> projectEngineer = softwareEngineerRepository.findById(loggedInUser.getId());

        if (projectEngineer.isPresent()) {
            SoftwareEngineer engineer = projectEngineer.get();
            projectList = softwareEngineerProjectRepository.findAllByProjectEngineer(engineer);
        }
        logService.INFO("Retrieved all projects for engineer: " + loggedInUser.getUsername());
        return projectList;
    }


    //nastaviti ovo jos..
//    public void editWorkDescription(String workDescription){
//        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
////       List<>projects = projectService.getAllProjects();
//        if(loggedInUser.getRole().equals("SOFTWARE_ENGINEER")){
//            SoftwareEngineer softwareEngineer = (SoftwareEngineer) loggedInUser;
//
//
//        }
//    }

}
