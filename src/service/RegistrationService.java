package service;

import model.JobSeeker;
import repository.JobSeekerRepository;
import util.PasswordUtil;
import util.ValidationUtil;

public class RegistrationService {

    private JobSeekerRepository repository =
            new JobSeekerRepository();

    public String register(String name,
                           String email,
                           String password) {

        if (ValidationUtil.isEmpty(name)
                || ValidationUtil.isEmpty(email)
                || ValidationUtil.isEmpty(password)) {

            return "Registration Failed!\nRequired fields cannot be empty.";

        }

        if (!ValidationUtil.isValidEmail(email)) {

            return "Registration Failed!\nInvalid email format.";

        }

        if (!ValidationUtil.isValidPassword(password)) {

            return "Registration Failed!\nPassword must contain at least 8 characters.";

        }

        if (repository.emailExists(email)) {

            return "Registration Failed!\nEmail already exists.";

        }

        password = PasswordUtil.encrypt(password);

        JobSeeker user =
                new JobSeeker(name, email, password);

        repository.save(user);

        return "Registration Successful!";

    }

}