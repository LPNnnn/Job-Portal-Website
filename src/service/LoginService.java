package service;

import model.JobSeeker;
import repository.JobSeekerRepository;
import util.PasswordUtil;
import util.ValidationUtil;

import java.util.Optional;
import java.util.Scanner;

public class LoginService {

    private final JobSeekerRepository repository =
            new JobSeekerRepository();

    private JobSeeker currentLoggedInJobSeeker;

    public void login(Scanner scanner) {
        System.out.println("\n========================================");
        System.out.println("          JOB SEEKER LOGIN");
        System.out.println("========================================");
        System.out.println("Enter 0 at any field to cancel.");

        String email = readEmail(scanner);

        if (email == null) {
            System.out.println("Job seeker login cancelled.");
            return;
        }

        String password = readPassword(scanner);

        if (password == null) {
            System.out.println("Job seeker login cancelled.");
            return;
        }

        repository.reload();

        Optional<JobSeeker> account =
                repository.findByEmail(email);

        if (account.isEmpty()) {
            System.out.println(
                    "Login failed: Job seeker account was not found."
            );
            return;
        }

        JobSeeker jobSeeker = account.get();

        String encryptedPassword =
                PasswordUtil.encryptPassword(password);

        if (!jobSeeker.getPassword().equals(encryptedPassword)) {
            System.out.println(
                    "Login failed: Incorrect password."
            );
            return;
        }

        currentLoggedInJobSeeker = jobSeeker;

        System.out.println("\nJob seeker login successful.");
        System.out.println(
                "Welcome, " + jobSeeker.getFullName() + "!"
        );

        showDashboard(scanner);
    }

    private void showDashboard(Scanner scanner) {
        boolean running = true;

        while (running && currentLoggedInJobSeeker != null) {
            System.out.println("\n========================================");
            System.out.println("       JOB SEEKER DASHBOARD");
            System.out.println("========================================");
            System.out.println("1. View Account Information");
            System.out.println("0. Logout");
            System.out.println("========================================");
            System.out.print("Choose an option: ");

            int choice = readMenuChoice(scanner, 0, 1);

            switch (choice) {
                case 1:
                    System.out.println("\nFull Name : "
                            + currentLoggedInJobSeeker.getFullName());
                    System.out.println("Email     : "
                            + currentLoggedInJobSeeker.getEmail());
                    break;

                case 0:
                    currentLoggedInJobSeeker = null;
                    System.out.println(
                            "Job seeker logged out successfully."
                    );
                    running = false;
                    break;

                default:
                    break;
            }
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
                System.out.println(
                        ValidationUtil.getEmailRequirementMessage()
                                + "\n"
                );
                continue;
            }

            return email.toLowerCase();
        }
    }

    private String readPassword(Scanner scanner) {
        while (true) {
            System.out.print("Password : ");
            String password = scanner.nextLine();

            if (password.equals("0")) {
                return null;
            }

            if (password.trim().isEmpty()) {
                System.out.println("Password cannot be empty.\n");
                continue;
            }

            return password;
        }
    }

    private int readMenuChoice(
            Scanner scanner,
            int minimum,
            int maximum) {

        while (true) {
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                if (choice >= minimum && choice <= maximum) {
                    return choice;
                }
            } catch (NumberFormatException exception) {
                // Common error is shown below.
            }

            System.out.println(
                    "Invalid option. Please enter a number from "
                            + minimum + " to " + maximum + "."
            );
            System.out.print("Choose an option: ");
        }
    }
}
// Updated 23/7/2026 
// Re-committed on 22/7/2026 
