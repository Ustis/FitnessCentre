package ustis.fitnesscentre.service;

import jakarta.security.auth.message.AuthException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ustis.fitnesscentre.dto.VisitArrivalRequest;
import ustis.fitnesscentre.dto.VisitLeavingRequest;
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

    public void recordArrival(Authentication clientAuth, VisitArrivalRequest visitArrivalRequest) throws AuthException {
        Client client = clientService.loadByPhoneNumber(clientAuth.getName())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));

        Visit visit = new Visit(visitArrivalRequest.getArrivalDateTime(), LocalDateTime.now(), client.getId());

        visitRepository.save(visit);
    }

    public void recordLeaving(Authentication clientAuth, VisitLeavingRequest visitLeavingRequest) throws AuthException {
        Client client = clientService.loadByPhoneNumber(clientAuth.getName())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));

        Visit visit = visitRepository.findIncompleteSession(client.getId())
                .orElseThrow(() -> new RuntimeException("Внутренняя ошибка сервера"));
        visit.setLeaving(visitLeavingRequest.getLeavingDateTime());
        visitRepository.update(visit);
    }
}
