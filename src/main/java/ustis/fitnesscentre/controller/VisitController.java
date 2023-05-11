package ustis.fitnesscentre.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ustis.fitnesscentre.exception.UserNotFoundException;
import ustis.fitnesscentre.service.VisitService;

@RestController
@RequestMapping("/api/visit")
public class VisitController {
    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping("/arrival")
    public ResponseEntity<Void> clientArrival(Authentication clientAuth) throws UserNotFoundException {
        visitService.recordArrival(clientAuth);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/leaving")
    public ResponseEntity<Void> clientLeaving(Authentication clientAuth) throws UserNotFoundException {
        visitService.recordLeaving(clientAuth);
        return ResponseEntity.ok().build();
    }
}
