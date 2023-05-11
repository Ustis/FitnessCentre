package ustis.fitnesscentre.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ustis.fitnesscentre.exception.UserNotFoundException;
import ustis.fitnesscentre.model.Client;
import ustis.fitnesscentre.model.Visit;
import ustis.fitnesscentre.repository.VisitRepository;

import java.time.LocalDateTime;

@Service
public class VisitService {
    private final VisitRepository visitRepository;
    private final ClientService clientService;

    public VisitService(VisitRepository visitRepository, ClientService clientService) {
        this.visitRepository = visitRepository;
        this.clientService = clientService;
    }

    public void recordArrival(Authentication clientAuth) throws UserNotFoundException {
        Client client = clientService.loadByPhoneNumber(clientAuth.getName()).get();

        Visit visit = new Visit(LocalDateTime.now(), client.getId());

        visitRepository.save(visit);
    }

    public void recordLeaving(Authentication clientAuth) throws UserNotFoundException {
        Client client = clientService.loadByPhoneNumber(clientAuth.getName()).get();

        Visit visit = visitRepository.findIncompleteSession(client.getId())
                .orElseThrow(() -> new RuntimeException("Внутренняя ошибка сервера"));
        visit.setLeaving(LocalDateTime.now());
        visitRepository.update(visit);
    }
}
