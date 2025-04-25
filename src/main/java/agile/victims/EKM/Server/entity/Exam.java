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
    private int relegionQuestionCount;
    private int foreignLanguageQuestionCount;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin createdBy;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<Question> questions;
} 