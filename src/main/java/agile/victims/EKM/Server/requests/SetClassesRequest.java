package agile.victims.EKM.Server.requests;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class SetClassesRequest {
    private String email;
    private String classes;
}
