package service;

import model.JobSeeker;
import repository.JobSeekerRepository;
import util.PasswordUtil;
import util.ConsoleInputUtil;
import util.ValidationUtil;

import java.util.Optional;
import java.util.Scanner;

public class LoginService {

    private static LoginService instance;

    private final JobSeekerRepository repository;
    private final ProfileService profileService;
    private final JobSearchService jobSearchService;
    private final Scanner scanner;

    private LoginService() {
        repository = JobSeekerRepository.getInstance();
        profileService = ProfileService.getInstance();
        jobSearchService = JobSearchService.getInstance();
        scanner = new Scanner(System.in);
    }

    public static LoginService getInstance() {
        if (instance == null) {
            instance = new LoginService();
        }

        return instance;
    }

    public void showLoginMenu() {
        login();
    }

    // =====================================================
    // JOB SEEKER LOGIN
    // =====================================================

    public void login() {
        System.out.println("\n=================================");
        System.out.println("          JOB SEEKER LOGIN");
        System.out.println("=================================");
        System.out.println("Enter 0 at any field to cancel.");

        String email = readEmail();

        if (email == null) {
            System.out.println("Login cancelled.");
            return;
        }

        String password = readPassword();

        if (password == null) {
            System.out.println("Login cancelled.");
            return;
        }

        authenticate(email, password);
    }

    // =====================================================
    // LOGIN WITH EXISTING SCANNER
    // =====================================================

    public void login(Scanner inputScanner) {
        if (inputScanner == null) {
            login();
            return;
        }

        System.out.println("\n=================================");
        System.out.println("          JOB SEEKER LOGIN");
        System.out.println("=================================");
        System.out.println("Enter 0 at any field to cancel.");

        String email = readEmail(inputScanner);

        if (email == null) {
            System.out.println("Login cancelled.");
            return;
        }

        String password = readPassword(inputScanner);

        if (password == null) {
            System.out.println("Login cancelled.");
            return;
        }

        authenticate(email, password);
    }

    // =====================================================
    // AUTHENTICATION
    // =====================================================

    private void authenticate(String email, String password) {
        repository.reload();

        Optional<JobSeeker> account =
                repository.findByEmail(email);

        if (account.isEmpty()) {
            System.out.println(
                    "Login failed: No job seeker account was found "
                            + "for this email."
            );
            return;
        }

        JobSeeker jobSeeker = account.get();

        String encryptedPassword;

        try {
            encryptedPassword =
                    PasswordUtil.encryptPassword(password);

        } catch (Exception exception) {
            System.out.println(
                    "Login failed: Unable to process the password."
            );
            return;
        }

        if (jobSeeker.getPassword() == null
                || !jobSeeker.getPassword()
                .equals(encryptedPassword)) {

            System.out.println(
                    "Login failed: Incorrect password."
            );
            return;
        }

        profileService.setCurrentUser(jobSeeker);

        System.out.println("\nLogin successful.");
        System.out.println(
                "Welcome, "
                        + displayName(jobSeeker)
                        + "!"
        );

        showJobSeekerMenu();
    }

    // =====================================================
    // JOB SEEKER MENU
    // =====================================================

    private void showJobSeekerMenu() {
        boolean running = true;

        while (running && profileService.isLoggedIn()) {
            System.out.println("\n=================================");
            System.out.println("         JOB SEEKER MENU");
            System.out.println("=================================");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. Profile Completion");
            System.out.println("4. Search Jobs");
            System.out.println("0. Logout");
            System.out.println("=================================");

            int choice = readMenuChoice(
                    "Choose an option: ",
                    0,
                    4
            );

            switch (choice) {
                case 1:
                    profileService.viewProfile();
                    break;

                case 2:
                    profileService.editProfile();
                    break;

                case 3:
                    profileService.displayProfileCompletion();
                    break;

                case 4:
                    jobSearchService.showSearchMenu(scanner);
                    break;

                case 0:
                    profileService.logout();
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid option."
                    );
            }
        }
    }

    // =====================================================
    // EMAIL INPUT
    // =====================================================

    private String readEmail() {
        return readEmail(scanner);
    }

    private String readEmail(Scanner inputScanner) {
        while (true) {
            System.out.print("Email: ");

            String email =
                    inputScanner.nextLine().trim();

            if (email.equals("0")) {
                return null;
            }

            if (email.isEmpty()) {
                System.out.println(
                        "Error: Email cannot be empty."
                );
                continue;
            }

            if (!ValidationUtil.isValidEmail(email)) {
                System.out.println(
                        "Error: "
                                + ValidationUtil
                                .getEmailRequirementMessage()
                );
                continue;
            }

            return email.toLowerCase();
        }
    }

    // =====================================================
    // PASSWORD INPUT
    // =====================================================

    private String readPassword() {
        return readPassword(scanner);
    }

    private String readPassword(Scanner inputScanner) {
        while (true) {
            String password = ConsoleInputUtil.readPassword(
                    inputScanner,
                    "Password: "
            );

            if (password.equals("0")) {
                return null;
            }

            if (password.trim().isEmpty()) {
                System.out.println(
                        "Error: Password cannot be empty."
                );
                continue;
            }

            /*
             * Do not apply the full registration password
             * strength validation here.
             *
             * During login, the system only needs to compare
             * the entered password with the stored password.
             */
            return password;
        }
    }

    // =====================================================
    // MENU INPUT
    // =====================================================

    private int readMenuChoice(
            String message,
            int minimum,
            int maximum) {

        while (true) {
            System.out.print(message);

            String input =
                    scanner.nextLine().trim();

            try {
                int choice =
                        Integer.parseInt(input);

                if (choice >= minimum
                        && choice <= maximum) {

                    return choice;
                }

            } catch (NumberFormatException exception) {
                // Display shared error message below.
            }

            System.out.println(
                    "Invalid option. Please enter a number from "
                            + minimum
                            + " to "
                            + maximum
                            + "."
            );
        }
    }

    // =====================================================
    // HELPER METHODS
    // =====================================================

    private String displayName(JobSeeker jobSeeker) {
        if (jobSeeker == null) {
            return "Job Seeker";
        }

        String fullName =
                jobSeeker.getFullName();

        if (fullName == null
                || fullName.trim().isEmpty()) {

            return jobSeeker.getEmail();
        }

        return fullName.trim();
    }
}