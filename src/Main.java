import service.CompanyLoginService;
import service.CompanyRegistrationService;
import service.LoginService;
import service.RegistrationService;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final RegistrationService registrationService =
            new RegistrationService();

    private static final LoginService loginService =
            new LoginService();

    private static final CompanyRegistrationService companyRegistrationService =
            new CompanyRegistrationService();

    private static final CompanyLoginService companyLoginService =
            new CompanyLoginService();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = readMenuChoice(0, 3);

            switch (choice) {
                case 1:
                    showJobSeekerMenu();
                    break;

                case 2:
                    showEmployerMenu();
                    break;

                case 3:
                    System.out.println(
                            "\nAdministrator module is not available yet."
                    );
                    break;

                case 0:
                    System.out.println(
                            "\nThank you for using Job Portal Website."
                    );
                    System.out.println("Goodbye!");
                    running = false;
                    break;

                default:
                    // Input is already validated.
                    break;
            }
        }

        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n========================================");
        System.out.println("       JOB PORTAL WEBSITE");
        System.out.println("========================================");
        System.out.println("SELECT YOUR ROLE:");
        System.out.println("1. Job Seeker");
        System.out.println("2. Employer");
        System.out.println("3. Administrator");
        System.out.println("0. Exit");
        System.out.println("========================================");
        System.out.print("Choose an option: ");
    }

    private static void showJobSeekerMenu() {
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

            int choice = readMenuChoice(0, 2);

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

    private static void showEmployerMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n========================================");
            System.out.println("            EMPLOYER MENU");
            System.out.println("========================================");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Back to Main Menu");
            System.out.println("========================================");
            System.out.print("Choose an option: ");

            int choice = readMenuChoice(0, 2);

            switch (choice) {
                case 1:
                    companyRegistrationService.register(scanner);
                    break;

                case 2:
                    companyLoginService.login(scanner);
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    break;
            }
        }
    }

    private static int readMenuChoice(int minimum, int maximum) {
        while (true) {
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                if (choice >= minimum && choice <= maximum) {
                    return choice;
                }
            } catch (NumberFormatException exception) {
                // Show the common error message below.
            }

            System.out.println(
                    "Invalid option. Please enter a number from "
                            + minimum + " to " + maximum + "."
            );
            System.out.print("Choose an option: ");
        }
    }
}
