package agile.victims.EKM.Server.responses;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ExamResult {
    public Long studentId;
    public String studentName;
    public String studentSurname;
    public String studentEmail;
    public Double turkishNet;
    public Double mathNet;
    public Double scienceNet;
    public Double historyNet;
    public Double religionNet;
    public Double foreignLanguageNet;
}
