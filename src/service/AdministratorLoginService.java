package service;

import java.util.Optional;
import java.util.Scanner;

import model.Administrator;
import repository.AdministratorRepository;
import util.PasswordUtil;
import util.ConsoleInputUtil;

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
                System.out.println("Please try again.");
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
                System.out.println("Please try again.");
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

            showDashboard(scanner);
            loginRunning = false;
        }
    }

    private void showDashboard(Scanner scanner) {
        while (currentLoggedInAdministrator != null) {
            System.out.println("\n========================================");
            System.out.println("       ADMINISTRATOR DASHBOARD");
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

    private String readUsername(Scanner scanner) {
        while (true) {
            System.out.print("Username : ");
            String username = scanner.nextLine().trim();

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
                        "Username must contain 3 to 30 characters "
                                + "and may use letters, numbers, "
                                + "dots, underscores, or hyphens.\n"
                );
                continue;
            }

            return username.toLowerCase();
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
        currentLoggedInAdministrator = null;
        System.out.println(
                "Administrator logged out successfully."
        );
    }
}
