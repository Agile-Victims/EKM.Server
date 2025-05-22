package agile.victims.EKM.Server.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String lessonName;
    private String subjectName;
} 