package managementapp.managementapp.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log {
    @Id
    @SequenceGenerator(name = "logSeqGen", sequenceName = "logSeqGen", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "logSeqGen")
    @Column(name="id", unique=true, nullable=false)
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private LogLevel logLevel;

    @Column
    private LocalDateTime createdDate;

    @Column
    private String message;

    @ManyToOne
    private UserApp userApp;
}
