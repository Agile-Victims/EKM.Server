package agile.victims.EKM.Server.repository;

import agile.victims.EKM.Server.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByLessonName(String lessonName);
    void deleteByLessonNameAndSubjectName(String lessonName, String subjectName);
} 