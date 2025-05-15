package agile.victims.EKM.Server.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateExamRequest {
    private String examName;
    private int turkishQuestionCount;
    private int mathQuestionCount;
    private int scienceQuestionCount;
    private int historyQuestionCount;
    private int religionQuestionCount;
    private int foreignLanguageQuestionCount;
}
