package ustis.fitnesscentre.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ustis.fitnesscentre.dto.ChangeClientRequest;
import ustis.fitnesscentre.dto.ClientDataResponse;
import ustis.fitnesscentre.exception.UserNotFoundException;
import ustis.fitnesscentre.model.Client;
import ustis.fitnesscentre.repository.ClientRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService implements UserDetailsService {
    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Optional<Client> loadByPhoneNumber(String phoneNumber) throws UserNotFoundException {
        Client client = repository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException());
        return repository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = repository.findByPhoneNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user: " + username));
        return org.springframework.security.core.userdetails.User
                .withUsername(client.getPhoneNumber())
                .password(client.getPassword().toString())
                .authorities(client.getRoles())
                .build();
    }

    public List<Client> getAll() {
        return repository.getAll();
    }

    public List<ClientDataResponse> getAllResponseFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return getAll().stream()
                .map(client -> {
                    ClientDataResponse clientDataResponse = new ClientDataResponse();
                    clientDataResponse.setId(client.getId());
                    clientDataResponse.setPhoneNumber(client.getPhoneNumber());
                    clientDataResponse.setFullName(client.getFullName());
                    clientDataResponse.setBirthdayDate(client.getBirthdayDate().format(formatter));
                    clientDataResponse.setGender(client.getGender());
                    clientDataResponse.setRoles(client.getRoles());
                    clientDataResponse.setBalance(client.getBalance());
                    return clientDataResponse;
                })
                .collect(Collectors.toList());
    }

    public void update(ChangeClientRequest client) {
        repository.updateWithoutPassword(new Client(
                client.getId(), client.getPhoneNumber(), client.getFullName(), client.getBirthdayDate(),
                client.getGender(), client.getRoles(), client.getBalance()
        ));
    }

    public void save(Client client) {
        repository.save(client);
    }

}
