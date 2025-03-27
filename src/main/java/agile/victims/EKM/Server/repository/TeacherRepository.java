package agile.victims.EKM.Server.repository;

import agile.victims.EKM.Server.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmailAndPassword(String email, String password);
    Optional<Teacher> findByEmail(String email);
}
