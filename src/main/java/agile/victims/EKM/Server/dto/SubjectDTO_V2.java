package agile.victims.EKM.Server.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SubjectDTO_V2 {
    private Long id;
    private String subjectName;

    public SubjectDTO_V2(Long id, String subjectName) {
        this.id = id;
        this.subjectName = subjectName;
    }
}
