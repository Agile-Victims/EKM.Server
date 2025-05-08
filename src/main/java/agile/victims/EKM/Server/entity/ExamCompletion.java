package agile.victims.EKM.Server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "exam_completions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamCompletion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "exam_id", nullable = false)
    private Long examId;

    @Column(nullable = false)
    private LocalDateTime completionDate;

    @Column(nullable = false)
    private boolean isCompleted;
} 