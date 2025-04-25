package agile.victims.EKM.Server.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "teachers")
@Getter @Setter
@NoArgsConstructor
//@AllArgsConstructor
public class Teacher extends User {
    public Teacher(String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }

//    public Teacher(User user) {
//        super(user);
//    }

//    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Goal> assignedGoals;
}