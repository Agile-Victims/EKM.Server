package agile.victims.EKM.Server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "students")
@Getter @Setter
//@NoArgsConstructor @AllArgsConstructor
public class Student extends User {

//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Goal> goals;
//
//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ExamResult> examResults;
}