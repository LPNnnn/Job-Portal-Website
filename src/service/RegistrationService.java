package service;

import java.util.Scanner;
import model.JobSeeker;
import repository.JobSeekerRepository;
import util.PasswordUtil;
import util.ValidationUtil;

public class RegistrationService {

    private JobSeekerRepository repository = new JobSeekerRepository();

    public void register(Scanner sc) {
        System.out.println("\n========== REGISTRATION ==========");

        System.out.print("Full Name : ");
        String fullName = sc.nextLine();

        if (ValidationUtil.isEmpty(fullName)) {
            System.out.println("Full name cannot be empty.");
            return;
        }

        System.out.print("Email : ");
        String email = sc.nextLine().trim();

        if (!ValidationUtil.isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }

        // Check if email already exists
        if (repository.findByEmail(email) != null) {
            System.out.println("Email already registered. Please use a different email.");
            return;
        }

        System.out.print("Password : ");
        String password = sc.nextLine();

        if (!ValidationUtil.isValidPassword(password)) {
            System.out.println("Password must be at least 8 characters long.");
            return;
        }

        String encryptedPassword = PasswordUtil.encrypt(password);

        JobSeeker jobSeeker = new JobSeeker(fullName, email, encryptedPassword);
        repository.save(jobSeeker);

        System.out.println("Registration successful!");
    }
}