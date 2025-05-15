package agile.victims.EKM.Server.service;

import agile.victims.EKM.Server.entity.Exam;
import agile.victims.EKM.Server.entity.Student;
import agile.victims.EKM.Server.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student getStudentById(Long id) {
        var result = studentRepository.findById(id);
        return result.orElse(null);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
