package agile.victims.EKM.Server.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class LoginRequest {

    private String email;
    private String password;
}
