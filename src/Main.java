import java.util.Scanner;
import service.LoginService;
import service.RegistrationService;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        RegistrationService registrationService = new RegistrationService();
        LoginService loginService = new LoginService();

        int choice;

        do {

            System.out.println("\n=================================");
            System.out.println("       JOB PORTAL SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("\nEnter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("\nInvalid input. Please enter a number.");
                sc.next();
                System.out.print("\nEnter your choice: ");
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.println("\n===== JOB SEEKER REGISTRATION =====");

                    System.out.print("Full Name : ");
                    String name = sc.nextLine();

                    System.out.print("Email : ");
                    String email = sc.nextLine();

                    System.out.print("Password : ");
                    String password = sc.nextLine();

                    String registerResult =
                            registrationService.register(name, email, password);

                    System.out.println();
                    System.out.println(registerResult);

                    break;

                case 2:

                    System.out.println("\n========== LOGIN ==========");

                    System.out.print("Email : ");
                    String loginEmail = sc.nextLine();

                    System.out.print("Password : ");
                    String loginPassword = sc.nextLine();

                    String loginResult =
                            loginService.login(loginEmail, loginPassword);

                    System.out.println();
                    System.out.println(loginResult);

                    break;

                case 3:

                    System.out.println("\n\nThank you for using Job Portal.");
                    System.out.println("Goodbye!");

                    break;

                default:

                    System.out.println("\n\nInvalid choice.");
                    System.out.println("Please choose between 1 and 3.");

            }

        } while (choice != 3);

        sc.close();

    }

}