package service;

import java.util.Optional;
import java.util.Scanner;

import model.Company;
import repository.CompanyRepository;
import util.PasswordUtil;
import util.ConsoleInputUtil;
import util.ValidationUtil;

public class CompanyLoginService {

    private static CompanyLoginService instance;

    private final CompanyRepository repository;

    private Company currentLoggedInCompany;

    private CompanyLoginService() {
        repository = CompanyRepository.getInstance();
    }

    public static CompanyLoginService getInstance() {
        if (instance == null) {
            instance = new CompanyLoginService();
        }

        return instance;
    }

    public void login(Scanner scanner) {
        boolean loginRunning = true;

        while (loginRunning) {
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
                        "\nLogin failed: Employer account was not found."
                );
                System.out.println("Please try again.");
                continue;
            }

            Company company = account.get();

            String encryptedPassword =
                    PasswordUtil.encryptPassword(password);

            if (!company.getPassword()
                    .equals(encryptedPassword)) {

                System.out.println(
                        "\nLogin failed: Incorrect password."
                );
                System.out.println("Please try again.");
                continue;
            }

            currentLoggedInCompany = company;

            System.out.println(
                    "\nEmployer login successful."
            );
            System.out.println(
                    "Welcome, "
                            + company.getCompanyName()
                            + "!"
            );

            showDashboard(scanner);
            loginRunning = false;
        }
    }

    private void showDashboard(Scanner scanner) {
        while (currentLoggedInCompany != null) {
            System.out.println("\n========================================");
            System.out.println("          EMPLOYER DASHBOARD");
            System.out.println("========================================");
            System.out.println("0. Logout");
            System.out.println("========================================");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                logout();
            } else {
                System.out.println(
                        "Invalid option. Please enter 0 to logout."
                );
            }
        }
    }

    private String readEmail(Scanner scanner) {
        while (true) {
            System.out.print("Company Email : ");
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
            String password = ConsoleInputUtil.readPassword(
                    scanner,
                    "Password : "
            );

            if (password.equals("0")) {
                return null;
            }

            if (password.trim().isEmpty()) {
                System.out.println(
                        "Password cannot be empty.\n"
                );
                continue;
            }

            return password;
        }
    }

    public void logout() {
        currentLoggedInCompany = null;
        System.out.println(
                "Employer logged out successfully."
        );
    }
}
