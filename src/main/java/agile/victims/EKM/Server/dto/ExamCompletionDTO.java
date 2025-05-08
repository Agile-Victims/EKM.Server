package agile.victims.EKM.Server.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ExamCompletionDTO {
    private Long studentId;
    private String studentName;
    private String studentSurname;
    private String studentEmail;
    private LocalDateTime completionDate;
    private String examName;
} 