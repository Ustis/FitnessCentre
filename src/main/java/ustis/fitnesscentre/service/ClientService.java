package ustis.fitnesscentre.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ustis.fitnesscentre.model.Client;
import ustis.fitnesscentre.repository.ClientRepository;

import java.util.Optional;

@Service
public class ClientService implements UserDetailsService {
    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Optional<Client> loadByPhoneNumber(String phoneNumber){
        return repository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = repository.findByPhoneNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(client.getPhoneNumber())
                .password(client.getPassword().toString())
                .build();
    }

    public void save(Client client) {
        repository.save(client);
    }
}
