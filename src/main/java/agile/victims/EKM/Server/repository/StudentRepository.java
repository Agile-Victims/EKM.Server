package agile.victims.EKM.Server.repository;

import agile.victims.EKM.Server.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends  JpaRepository<Student, Long> {
    Optional<Student> findByEmailAndPassword(String email, String password);
    Optional<Student> findByEmail(String email);
}
