package managementapp.managementapp.services;

import managementapp.managementapp.repositories.SoftwareEngineerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoftwareEngineerService {
    @Autowired
    private SoftwareEngineerRepository softwareEngineerRepository;
}
