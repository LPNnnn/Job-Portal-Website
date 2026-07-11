package service;

import java.util.Scanner;
import model.JobSeeker;
import repository.JobSeekerRepository;
import util.PasswordUtil;
import util.ValidationUtil;

public class LoginService {

    private JobSeekerRepository repository = new JobSeekerRepository();

    public void login(Scanner sc) {
        boolean loginSuccess = false;
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        System.out.println("\n========== LOGIN ==========");

        while (!loginSuccess && attempts < MAX_ATTEMPTS) {
            if (attempts > 0) {
                System.out.print("\nIncorrect credentials. Please try again.");
                System.out.println(" (Attempt " + (attempts + 1) + " of " + MAX_ATTEMPTS + ")");
            }

            System.out.print("Email : ");
            String email = sc.nextLine().trim();

            // Validate email format
            if (!ValidationUtil.isValidEmail(email)) {
                System.out.println("\nInvalid email format. Please enter a valid email address.");
                attempts++;
                continue;
            }

            System.out.print("Password : ");
            String password = sc.nextLine();

            // Validate password length
            if (!ValidationUtil.isValidPassword(password)) {
                System.out.println("Password must be at least 8 characters long.");
                attempts++;
                continue;
            }

            password = PasswordUtil.encrypt(password);

            JobSeeker user = repository.login(email, password);

            if (user != null) {
                loginSuccess = true;
                System.out.println("\nLogin Successful!");
                System.out.println("Welcome " + user.getFullName() + "!");
            } else {
                attempts++;
                if (attempts == MAX_ATTEMPTS) {
                    System.out.println("\nMaximum login attempts reached. Please try again later.");
                }
            }
        }
    }
}