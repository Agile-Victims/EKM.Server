package agile.victims.EKM.Server.controller;

import agile.victims.EKM.Server.entity.User;
import agile.victims.EKM.Server.requests.LoginRequest;
import agile.victims.EKM.Server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login/{userType}")
    public ResponseEntity<?> login(@PathVariable("userType") String userType, @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(userType, loginRequest));
    }
    @PostMapping("/signup/{userType}")
    public ResponseEntity<String> signup(@PathVariable("userType") String userType, @RequestBody User user) {
        return userService.signup(userType, user);
    }
}
