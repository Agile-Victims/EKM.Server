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

    @PostMapping("/set-classes/")
    public ResponseEntity<?> setClasses(@RequestBody SetClassesRequest setClassesRequest)
    {
        try
        {
            return ResponseEntity.ok(teacherService.setClasses(setClassesRequest));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/get-classes")
    public ResponseEntity<?> getClasses(@RequestBody GetClassesRequest getClassesRequest) {
        try {
            return teacherService.getTeacherClasses(getClassesRequest);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }



}
