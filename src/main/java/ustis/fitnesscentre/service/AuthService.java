package ustis.fitnesscentre.service;

import jakarta.security.auth.message.AuthException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ustis.fitnesscentre.dto.JwtRequest;
import ustis.fitnesscentre.dto.JwtResponse;
import ustis.fitnesscentre.dto.RegisterReqest;
import ustis.fitnesscentre.exception.UserNotFoundException;
import ustis.fitnesscentre.model.Client;
import ustis.fitnesscentre.util.JwtTokenUtil;

@Service
public class AuthService {
    private final ClientService clientService;

    private final JwtTokenUtil jwtTokenUtil;

    private final PasswordEncoder passwordEncoder;

    public AuthService(ClientService clientService, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public JwtResponse login(JwtRequest authRequest) throws AuthException, UserNotFoundException {
        final Client client = clientService.loadByPhoneNumber(authRequest.getPhoneNumber()).get();
        if (passwordEncoder.matches(authRequest.getPassword(), client.getPassword().toString())) {
            final String accessToken = jwtTokenUtil.generateToken(client);
            return new JwtResponse(accessToken, client.getRoles());
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public void register(RegisterReqest registerReqest) {
        Client client = new Client(
                registerReqest.getPhoneNumber(),
                passwordEncoder.encode(registerReqest.getPassword()),
                registerReqest.getFullName(),
                registerReqest.getBirthdayDate(),
                registerReqest.getGender()
        );
        clientService.save(client);
    }

    public Client getClientFromToken(String token) throws UserNotFoundException {
        return clientService.loadByPhoneNumber(jwtTokenUtil.getUsernameFromToken(token)).get();
    }
}
