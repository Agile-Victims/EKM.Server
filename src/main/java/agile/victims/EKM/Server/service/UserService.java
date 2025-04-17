package agile.victims.EKM.Server.service;

import agile.victims.EKM.Server.entity.Admin;
import agile.victims.EKM.Server.entity.Student;
import agile.victims.EKM.Server.entity.Teacher;
import agile.victims.EKM.Server.entity.User;
import agile.victims.EKM.Server.repository.AdminRepository;
import agile.victims.EKM.Server.repository.StudentRepository;
import agile.victims.EKM.Server.repository.TeacherRepository;
import agile.victims.EKM.Server.requests.LoginRequest;
import agile.victims.EKM.Server.requests.SignUpRequest;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired private StudentRepository studentRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private AdminRepository adminRepository;

    private Boolean ValidateDuplicateMail(String userType, String mail)
    {
        if (userType.equalsIgnoreCase("student"))
            return studentRepository.findByEmail(mail).isPresent();
        if (userType.equalsIgnoreCase("teacher"))
            return teacherRepository.findByEmail(mail).isPresent();
        if (userType.equalsIgnoreCase("admin"))
            return adminRepository.findByEmail(mail).isPresent();
        return null;
    }

    public ResponseEntity<?> login(String userType, LoginRequest loginRequest) {
        if (loginRequest.getEmail() == null || loginRequest.getEmail().isEmpty()) {
            throw (new RuntimeException("Email is required"));
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw (new RuntimeException("Password is required"));
        }

        return switch (userType.toLowerCase()) {
            case "student" -> studentRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())
                    .map(s -> ResponseEntity.ok("Student Dashboard Access Granted"))
                    //.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
                    .orElseThrow(() -> new RuntimeException("Student Login Failed"));
            case "teacher" -> teacherRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())
                    .map(t -> ResponseEntity.ok("Teacher Dashboard Access Granted"))
                    //.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
                    .orElseThrow(() -> new RuntimeException("Teacher Login Failed"));
            case "admin" -> adminRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())
                    .map(a -> ResponseEntity.ok("Admin Dashboard Access Granted"))
                    //.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials"));
                    .orElseThrow(() -> new RuntimeException("Admin Login Failed"));
            default -> throw new RuntimeException("Invalid user type");
        };
    }
    public ResponseEntity<String> signup(String userType, SignUpRequest signUpRequest) {
        if (signUpRequest.getEmail() == null || signUpRequest.getEmail().isEmpty()) {
            throw (new RuntimeException("Email is required"));
        }
        if (signUpRequest.getPassword() == null || signUpRequest.getPassword().isEmpty()) {
            throw (new RuntimeException("Password is required"));
        }
        if (signUpRequest.getName() == null || signUpRequest.getName().isEmpty()) {
            throw (new RuntimeException("Name is required"));
        }
        if (signUpRequest.getSurname() == null || signUpRequest.getSurname().isEmpty()) {
            throw (new RuntimeException("Surname is required"));
        }

        if (ValidateDuplicateMail(userType, signUpRequest.getEmail()) == null || Boolean.TRUE.equals(ValidateDuplicateMail(userType, signUpRequest.getEmail()))) {
            throw (new RuntimeException("Duplicate Email"));
        }

        switch (userType.toLowerCase()) {
            case "student":
                Student student = new Student(signUpRequest.getName(), signUpRequest.getSurname(), signUpRequest.getEmail(), signUpRequest.getPassword());
                student.setRole(User.Role.STUDENT);
                // Add additional fields in the feature
                studentRepository.save(student);
                return ResponseEntity.status(HttpStatus.CREATED).body("Student registered successfully");
            case "teacher":
                Teacher teacher = new Teacher(signUpRequest.getName(), signUpRequest.getSurname(), signUpRequest.getEmail(), signUpRequest.getPassword());
                teacher.setRole(User.Role.TEACHER);
                teacherRepository.save(teacher);
                return ResponseEntity.status(HttpStatus.CREATED).body("Teacher registered successfully");
            case "admin":
                Admin admin = new Admin(signUpRequest.getName(), signUpRequest.getSurname(), signUpRequest.getEmail(), signUpRequest.getPassword());
                admin.setRole(User.Role.ADMIN);
                adminRepository.save(admin);
                return ResponseEntity.status(HttpStatus.CREATED).body("Admin registered successfully");
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user type");
        }

    }
}
