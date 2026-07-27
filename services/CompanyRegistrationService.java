package service;

import java.util.Scanner;
import model.Company;
import repository.CompanyRepository;
import util.PasswordUtil;
import util.ValidationUtil;

public class CompanyRegistrationService {

    private CompanyRepository repository =
            new CompanyRepository();

    public void register(Scanner sc) {

        System.out.println("\n===== COMPANY REGISTRATION =====");

        String companyName;

        while (true) {

            System.out.print("Company Name : ");
            companyName = sc.nextLine();

            if (ValidationUtil.isEmpty(companyName)) {

                System.out.println("Company name cannot be empty.\n");

            } else {

                break;

            }

        }

        String companyEmail;

        while (true) {

            System.out.print("Company Email : ");
            companyEmail = sc.nextLine();

            if (!ValidationUtil.isValidEmail(companyEmail)) {

                System.out.println("Invalid email format.\n");

            } else if (repository.emailExists(companyEmail)) {

                System.out.println("Company email already exists.\n");

            } else {

                break;

            }

        }

        String password;

        while (true) {

            System.out.print("Password : ");
            password = sc.nextLine();

            if (!ValidationUtil.isValidPassword(password)) {

                System.out.println("Password must contain at least 8 characters.\n");

            } else {

                break;

            }

        }

        password = PasswordUtil.encrypt(password);

        Company company =
                new Company(companyName,
                        companyEmail,
                        password);

        repository.save(company);

        System.out.println("\nCompany Registration Successful!");

    }

}