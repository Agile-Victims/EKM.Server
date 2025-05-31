package agile.victims.EKM.Server.controller;

import agile.victims.EKM.Server.dto.SubjectDTO;
import agile.victims.EKM.Server.entity.Subject;
import agile.victims.EKM.Server.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @PostMapping("/deleteSubject")
    public ResponseEntity<Map<String, Object>> deleteSubject(@RequestBody SubjectDTO subjectDTO) {
        Optional<Subject> subject = subjectRepository.findByLessonNameAndSubjectName(subjectDTO.getLessonName(), subjectDTO.getSubjectName());

        if (subject.isPresent()) {
            subjectRepository.delete(subject.get());
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Konu silindi.");
            return ResponseEntity.ok(result);
        } else {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "Silinecek konu bulunamadı");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

} 