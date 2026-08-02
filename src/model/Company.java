package model;

import java.util.Scanner;

import service.CompanyLoginService;
import service.CompanyRegistrationService;

public class Company {

    private static final CompanyRegistrationService
            companyRegistrationService =
            CompanyRegistrationService.getInstance();

    private static final CompanyLoginService
            companyLoginService =
            CompanyLoginService.getInstance();

    private static final Scanner scanner =
            new Scanner(System.in);

    private String companyEmail;
    private String password;
    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String industry;
    private String companySize;
    private String description;

    public Company() {
    }

    public Company(
            String companyEmail,
            String password,
            String companyName) {

        this.companyEmail = safeValue(companyEmail).toLowerCase();
        this.password = safeValue(password);
        this.companyName = safeValue(companyName);
    }

    public Company(
            String companyEmail,
            String password,
            String companyName,
            String companyAddress,
            String companyPhone,
            String industry,
            String companySize,
            String description) {

        this.companyEmail = safeValue(companyEmail).toLowerCase();
        this.password = safeValue(password);
        this.companyName = safeValue(companyName);
        this.companyAddress = safeValue(companyAddress);
        this.companyPhone = safeValue(companyPhone);
        this.industry = safeValue(industry);
        this.companySize = safeValue(companySize);
        this.description = safeValue(description);
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public String getIndustry() {
        return industry;
    }

    public String getCompanySize() {
        return companySize;
    }

    public String getDescription() {
        return description;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = safeValue(companyEmail).toLowerCase();
    }

    public void setPassword(String password) {
        this.password = safeValue(password);
    }

    public void setCompanyName(String companyName) {
        this.companyName = safeValue(companyName);
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = safeValue(companyAddress);
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = safeValue(companyPhone);
    }

    public void setIndustry(String industry) {
        this.industry = safeValue(industry);
    }

    public void setCompanySize(String companySize) {
        this.companySize = safeValue(companySize);
    }

    public void setDescription(String description) {
        this.description = safeValue(description);
    }

    @Override
    public String toString() {
        return safeValue(companyEmail) + "|"
                + safeValue(password) + "|"
                + safeValue(companyName) + "|"
                + safeValue(companyAddress) + "|"
                + safeValue(companyPhone) + "|"
                + safeValue(industry) + "|"
                + safeValue(companySize) + "|"
                + safeValue(description);
    }

    public static Company fromString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.split("\\|", -1);

        if (parts.length < 3) {
            return null;
        }

        return new Company(
                getPart(parts, 0),
                getPart(parts, 1),
                getPart(parts, 2),
                getPart(parts, 3),
                getPart(parts, 4),
                getPart(parts, 5),
                getPart(parts, 6),
                getPart(parts, 7)
        );
    }

    public static void showMenu() {
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

            int choice = readMenuChoice();

            switch (choice) {
                case 1:
                    companyRegistrationService.showRegistrationMenu();
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

    private static int readMenuChoice() {
        while (true) {
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                if (choice >= 0 && choice <= 2) {
                    return choice;
                }
            } catch (NumberFormatException exception) {
                // Common error message is shown below.
            }

            System.out.println(
                    "Invalid option. Please enter 0, 1, or 2."
            );
            System.out.print("Choose an option: ");
        }
    }

    private static String getPart(
            String[] parts,
            int index) {

        if (index >= parts.length || parts[index] == null) {
            return "";
        }

        return parts[index].trim();
    }

    private static String safeValue(String value) {
        if (value == null) {
            return "";
        }

        return value.trim().replace("|", "");
    }
}
