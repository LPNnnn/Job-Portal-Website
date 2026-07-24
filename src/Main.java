import model.JobSeeker;
import model.Company;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        runApplication();
    }
    
    private static void runApplication() {
        while (true) {
            displayMainMenu();
            int choice = getIntInput();
            handleMainMenuChoice(choice);
        }
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
    
    private static void handleMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                JobSeeker.showMenu();
                break;
            case 2:
                Company.showMenu();
                break;
            case 3:
                System.out.println("Administrator (JOB-13) coming soon.");
                break;
            case 0:
                System.out.println("Thank you for using Job Portal. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Please choose 0, 1, 2, or 3.");
        }
    }
    
    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}