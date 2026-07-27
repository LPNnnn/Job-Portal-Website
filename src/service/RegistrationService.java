package service;

import model.JobSeeker;
import repository.JobSeekerRepository;
import util.PasswordUtil;
import util.ValidationUtil;

import java.util.Scanner;

public class RegistrationService {

    private final JobSeekerRepository repository =
            new JobSeekerRepository();

    public void register(Scanner scanner) {
        System.out.println("\n========================================");
        System.out.println("       JOB SEEKER REGISTRATION");
        System.out.println("========================================");
        System.out.println("Enter 0 at any field to cancel.");

        String fullName = readFullName(scanner);

        if (fullName == null) {
            System.out.println("Job seeker registration cancelled.");
            return;
        }

        String email = readEmail(scanner);

        if (email == null) {
            System.out.println("Job seeker registration cancelled.");
            return;
        }

        String password = readPassword(scanner);

        if (password == null) {
            System.out.println("Job seeker registration cancelled.");
            return;
        }

        JobSeeker jobSeeker = new JobSeeker(
                fullName,
                email,
                PasswordUtil.encryptPassword(password)
        );

        if (repository.save(jobSeeker)) {
            System.out.println(
                    "\nJob seeker registration successful."
            );
        } else {
            System.out.println(
                    "\nJob seeker registration failed."
            );
        }
    }

    private String readFullName(Scanner scanner) {
        while (true) {
            System.out.print("Full Name : ");
            String fullName = scanner.nextLine().trim();

            if (fullName.equals("0")) {
                return null;
            }

            if (!ValidationUtil.isValidName(fullName)) {
                System.out.println(
                        "Invalid name. Use at least 2 letters.\n"
                );
                continue;
            }

            return fullName;
        }
    }

    private String readEmail(Scanner scanner) {
        while (true) {
            System.out.print("Email : ");
            String email = scanner.nextLine().trim();

            if (email.equals("0")) {
                return null;
            }

            if (!ValidationUtil.isValidEmail(email)) {
                System.out.println(ValidationUtil.getEmailRequirementMessage() + "\n");
            } else if (repository.emailExists(email)) {
                System.out.println("Email already exists.\n");
            } else {
                return email.toLowerCase();
            }
        }
    }

    private String readPassword(Scanner scanner) {
        while (true) {
            System.out.print("Password : ");
            String password = scanner.nextLine();

            if (password.equals("0")) {
                return null;
            }

            if (!ValidationUtil.isValidPassword(password)) {
                System.out.println(
                        ValidationUtil.getPasswordRequirementMessage() + "\n"
                );
                continue;
            }

            return password;
        }
    }
}
