package managementapp.managementapp.services;

import managementapp.managementapp.models.Log;
import managementapp.managementapp.models.LogLevel;
import managementapp.managementapp.models.UserApp;
import managementapp.managementapp.repositories.LogRepository;
import managementapp.managementapp.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogService {
    @Autowired
    LogRepository logRepository;
    @Autowired
    UserAppRepository userAppRepository;

    public void TRACE(String message){
        insertLog(message, LogLevel.TRACE);
    }

    public void DEBUG(String message){
        insertLog(message, LogLevel.DEBUG);
    }

    public void INFO(String message) { insertLog(message, LogLevel.INFO); }

    public void WARN(String message){
        insertLog(message, LogLevel.WARN);
    }

    public void ERROR(String message){
        insertLog(message, LogLevel.ERROR);
    }

    public void FATAL(String message){
        insertLog(message, LogLevel.FATAL);
    }

    private void insertLog(String message, LogLevel logLevel){
        UserApp loggedInUser = userAppRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Log log = Log.builder()
                .logLevel(logLevel)
                .message(message)
                .createdDate(LocalDateTime.now())
                .userApp(loggedInUser)
                .build();
        logRepository.save(log);
    }

    public ResponseEntity<?> getUserLogs(Long userId){
        UserApp userApp = userAppRepository.findById(userId).orElse(null);
        if(userApp == null){
            ERROR("User with id: " + userId + " does not exist.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with id: " + userId + " does not exist.");
        }
        return new ResponseEntity<>(logRepository.findByUserAppIdOrderByCreatedDateDesc(userId), HttpStatus.OK);
    }

    public ResponseEntity<?> getCriticalLogs(){
        return new ResponseEntity<>(logRepository.findCriticalLogs(), HttpStatus.OK);
    }
}
