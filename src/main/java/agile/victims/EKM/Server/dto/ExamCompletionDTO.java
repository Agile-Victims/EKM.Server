package agile.victims.EKM.Server.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExamCompletionDTO {
    private Long examId;
    private String studentEmail;
    private int turkishCorrectCount;
    private int mathCorrectCount;
    private int scienceCorrectCount;
    private int historyCorrectCount;
    private int religionCorrectCount;
    private int foreignLanguageCorrectCount;
    private int turkishWrongCount;
    private int mathWrongCount;
    private int scienceWrongCount;
    private int historyWrongCount;
    private int religionWrongCount;
    private int foreignLanguageWrongCount;
    private String turkishWrongSubjects;
    private String mathWrongSubjects;
    private String scienceWrongSubjects;
    private String historyWrongSubjects;
    private String religionWrongSubjects;
    private String foreignLanguageWrongSubjects;
    private String turkishEmptySubjects;
    private String mathEmptySubjects;
    private String scienceEmptySubjects;
    private String historyEmptySubjects;
    private String religionEmptySubjects;
    private String foreignLanguageEmptySubjects;
}
