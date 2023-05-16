package managementapp.managementapp.services;

import managementapp.managementapp.dtos.project.AddManagerToProjectDto;
import managementapp.managementapp.models.Project;
import managementapp.managementapp.models.ProjectManager;
import managementapp.managementapp.models.UserApp;
import managementapp.managementapp.repositories.ProjectManagerRepository;
import managementapp.managementapp.repositories.ProjectRepository;
import managementapp.managementapp.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectManagerService {

    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    @Autowired
    private UserAppRepository userAppRepository;

    @Autowired
    private ProjectRepository projectRepository;


//    public void addManagerToProject(Long projectId){
//        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        Optional<Project> projectOptional = projectRepository.findById(projectId);
//        List<ProjectManager> projectManagers = projectManagerRepository.findAll();
//        if(projectOptional.isPresent()){
//            for (ProjectManager manager:projectManagers
//                 ) {
//                projectOptional.get().setProjectManager(manager);
//            }
//
//        }
//
//
//    }

}
