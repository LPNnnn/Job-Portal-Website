package service;

import java.util.Optional;
import java.util.Scanner;
import model.Administrator;
import repository.AdministratorRepository;
import util.PasswordUtil;

public class AdministratorLoginService {

    private final AdministratorRepository repository =
            new AdministratorRepository();

    private Administrator currentLoggedInAdministrator;

    public void login(Scanner scanner) {

        boolean loginRunning = true;

        while (loginRunning) {

            System.out.println("\n========================================");
            System.out.println("         ADMINISTRATOR LOGIN");
            System.out.println("========================================");
            System.out.println("Enter 0 at any field to cancel.");

            String username = readUsername(scanner);

            if (username == null) {
                System.out.println(
                        "Administrator login cancelled."
                );
                return;
            }

            String password = readPassword(scanner);

            if (password == null) {
                System.out.println(
                        "Administrator login cancelled."
                );
                return;
            }

            repository.reload();

            Optional<Administrator> account =
                    repository.findByUsername(username);

            if (account.isEmpty()) {

                System.out.println(
                        "\nLogin failed: Administrator username "
                                + "was not found."
                );

                System.out.println(
                        "Please try again."
                );

                continue;
            }

            Administrator administrator = account.get();

            String encryptedPassword =
                    PasswordUtil.encryptPassword(password);

            if (!administrator.getPassword()
                    .equals(encryptedPassword)) {

                System.out.println(
                        "\nLogin failed: Incorrect password."
                );

                System.out.println(
                        "Please try again."
                );

                continue;
            }

            currentLoggedInAdministrator = administrator;

            System.out.println(
                    "\nAdministrator login successful."
            );

            System.out.println(
                    "Welcome, "
                            + administrator.getAdministratorName()
                            + "!"
            );

            showAdministratorDashboard(scanner);

            loginRunning = false;
        }
    }

    private String readUsername(Scanner scanner) {

        while (true) {

            System.out.print("Username : ");

            String username =
                    scanner.nextLine().trim();

            if (username.equals("0")) {
                return null;
            }

            if (username.isEmpty()) {

                System.out.println(
                        "Username cannot be empty.\n"
                );

                continue;
            }

            if (!username.matches(
                    "^[A-Za-z0-9._-]{3,30}$")) {

                System.out.println(
                        "Username must contain 3 to 30 "
                                + "characters."
                );

                System.out.println(
                        "Only letters, numbers, dots, "
                                + "underscores and hyphens "
                                + "are allowed.\n"
                );

                continue;
            }

            return username.toLowerCase();
        }
    }

    private String readPassword(Scanner scanner) {

        while (true) {

            System.out.print("Password : ");

            String password =
                    scanner.nextLine();

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

    private void showAdministratorDashboard(
            Scanner scanner) {

        boolean running = true;

        while (running
                && currentLoggedInAdministrator != null) {

            System.out.println("\n========================================");
            System.out.println("       ADMINISTRATOR DASHBOARD");
            System.out.println("========================================");
            System.out.println(
                    "1. View Administrator Information"
            );
            System.out.println("0. Logout");
            System.out.println("========================================");
            System.out.print("Choose an option: ");

            int choice =
                    readMenuChoice(scanner, 0, 1);

            switch (choice) {

                case 1:
                    viewAdministratorInformation();
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

    private void viewAdministratorInformation() {

        if (currentLoggedInAdministrator == null) {

            System.out.println(
                    "No administrator is currently logged in."
            );

            return;
        }

        System.out.println("\n========================================");
        System.out.println("      ADMINISTRATOR INFORMATION");
        System.out.println("========================================");

        System.out.println(
                "Administrator Name : "
                        + currentLoggedInAdministrator
                        .getAdministratorName()
        );

        System.out.println(
                "Username           : "
                        + currentLoggedInAdministrator
                        .getUsername()
        );

        System.out.println("========================================");
    }

    public void logout() {

        currentLoggedInAdministrator = null;

        System.out.println(
                "Administrator logged out successfully."
        );
    }

    private int readMenuChoice(
            Scanner scanner,
            int minimum,
            int maximum) {

        while (true) {

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
                // Display common error message below.
            }

            System.out.println(
                    "Invalid option. Please enter a number from "
                            + minimum
                            + " to "
                            + maximum
                            + "."
            );

            System.out.print(
                    "Choose an option: "
            );
        }
    }
}// Updated 23/7/2026 
// Re-committed on 22/7/2026 
