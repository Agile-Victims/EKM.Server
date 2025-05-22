package agile.victims.EKM.Server.requests;

import lombok.Data;

@Data
public class CreateExamRequest {
    private String examName;
    private int turkishQuestionCount;
    private int mathQuestionCount;
    private int scienceQuestionCount;
    private int historyQuestionCount;
    private int religionQuestionCount;
    private int foreignLanguageQuestionCount;
    private String turkishSubjects;
    private String mathSubjects;
    private String scienceSubjects;
    private String historySubjects;
    private String religionSubjects;
    private String foreignLanguageSubjects;
}
