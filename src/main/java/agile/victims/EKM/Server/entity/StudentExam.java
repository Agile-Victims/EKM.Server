package agile.victims.EKM.Server.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "student_exams")
public class StudentExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    private boolean isCompleted;
} 