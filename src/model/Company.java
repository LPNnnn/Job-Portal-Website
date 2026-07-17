package model;

import java.util.Scanner;
import service.CompanyRegistrationService;

public class Company {

    private String companyName;
    private String companyEmail;
    private String password;

    private static final CompanyRegistrationService companyRegistrationService
            = new CompanyRegistrationService();

    public Company(
            String companyName,
            String companyEmail,
            String password) {

        this.companyName = safeValue(companyName);

        this.companyEmail
                = safeValue(companyEmail).toLowerCase();

        this.password = safeValue(password);
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setCompanyName(String companyName) {

        this.companyName
                = safeValue(companyName);
    }

    public void setCompanyEmail(String companyEmail) {

        this.companyEmail
                = safeValue(companyEmail).toLowerCase();
    }

    public void setPassword(String password) {

        this.password
                = safeValue(password);
    }

    // ========================================
    // EMPLOYER MENU
    // ========================================
    public static void showMenu(Scanner scanner) {

        boolean running = true;

        while (running) {

            System.out.println("\n========================================");
            System.out.println("            EMPLOYER MENU");
            System.out.println("========================================");
            System.out.println("1. Register");
            System.out.println("0. Back to Main Menu");
            System.out.println("========================================");
            System.out.print("Choose an option: ");

            int choice = readMenuChoice(
                    scanner,
                    0,
                    1
            );

            switch (choice) {

                case 1:
                    companyRegistrationService.register(
                            scanner
                    );
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

        return safeValue(companyName)
                + "|"
                + safeValue(companyEmail)
                + "|"
                + safeValue(password);
    }

    public static Company fromString(String line) {

        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] data = line.split("\\|", -1);

        if (data.length < 3) {
            return null;
        }

        return new Company(
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
// Re-committed on 17/7/2026 
