import java.util.Scanner;
import model.Company;
import model.JobSeeker;
import service.AdministratorLoginService;

public class Main {

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final AdministratorLoginService
            administratorLoginService =
            new AdministratorLoginService();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            displayMainMenu();

            int choice = readMenuChoice(0, 3);

            switch (choice) {

                case 1:
                    JobSeeker.showMenu(scanner);
                    break;

                case 2:
                    Company.showMenu(scanner);
                    break;

                case 3:
                    administratorLoginService.login(scanner);
                    break;

                case 0:
                    System.out.println(
                            "\nThank you for using Job Portal Website."
                    );
                    System.out.println("Goodbye!");

                    running = false;
                    break;

                default:
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

    private static int readMenuChoice(
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
                // Display error message below.
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
}// Initial JobPortal structure committed on 16/7/2026 
