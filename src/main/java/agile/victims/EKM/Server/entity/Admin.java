package agile.victims.EKM.Server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admins")
@Getter @Setter
@NoArgsConstructor
//@AllArgsConstructor
public class Admin extends User {
    public Admin(User user) {
        super(user);
    }

    public Admin(String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }
}