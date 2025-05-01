package agile.victims.EKM.Server.service;

import agile.victims.EKM.Server.entity.Teacher;
import agile.victims.EKM.Server.repository.TeacherRepository;
import agile.victims.EKM.Server.requests.GetClassesRequest;
import agile.victims.EKM.Server.requests.SetClassesRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<?> getTeacherClasses(GetClassesRequest getClassesRequest) {
        if (getClassesRequest.getEmail() == null || getClassesRequest.getEmail().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        String classes = teacherRepository.getClassByEmail(getClassesRequest.getEmail());

        if (classes == null) {
            return ResponseEntity.status(404).body("Teacher with email " + getClassesRequest.getEmail() + " not found.");
        }

        return ResponseEntity.ok(classes);
    }
}
