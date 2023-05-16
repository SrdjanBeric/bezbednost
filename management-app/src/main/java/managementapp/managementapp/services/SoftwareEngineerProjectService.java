package managementapp.managementapp.services;

import managementapp.managementapp.models.Project;
import managementapp.managementapp.models.SoftwareEngineer;
import managementapp.managementapp.models.SoftwareEngineerProject;
import managementapp.managementapp.models.UserApp;
import managementapp.managementapp.repositories.ProjectRepository;
import managementapp.managementapp.repositories.SoftwareEngineerProjectRepository;
import managementapp.managementapp.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

//    public List<Project> getProjects() {
//        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        List<Project> projects = new ArrayList<>();
//
//        if (loggedInUser.getRole().getName().equals("SOFTWARE_ENGINEER")) {
//            projects = projectRepository.findAllBySoftwareEngineerId(loggedInUser.getId());
//        } else {
//            projects = projectRepository.findAll();
//        }
//
//        return projects;
//    }
//
//
//    //nastaviti ovo jos..
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
