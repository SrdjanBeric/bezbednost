package managementapp.managementapp.services;

import managementapp.managementapp.models.SoftwareEngineer;
import managementapp.managementapp.models.UserApp;
import managementapp.managementapp.repositories.SoftwareEngineerRepository;
import managementapp.managementapp.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SoftwareEngineerService {
    @Autowired
    private SoftwareEngineerRepository softwareEngineerRepository;

    @Autowired
    private UserAppRepository userAppRepository;


    //kreiranje liste vestina
    public void skillsCreate(List<String> newSkills) {
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            SoftwareEngineer softwareEngineer = (SoftwareEngineer) loggedInUser;
            softwareEngineer.setSkills(newSkills);
            softwareEngineerRepository.save(softwareEngineer);
    }

    //update list of skils
    public void skillsUpdate(List<String>updateSkills){
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        SoftwareEngineer softwareEngineer = (SoftwareEngineer) loggedInUser;
        softwareEngineer.setSkills(updateSkills);
        softwareEngineerRepository.save(softwareEngineer);
    }

    //citanje celokupne liste
    public List<String>getSkillsList() {
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        SoftwareEngineer softwareEngineer = (SoftwareEngineer) loggedInUser;
        ArrayList<String>skills = new ArrayList<>(softwareEngineer.getSkills());
        return  skills;
    }





}
