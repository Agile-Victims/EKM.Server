package agile.victims.EKM.Server.requests;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUpRequest {
    private String email;
    private String password;
    private String name;
    private String surname;
}
