package agile.victims.EKM.Server.service;

import agile.victims.EKM.Server.entity.Admin;
import agile.victims.EKM.Server.entity.Student;
import agile.victims.EKM.Server.entity.Teacher;
import agile.victims.EKM.Server.entity.User;
import agile.victims.EKM.Server.repository.AdminRepository;
import agile.victims.EKM.Server.repository.StudentRepository;
import agile.victims.EKM.Server.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired private StudentRepository studentRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private AdminRepository adminRepository;

    public ResponseEntity<String> login(String userType, User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is required");
        }

        return switch (userType.toLowerCase()) {
            case "student" -> studentRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())
                    .map(s -> ResponseEntity.ok("Student Dashboard Access Granted"))
                    .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
            case "teacher" -> teacherRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())
                    .map(t -> ResponseEntity.ok("Teacher Dashboard Access Granted"))
                    .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
            case "admin" -> adminRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())
                    .map(a -> ResponseEntity.ok("Admin Dashboard Access Granted"))
                    .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
            default -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user type");
        };
    }
    public ResponseEntity<String> signup(String userType, User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is required");
        }

        switch (userType.toLowerCase()) {
            case "student":
                Student student = new Student(user);
                // Add additional fields in the feature
                studentRepository.save(student);
                return ResponseEntity.status(HttpStatus.CREATED).body("Student registered successfully");
            case "teacher":
                Teacher teacher = new Teacher(user);
                teacherRepository.save(teacher);
                return ResponseEntity.status(HttpStatus.CREATED).body("Teacher registered successfully");
            case "admin":
                Admin admin = new Admin(user);
                adminRepository.save(admin);
                return ResponseEntity.status(HttpStatus.CREATED).body("Admin registered successfully");
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user type");
        }

    }
}
