package ustis.fitnesscentre.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ustis.fitnesscentre.dto.ChangeClientRequest;
import ustis.fitnesscentre.dto.ClientDataResponse;
import ustis.fitnesscentre.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final ClientService clientService;

    public AdminController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/getClients")
    public ResponseEntity<List<ClientDataResponse>> getClients() {
        return ResponseEntity.ok(clientService.getAllResponseFormat());
    }

    @PostMapping("/changeClient")
    public ResponseEntity<Void> changeClientData(@RequestBody ChangeClientRequest clientData) {
        clientService.update(clientData);
        return ResponseEntity.ok().build();
    }
}
