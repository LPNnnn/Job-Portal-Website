import java.util.Scanner;
import service.LoginService;
import service.RegistrationService;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        RegistrationService register = new RegistrationService();
        LoginService login = new LoginService();

        int choice;

        do {
            System.out.println("\n====================");
            System.out.println(" JOB PORTAL SYSTEM ");
            System.out.println("====================");
            System.out.println("1. Job Seeker Registration");
            System.out.println("2. Job Seeker Login");
            System.out.println("3. Exit");
            System.out.print("Choice : ");

            choice = sc.nextInt();
            sc.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    register.register(sc);
                    break;
                case 2:
                    login.login(sc);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 3);

        sc.close();
    }
}