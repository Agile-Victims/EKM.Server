package agile.victims.EKM.Server.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "exams")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String examName;
    private int turkishQuestionCount;
    private int mathQuestionCount;
    private int scienceQuestionCount;
    private int historyQuestionCount;
    private int religionQuestionCount;
    private int foreignLanguageQuestionCount;
    private boolean isActive;
} 