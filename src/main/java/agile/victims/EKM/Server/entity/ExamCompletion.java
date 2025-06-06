package agile.victims.EKM.Server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "exam_completions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamCompletion {

//    @ManyToOne
//    @JoinColumn(name = "exam_id")
//    private Exam exam;
//
//    @ManyToOne
//    @JoinColumn(name = "student_id")
//    private Student student;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exam_id", nullable = false)
    private Long examId;
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "turkish_correct_count", nullable = false)
    private int turkishCorrectCount;

    @Column(name = "math_correct_count", nullable = false)
    private int mathCorrectCount;

    @Column(name = "science_correct_count", nullable = false)
    private int scienceCorrectCount;

    @Column(name = "history_correct_count", nullable = false)
    private int historyCorrectCount;

    @Column(name = "religion_correct_count", nullable = false)
    private int religionCorrectCount;

    @Column(name = "foreign_language_correct_count", nullable = false)
    private int foreignLanguageCorrectCount;

    @Column(name = "turkish_wrong_count", nullable = false)
    private int turkishWrongCount;

    @Column(name = "math_wrong_count", nullable = false)
    private int mathWrongCount;

    @Column(name = "science_wrong_count", nullable = false)
    private int scienceWrongCount;

    @Column(name = "history_wrong_count", nullable = false)
    private int historyWrongCount;

    @Column(name = "religion_wrong_count", nullable = false)
    private int religionWrongCount;

    @Column(name = "foreign_language_wrong_count", nullable = false)
    private int foreignLanguageWrongCount;

    @Column(name = "completion_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date completionDate;

    @Column(name = "turkish_wrong_subjects", nullable = false)
    private String turkishWrongSubjects;

    @Column(name = "math_wrong_subjects", nullable = false)
    private String mathWrongSubjects;

    @Column(name = "science_wrong_subjects", nullable = false)
    private String scienceWrongSubjects;

    @Column(name = "history_wrong_subjects", nullable = false)
    private String historyWrongSubjects;

    @Column(name = "religion_wrong_subjects", nullable = false)
    private String religionWrongSubjects;

    @Column(name = "foreign_language_wrong_subjects", nullable = false)
    private String foreignLanguageWrongSubjects;

    @Column(name = "turkish_empty_subjects", nullable = false)
    private String turkishEmptySubjects;

    @Column(name = "math_empty_subjects", nullable = false)
    private String mathEmptySubjects;

    @Column(name = "science_empty_subjects", nullable = false)
    private String scienceEmptySubjects;

    @Column(name = "history_empty_subjects", nullable = false)
    private String historyEmptySubjects;

    @Column(name = "religion_empty_subjects", nullable = false)
    private String religionEmptySubjects;

    @Column(name = "foreign_language_empty_subjects", nullable = false)
    private String foreignLanguageEmptySubjects;


} 