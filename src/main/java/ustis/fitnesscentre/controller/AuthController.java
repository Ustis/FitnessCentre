package ustis.fitnesscentre.controller;

import jakarta.security.auth.message.AuthException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ustis.fitnesscentre.dto.JwtRequest;
import ustis.fitnesscentre.dto.JwtResponse;
import ustis.fitnesscentre.dto.RegisterReqest;
import ustis.fitnesscentre.exception.UserNotFoundException;
import ustis.fitnesscentre.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws UserNotFoundException, AuthException {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterReqest registerReqest) {
        if(!registerReqest.getPassword().equals(registerReqest.getPasswordConfirmation()))
            throw new RuntimeException("Пароли не совпадают");
        authService.register(registerReqest);
        return ResponseEntity.ok().build();
    }
}
