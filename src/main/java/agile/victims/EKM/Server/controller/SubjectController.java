package agile.victims.EKM.Server.controller;

import agile.victims.EKM.Server.dto.SubjectDTO;
import agile.victims.EKM.Server.entity.Subject;
import agile.victims.EKM.Server.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webApi/subjects")
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/getSubjects/{lessonName}")
    public ResponseEntity<List<Subject>> getSubjects(@PathVariable String lessonName) {
        List<Subject> subjects = subjectRepository.findByLessonName(lessonName);
        return ResponseEntity.ok(subjects);
    }

    @PostMapping("/addSubject")
    public ResponseEntity<Subject> addSubject(@RequestBody SubjectDTO subjectDTO) {
        Subject subject = new Subject();
        subject.setLessonName(subjectDTO.getLessonName());
        subject.setSubjectName(subjectDTO.getSubjectName());
        Subject savedSubject = subjectRepository.save(subject);
        return ResponseEntity.ok(savedSubject);
    }

    @DeleteMapping("/deleteSubject/{lessonName}/{subjectName}")
    public ResponseEntity<Void> deleteSubject(@PathVariable String lessonName, @PathVariable String subjectName) {
        subjectRepository.deleteByLessonNameAndSubjectName(lessonName, subjectName);
        return ResponseEntity.ok().build();
    }
} 