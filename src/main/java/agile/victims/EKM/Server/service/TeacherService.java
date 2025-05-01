package agile.victims.EKM.Server.service;

import agile.victims.EKM.Server.entity.Teacher;
import agile.victims.EKM.Server.repository.TeacherRepository;
import agile.victims.EKM.Server.requests.GetClassesRequest;
import agile.victims.EKM.Server.requests.SetClassesRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;

    @Transactional
    public ResponseEntity<?> setClasses(SetClassesRequest setClassesRequest) {
        if (setClassesRequest.getClasses() == null || setClassesRequest.getClasses().isEmpty()) {
            throw new RuntimeException("Classes is required");
        }

        if (setClassesRequest.getEmail() == null || setClassesRequest.getEmail().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        int updatedCount = teacherRepository.updateTeacherClasses(
                setClassesRequest.getEmail(),
                setClassesRequest.getClasses()
        );

        if (updatedCount == 0) {
            return ResponseEntity.status(404).body("Teacher with email " + setClassesRequest.getEmail() + " not found.");
        }

        return ResponseEntity.ok("Classes updated successfully");
    }

    public ResponseEntity<?> getTeacher(String email) {
        if (email == null || email.isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        Optional<Teacher> teacher = teacherRepository.findByEmail(email);

        if (teacher.isEmpty()) {
            return ResponseEntity.status(404).body("Teacher with email " + email + " not found.");
        }

        return ResponseEntity.ok(teacher.get());
    }
}
