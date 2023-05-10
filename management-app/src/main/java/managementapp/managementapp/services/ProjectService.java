package managementapp.managementapp.services;

import managementapp.managementapp.models.Project;
import managementapp.managementapp.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public ResponseEntity<?> getAllProjects(){
        List<Project> projects = projectRepository.findAll();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
}
