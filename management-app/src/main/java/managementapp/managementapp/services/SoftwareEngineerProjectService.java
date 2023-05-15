package managementapp.managementapp.services;

import managementapp.managementapp.models.Project;
import managementapp.managementapp.models.SoftwareEngineer;
import managementapp.managementapp.models.UserApp;
import managementapp.managementapp.repositories.ProjectRepository;
import managementapp.managementapp.repositories.SoftwareEngineerProjectRepository;
import managementapp.managementapp.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareEngineerProjectService {

    @Autowired
    private SoftwareEngineerProjectRepository softwareEngineerProjectRepository;

    @Autowired
    private UserAppRepository userAppRepository;

//    @Autowired
//    private final ProjectService projectService;

    @Autowired
    private  ProjectRepository projectRepository;

//    private SoftwareEngineerProjectService(SoftwareEngineerProjectRepository softwareEngineerProjectRepository, UserAppRepository userAppRepository, ProjectService projectService, ProjectRepository projectRepository) {
//        this.softwareEngineerProjectRepository = softwareEngineerProjectRepository;
//        this.userAppRepository = userAppRepository;
////        this.projectService = projectService;
//        this.projectRepository = projectRepository;
//    }


    //nastaviti ovo jos..
    public void editWorkDescription(String workDescription){
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Project> projects = projectRepository.findAllBySoftwareEngineerId(loggedInUser.getId());
        if(loggedInUser.getRole().equals("SOFTWARE_ENGINEER")){
            SoftwareEngineer softwareEngineer = (SoftwareEngineer) loggedInUser;

        }
    }

}
