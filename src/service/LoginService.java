package service;

import repository.JobSeekerRepository;
import util.PasswordUtil;
import util.ValidationUtil;

public class LoginService {

    private JobSeekerRepository repository = new JobSeekerRepository();

    public String login(String email, String password) {

        if (ValidationUtil.isEmpty(email)
                || ValidationUtil.isEmpty(password)) {

            return "Login Failed!\nEmail and password are required.";

        }

        if (!repository.emailExistsOnly(email)) {

            return "Login Failed!\nAccount does not exist.\nPlease register first.";

        }

        String encryptedPassword
                = PasswordUtil.encrypt(password);

        if (!repository.login(email, encryptedPassword)) {

            return "Login Failed!\nIncorrect password.";

        }

        return "Login Successful!\nWelcome back!";

    }

}
