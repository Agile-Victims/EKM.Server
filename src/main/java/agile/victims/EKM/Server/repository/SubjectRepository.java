package agile.victims.EKM.Server.repository;

import agile.victims.EKM.Server.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByLessonName(String lessonName);
    @Query("SELECT s FROM Subject s WHERE s.lessonName = :lessonName AND s.subjectName = :subjectName")
    Optional<Subject> findByLessonNameAndSubjectName(@Param("lessonName") String lessonName, @Param("subjectName") String subjectName);


    void deleteByLessonNameAndSubjectName(String lessonName, String subjectName);
} 