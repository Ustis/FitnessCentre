package ustis.fitnesscentre.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ustis.fitnesscentre.dto.CardPriceResponse;
import ustis.fitnesscentre.exception.UserNotFoundException;
import ustis.fitnesscentre.model.Client;
import ustis.fitnesscentre.model.ClubCard;
import ustis.fitnesscentre.repository.ClientRepository;
import ustis.fitnesscentre.repository.ClubCardRepository;
import ustis.fitnesscentre.repository.VisitRepository;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;

@Service
public class ClubCardService {

    @Value("${business-logic.first-level-of-discount-hours}")
    private String firstLevelOfDiscountHours;

    @Value("${business-logic.first-level-of-discount-percentage}")
    private String firstLevelOfDiscountPercentage;

    @Value("${business-logic.second-level-of-discount-hours}")
    private String secondLevelOfDiscountHours;

    @Value("${business-logic.second-level-of-discount-percentage}")
    private String secondLevelOfDiscountPercentage;

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

    public Boolean isCardActive(Authentication clientAuth) throws UserNotFoundException {
        Client client = clientService.loadByPhoneNumber(clientAuth.getName()).get();
        return clubCardRepository.isClubCardActive(client.getId());
    }

    public CardPriceResponse cardPriceResponse(Authentication clientAuth) throws UserNotFoundException {
        Client client = clientService.loadByPhoneNumber(clientAuth.getName()).get();
        DecimalFormat df = new DecimalFormat("#,###.00");

        return new CardPriceResponse(df.format(cardPrice(client)),
                df.format(client.getBalance()));
    }

    public BigDecimal cardPrice(Client client) {
        Duration timeSpent = visitRepository.findTimeSpent(client.getId());
        return calculateDiscount(timeSpent);
    }

    private BigDecimal calculateDiscount(Duration timeSpent) {
        Duration firstLevel = Duration.ofHours(Integer.parseInt(firstLevelOfDiscountHours));
        float firstLevelDiscountPrecent = Float.parseFloat(firstLevelOfDiscountPercentage);
        Duration secondLevel = Duration.ofHours(Integer.parseInt(secondLevelOfDiscountHours));
        float secondLevelDiscountPrecent = Float.parseFloat(secondLevelOfDiscountPercentage);
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
                    .divide(BigDecimal.valueOf(100), moneyMathContext);
        }
        return standartCardPrice;
    }

    public void buyClubCard(Authentication clientAuth) throws UserNotFoundException {
        Client client = clientService.loadByPhoneNumber(clientAuth.getName()).get();
        BigDecimal price = cardPrice(client);
        client.setBalance(
                client.getBalance()
                        .subtract(price)
        );
        clientRepository.update(client);
        clubCardRepository.save(new ClubCard(
                LocalDate.now(),
                LocalDate.now().plusMonths(1),
                true,
                client.getId()
        ));
    }
}
