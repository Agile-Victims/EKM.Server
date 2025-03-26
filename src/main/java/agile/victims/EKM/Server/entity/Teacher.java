package agile.victims.EKM.Server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "teachers")
@Getter @Setter
//@NoArgsConstructor @AllArgsConstructor
public class Teacher extends User {

//    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Goal> assignedGoals;
}