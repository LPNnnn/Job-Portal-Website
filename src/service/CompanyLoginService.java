package service;

import java.util.Optional;
import java.util.Scanner;
import model.Company;
import repository.CompanyRepository;
import util.PasswordUtil;
import util.ValidationUtil;

public class CompanyLoginService {

    private final CompanyRepository repository =
            new CompanyRepository();

    private Company currentLoggedInCompany;

    public void login(Scanner scanner) {
        System.out.println("\n========================================");
        System.out.println("            EMPLOYER LOGIN");
        System.out.println("========================================");
        System.out.println("Enter 0 at any field to cancel.");

        String email = readEmail(scanner);

        if (email == null) {
            System.out.println("Employer login cancelled.");
            return;
        }

        String password = readPassword(scanner);

        if (password == null) {
            System.out.println("Employer login cancelled.");
            return;
        }

        repository.reload();

        Optional<Company> account =
                repository.findByEmail(email);

        if (account.isEmpty()) {
            System.out.println(
                    "Login failed: Employer account was not found."
            );
            return;
        }

        Company company = account.get();
        String encryptedPassword = PasswordUtil.encryptPassword(password);

        if (!company.getPassword().equals(encryptedPassword)) {
            System.out.println(
                    "Login failed: Incorrect password."
            );
            return;
        }

        currentLoggedInCompany = company;

        System.out.println("\nEmployer login successful.");
        System.out.println(
                "Welcome, " + company.getCompanyName() + "!"
        );

        showDashboard(scanner);
    }

    private void showDashboard(Scanner scanner) {
        boolean running = true;

        while (running && currentLoggedInCompany != null) {
            System.out.println("\n========================================");
            System.out.println("          EMPLOYER DASHBOARD");
            System.out.println("========================================");
            System.out.println("1. View Company Information");
            System.out.println("0. Logout");
            System.out.println("========================================");
            System.out.print("Choose an option: ");

            int choice = readMenuChoice(scanner, 0, 1);

            switch (choice) {
                case 1:
                    viewCompanyInformation();
                    break;

                case 0:
                    logout();
                    running = false;
                    break;

                default:
                    break;
            }
        }
    }

    private void viewCompanyInformation() {
        System.out.println("\n========================================");
        System.out.println("        COMPANY INFORMATION");
        System.out.println("========================================");
        System.out.println(
                "Company Name  : "
                        + currentLoggedInCompany.getCompanyName()
        );
        System.out.println(
                "Company Email : "
                        + currentLoggedInCompany.getCompanyEmail()
        );
    }

    private String readEmail(Scanner scanner) {
        while (true) {
            System.out.print("Company Email : ");
            String email = scanner.nextLine().trim();

            if (email.equals("0")) {
                return null;
            }

            if (!ValidationUtil.isValidEmail(email)) {
                System.out.println(ValidationUtil.getEmailRequirementMessage() + "\n");
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

    public void logout() {
        currentLoggedInCompany = null;
        System.out.println("Employer logged out successfully.");
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
                // Show the common error below.
            }

            System.out.println(
                    "Invalid option. Please enter a number from "
                            + minimum + " to " + maximum + "."
            );
            System.out.print("Choose an option: ");
        }
    }
}
