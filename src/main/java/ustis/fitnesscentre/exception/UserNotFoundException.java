package ustis.fitnesscentre.exception;

import javax.security.auth.login.LoginException;

public class UserNotFoundException extends LoginException {

    public UserNotFoundException() {
        super("Пользователь не найден");
    }

}
