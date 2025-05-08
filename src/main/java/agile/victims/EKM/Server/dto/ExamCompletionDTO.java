package agile.victims.EKM.Server.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ExamCompletionDTO {
    private Long examId;
    private String studentEmail;
    private int turkishCorrectCount;
    private int mathCorrectCount;
    private int scienceCorrectCount;
    private int historyCorrectCount;
    private int relegionCorrectCount;
    private int foreignLanguageCorrectCount;
    private int turkishWrongCount;
    private int mathWrongCount;
    private int scienceWrongCount;
    private int historyWrongCount;
    private int relegionWrongCount;
    private int foreignLanguageWrongCount;
}
