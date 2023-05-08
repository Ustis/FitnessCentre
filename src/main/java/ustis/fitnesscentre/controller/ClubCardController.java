package ustis.fitnesscentre.controller;

import jakarta.security.auth.message.AuthException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ustis.fitnesscentre.service.ClubCardService;

import java.text.DecimalFormat;

@RestController
@RequestMapping("/api/clubCard")
public class ClubCardController {
    private final ClubCardService clubCardService;

    public ClubCardController(ClubCardService clubCardService) {
        this.clubCardService = clubCardService;
    }

    @GetMapping("/isCardActive")
    public ResponseEntity<Boolean> isClubCardActive(Authentication clientAuth) throws AuthException {
        return ResponseEntity.ok(clubCardService.isCardActive(clientAuth));
    }

    @GetMapping("/cardPrice")
    public ResponseEntity<String> cardPrice(Authentication clientAuth) throws AuthException {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return ResponseEntity.ok(df.format(clubCardService.cardPrice(clientAuth)));
    }

    @PostMapping("/buyCard")
    public ResponseEntity<Void> buyCard(Authentication clientAuth) throws AuthException {
        clubCardService.buyClubCard(clientAuth);
        return ResponseEntity.ok().build();
    }
}
