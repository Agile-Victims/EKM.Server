package agile.victims.EKM.Server.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String subject;
    private int questionNumber;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;
} 