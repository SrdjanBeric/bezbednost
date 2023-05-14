package managementapp.managementapp.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    private UUID id;
    @Column
    private String hmacValue;
    @ManyToOne
    private UserApp userApp;
    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateTimeStart;
    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateTimeEnd;

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(getDateTimeEnd());
    }
}
