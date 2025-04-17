package agile.victims.EKM.Server.controller;

import agile.victims.EKM.Server.requests.SignUpRequest;
import agile.victims.EKM.Server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi/teacher")
public class TeacherController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        try {
            return ResponseEntity.ok(userService.signup("teacher", signUpRequest));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
