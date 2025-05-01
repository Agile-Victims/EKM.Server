package agile.victims.EKM.Server.repository;

import agile.victims.EKM.Server.entity.Teacher;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmailAndPassword(String email, String password);
    Optional<Teacher> findByEmail(String email);
    @Modifying
    @Transactional
    @Query("UPDATE Teacher t SET t.classes = :classes WHERE t.email = :email")
    int updateTeacherClasses(@Param("email") String email, @Param("classes") String classes);
    @Query("SELECT t.classes FROM Teacher t WHERE t.email = :email")
    String getClassByEmail(@Param("email") String email);

}
