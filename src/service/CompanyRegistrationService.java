package service;

import model.Company;
import repository.CompanyRepository;
import util.PasswordUtil;
import util.ValidationUtil;

import java.util.Scanner;

public class CompanyRegistrationService {

    private final CompanyRepository repository =
            new CompanyRepository();

    public void register(Scanner scanner) {
        System.out.println("\n========================================");
        System.out.println("         EMPLOYER REGISTRATION");
        System.out.println("========================================");
        System.out.println("Enter 0 at any field to cancel.");

        String companyName = readCompanyName(scanner);

        if (companyName == null) {
            System.out.println("Employer registration cancelled.");
            return;
        }

        String companyEmail = readCompanyEmail(scanner);

        if (companyEmail == null) {
            System.out.println("Employer registration cancelled.");
            return;
        }

        String password = readPassword(scanner);

        if (password == null) {
            System.out.println("Employer registration cancelled.");
            return;
        }

        Company company = new Company(
                companyName,
                companyEmail,
                PasswordUtil.encryptPassword(password)
        );

        if (repository.save(company)) {
            System.out.println(
                    "\nEmployer registration successful."
            );
        } else {
            System.out.println(
                    "\nEmployer registration failed."
            );
        }
    }

    private String readCompanyName(Scanner scanner) {
        while (true) {
            System.out.print("Company Name : ");
            String companyName = scanner.nextLine().trim();

            if (companyName.equals("0")) {
                return null;
            }

            if (!ValidationUtil.isValidCompanyName(companyName)) {
                System.out.println(
                        "Company name must contain 2 to 100 valid characters.\n"
                );
                continue;
            }

            return companyName;
        }
    }

    private String readCompanyEmail(Scanner scanner) {
        while (true) {
            System.out.print("Company Email : ");
            String email = scanner.nextLine().trim();

            if (email.equals("0")) {
                return null;
            }

            if (!ValidationUtil.isValidEmail(email)) {
                System.out.println(ValidationUtil.getEmailRequirementMessage() + "\n");
            } else if (repository.emailExists(email)) {
                System.out.println(
                        "Company email already exists.\n"
                );
            } else {
                return email.toLowerCase();
            }
        }
    }

    private String readPassword(Scanner scanner) {
        while (true) {
            System.out.print("Password : ");
            String password = scanner.nextLine();

            if (password.equals("0")) {
                return null;
            }

            if (!ValidationUtil.isValidPassword(password)) {
                System.out.println(
                        ValidationUtil.getPasswordRequirementMessage() + "\n"
                );
                continue;
            }

            return password;
        }
    }
}
