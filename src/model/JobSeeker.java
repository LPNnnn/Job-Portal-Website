package model;

import java.util.Scanner;
import service.LoginService;
import service.RegistrationService;

public class JobSeeker {

    private String fullName;
    private String email;
    private String password;

    private static final RegistrationService registrationService
            = new RegistrationService();

    private static final LoginService loginService
            = new LoginService();

    public JobSeeker(
            String fullName,
            String email,
            String password) {

        this.fullName = safeValue(fullName);
        this.email = safeValue(email).toLowerCase();
        this.password = safeValue(password);
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setFullName(String fullName) {
        this.fullName = safeValue(fullName);
    }

    public void setEmail(String email) {
        this.email = safeValue(email).toLowerCase();
    }

    public void setPassword(String password) {
        this.password = safeValue(password);
    }

    // ========================================
    // JOB SEEKER MENU
    // ========================================
    public static void showMenu(Scanner scanner) {

        boolean running = true;

        while (running) {

            System.out.println("\n========================================");
            System.out.println("          JOB SEEKER MENU");
            System.out.println("========================================");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Back to Main Menu");
            System.out.println("========================================");
            System.out.print("Choose an option: ");

            int choice = readMenuChoice(
                    scanner,
                    0,
                    2
            );

            switch (choice) {

                case 1:
                    registrationService.register(scanner);
                    break;

                case 2:
                    loginService.login(scanner);
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    break;
            }
        }
    }

    private static int readMenuChoice(
            Scanner scanner,
            int minimum,
            int maximum) {

        while (true) {

            String input = scanner.nextLine().trim();

            try {

                int choice = Integer.parseInt(input);

                if (choice >= minimum
                        && choice <= maximum) {

                    return choice;
                }

            } catch (NumberFormatException exception) {
                // Display error below.
            }

            System.out.println(
                    "Invalid option. Please enter a number from "
                    + minimum
                    + " to "
                    + maximum
                    + "."
            );

            System.out.print("Choose an option: ");
        }
    }

    @Override
    public String toString() {

        return safeValue(fullName)
                + "|"
                + safeValue(email)
                + "|"
                + safeValue(password);
    }

    public static JobSeeker fromString(String line) {

        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] data = line.split("\\|", -1);

        if (data.length < 3) {
            return null;
        }

        return new JobSeeker(
                data[0],
                data[1],
                data[2]
        );
    }

    private static String safeValue(String value) {

        if (value == null) {
            return "";
        }

        return value.trim().replace("|", " ");
    }
}
// Re-committed on 16/7/2026 
