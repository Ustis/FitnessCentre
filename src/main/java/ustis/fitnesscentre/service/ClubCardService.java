package ustis.fitnesscentre.service;

import jakarta.security.auth.message.AuthException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ustis.fitnesscentre.model.Client;
import ustis.fitnesscentre.repository.ClientRepository;
import ustis.fitnesscentre.repository.ClubCardRepository;
import ustis.fitnesscentre.repository.VisitRepository;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Duration;

@Service
public class ClubCardService {
    private final VisitRepository visitRepository;
    private final ClientService clientService;

    private final ClubCardRepository clubCardRepository;
    private final ClientRepository clientRepository;

    public ClubCardService(VisitRepository visitRepository, ClientService clientService, ClubCardRepository clubCardRepository, ClientRepository clientRepository) {
        this.visitRepository = visitRepository;
        this.clientService = clientService;
        this.clubCardRepository = clubCardRepository;
        this.clientRepository = clientRepository;
    }

    public Boolean isCardActive(Authentication clientAuth) throws AuthException {
        Client client = clientService.loadByPhoneNumber(clientAuth.getName())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        return clubCardRepository.isClubCardActive(client.getId());
    }

    public BigDecimal cardPrice(Authentication clientAuth) throws AuthException {
        Client client = clientService.loadByPhoneNumber(clientAuth.getName())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        Duration timeSpent = visitRepository.findTimeSpent(client.getId());
        return calculateDiscount(timeSpent);
    }

    private BigDecimal calculateDiscount(Duration timeSpent) {
        // TODO вынести константы
        // TODO написать для неограниченного количества констант
        Duration firstLevel = Duration.ofHours(14).plusMinutes(0);
        float firstLevelDiscountPrecent = 3;
        Duration secondLevel = Duration.ofHours(32). plusMinutes(0);
        float secondLevelDiscountPrecent = 6;
        MathContext moneyMathContext = new MathContext(2, RoundingMode.HALF_DOWN);
        BigDecimal standartCardPrice = new BigDecimal(100, moneyMathContext);
        if (timeSpent.compareTo(firstLevel) >= 0){
            if(timeSpent.compareTo(secondLevel) >= 0) {
                return standartCardPrice
                        .multiply(BigDecimal.valueOf(100-secondLevelDiscountPrecent))
                        .divide(BigDecimal.valueOf(100), moneyMathContext);
            }
            return standartCardPrice
                    .multiply(BigDecimal.valueOf(100-firstLevelDiscountPrecent))
                    .divide(BigDecimal.valueOf(100));
        }
        return standartCardPrice;
    }

    public void buyClubCard(Authentication clientAuth) throws AuthException {
        Client client = clientService.loadByPhoneNumber(clientAuth.getName())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        BigDecimal price = cardPrice(clientAuth);
        client.setBalance(
                client.getBalance()
                        .subtract(price)
        );
        clientRepository.update(client);
    }
}
