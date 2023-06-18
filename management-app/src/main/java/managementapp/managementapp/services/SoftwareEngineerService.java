package managementapp.managementapp.services;

import managementapp.managementapp.dtos.project.UserAppDto;
import managementapp.managementapp.models.Role;
import managementapp.managementapp.models.SoftwareEngineer;
import managementapp.managementapp.models.UserApp;
import managementapp.managementapp.repositories.RoleRepository;
import managementapp.managementapp.repositories.SoftwareEngineerRepository;
import managementapp.managementapp.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SoftwareEngineerService {
    @Autowired
    private SoftwareEngineerRepository softwareEngineerRepository;

    @Autowired
    private UserAppRepository userAppRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    LogService logService;

    //kreiranje liste vestina
    public void skillsCreate(List<String> newSkills) {
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        SoftwareEngineer softwareEngineer = (SoftwareEngineer) loggedInUser;
        softwareEngineer.setSkills(newSkills);
        softwareEngineerRepository.save(softwareEngineer);
        logService.INFO("Created skills list for software engineer: " + loggedInUser.getUsername());
    }

    //update list of skils
    public void skillsUpdate(List<String>updateSkills){
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        SoftwareEngineer softwareEngineer = (SoftwareEngineer) loggedInUser;
        softwareEngineer.setSkills(updateSkills);
        softwareEngineerRepository.save(softwareEngineer);
        logService.INFO("Updated skills list for software engineer: " + loggedInUser.getUsername());
    }

    //citanje celokupne liste
    public List<String>getSkillsList() {
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        SoftwareEngineer softwareEngineer = (SoftwareEngineer) loggedInUser;
        ArrayList<String>skills = new ArrayList<>(softwareEngineer.getSkills());
        logService.INFO("Retrieved skills list for software engineer: " + loggedInUser.getUsername());
        return  skills;
    }

    public void update(UserAppDto userAppDto){
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Role role = roleRepository.findByName(userAppDto.getRoleName());
        loggedInUser.setUsername(userAppDto.getUsername());
        loggedInUser.setPassword(passwordEncoder.encode((userAppDto.getPassword() + loggedInUser.getPasswordSalt())));
        loggedInUser.setAddress(userAppDto.getAddress());
        loggedInUser.setRole(role);
        loggedInUser.setActive(userAppDto.getActive());

        userAppRepository.save(loggedInUser);
        logService.INFO("Updated software engineer: " + loggedInUser.getUsername());
    }





}
