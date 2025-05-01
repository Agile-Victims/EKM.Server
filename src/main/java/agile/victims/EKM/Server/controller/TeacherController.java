package agile.victims.EKM.Server.controller;

import agile.victims.EKM.Server.requests.GetClassesRequest;
import agile.victims.EKM.Server.requests.SetClassesRequest;
import agile.victims.EKM.Server.requests.SignUpRequest;
import agile.victims.EKM.Server.service.TeacherService;
import agile.victims.EKM.Server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webApi/teacher")
public class TeacherController {
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/signup/")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        try {
            return ResponseEntity.ok(userService.signup("teacher", signUpRequest));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/set-classes")
    public ResponseEntity<?> setClasses(@RequestBody SetClassesRequest setClassesRequest)
    {
        try
        {
            return ResponseEntity.ok(teacherService.setClasses(setClassesRequest));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-my-info/{email}")
    public ResponseEntity<?> getMyInfo(@PathVariable String email) {
        try {
            return teacherService.getTeacher(email);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }
}
