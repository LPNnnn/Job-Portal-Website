import java.util.Scanner;
import service.RegistrationService;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        RegistrationService service =
                new RegistrationService();

        int choice;

        do {

            System.out.println("\n==============================");
            System.out.println("          JOB PORTAL");
            System.out.println("==============================");
            System.out.println("1. Register");
            System.out.println("2. Exit");
            System.out.print("Choice : ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

            System.out.println("\n==============================");
            System.out.println("          JOB PORTAL");
            System.out.println("==============================");

                    System.out.print("Full Name : ");
                    String name = sc.nextLine();

                    System.out.print("Email : ");
                    String email = sc.nextLine();

                    System.out.print("Password : ");
                    String password = sc.nextLine();

                    String result =
                            service.register(name, email, password);

                    System.out.println("\n" + result);

                    break;

                case 2:

                    System.out.println("Thank you.");

                    break;

                default:

                    System.out.println("Invalid choice.");

            }

        } while (choice != 2);

        sc.close();

    }

}