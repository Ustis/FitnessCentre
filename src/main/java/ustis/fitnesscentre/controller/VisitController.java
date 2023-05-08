package ustis.fitnesscentre.controller;

import jakarta.security.auth.message.AuthException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ustis.fitnesscentre.dto.VisitArrivalRequest;
import ustis.fitnesscentre.dto.VisitLeavingRequest;
import ustis.fitnesscentre.service.VisitService;

@RestController
@RequestMapping("/api/visit")
public class VisitController {
    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PostMapping("/arrival")
    public ResponseEntity<Void> clientArrival(Authentication clientAuth, @RequestBody VisitArrivalRequest request) throws AuthException {
        visitService.recordArrival(clientAuth, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/leaving")
    public ResponseEntity<Void> clientLeaving(Authentication clientAuth, @RequestBody VisitLeavingRequest request) throws AuthException {
        visitService.recordLeaving(clientAuth, request);
        return ResponseEntity.ok().build();
    }
}
