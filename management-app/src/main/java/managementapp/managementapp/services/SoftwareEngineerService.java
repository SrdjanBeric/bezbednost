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


    //kreirane liste vestina
    public void skillsCreate(List<String> newSkills) {
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(loggedInUser.getRole().equals("SOFTWARE_ENGINEER")){
            SoftwareEngineer softwareEngineer = (SoftwareEngineer) loggedInUser;
            softwareEngineer.setSkills(newSkills);
            softwareEngineerRepository.save(softwareEngineer);
        }else{
            throw new RuntimeException("Can't create list of skills, because user does not Software engineer");
        }

    }

    //update list of skils
    public void skillsUpdate(List<String>updateSkills){
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(loggedInUser.getRole().equals("SOFTWARE_ENGINEER")){
            SoftwareEngineer softwareEngineer = (SoftwareEngineer) loggedInUser;
            List<String>currentSkills = softwareEngineer.getSkills();
            currentSkills.addAll(updateSkills);
            softwareEngineer.setSkills(currentSkills);
            softwareEngineerRepository.save(softwareEngineer);
        }else{
            throw new RuntimeException("Can't update list of skills, because user does not Software engineer");
        }

    }

    //citanje celokupne liste
    public List<String>skillsList(){
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        SoftwareEngineer softwareEngineer = (SoftwareEngineer) loggedInUser;
        List<String>skills = softwareEngineer.getSkills();
        List<String>skillsList=new ArrayList<>();

        for (String skill :skills
             ) {
            skillsList.add(skill);
        }
        return skillsList;
    }


}
