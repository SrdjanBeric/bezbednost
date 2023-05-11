package managementapp.managementapp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoftwareEngineerProject {
    @Id
    @SequenceGenerator(name = "softwareEngineerProjectSeqGen", sequenceName = "softwareEngineerProjectSeqGen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "softwareEngineerProjectSeqGen")
    @Column(name="id", unique=true, nullable=false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private SoftwareEngineer softwareEngineer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIncludeProperties({"id", "name"})
    private Project project;

    @Column
    private String workDescription;

    // Datum kada je Software engineer poceo da radi na projektu
    @Column
    @DateTimeFormat(pattern = "dd-MM-yyyy", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date startDate;

    // Datum kada je Software engineer zavrsio sa radom na projektu
    @Column
    @DateTimeFormat(pattern = "dd-MM-yyyy", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date endDate;
}
