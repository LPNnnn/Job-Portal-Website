package model;

import service.CompanyRegistrationService;
import java.util.Scanner;

public class Company {
    private static CompanyRegistrationService companyRegistrationService = CompanyRegistrationService.getInstance();
    private static Scanner scanner = new Scanner(System.in);
    
    // ===== DATA FIELDS =====
    private String companyEmail;
    private String password;
    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String industry;
    private String companySize;
    private String description;
    
    // ===== CONSTRUCTORS =====
    public Company() {}
    
    public Company(String companyEmail, String password, String companyName) {
        this.companyEmail = companyEmail;
        this.password = password;
        this.companyName = companyName;
    }
    
    public Company(String companyEmail, String password, String companyName, 
                   String companyAddress, String companyPhone, String industry, 
                   String companySize, String description) {
        this.companyEmail = companyEmail;
        this.password = password;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyPhone = companyPhone;
        this.industry = industry;
        this.companySize = companySize;
        this.description = description;
    }
    
    // ===== GETTERS AND SETTERS =====
    public String getCompanyEmail() { return companyEmail; }
    public String getPassword() { return password; }
    public String getCompanyName() { return companyName; }
    public String getCompanyAddress() { return companyAddress; }
    public String getCompanyPhone() { return companyPhone; }
    public String getIndustry() { return industry; }
    public String getCompanySize() { return companySize; }
    public String getDescription() { return description; }
    
    public void setCompanyEmail(String companyEmail) { this.companyEmail = companyEmail; }
    public void setPassword(String password) { this.password = password; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setCompanyAddress(String companyAddress) { this.companyAddress = companyAddress; }
    public void setCompanyPhone(String companyPhone) { this.companyPhone = companyPhone; }
    public void setIndustry(String industry) { this.industry = industry; }
    public void setCompanySize(String companySize) { this.companySize = companySize; }
    public void setDescription(String description) { this.description = description; }
    
    // ===== FILE I/O =====
    @Override
    public String toString() {
        return companyEmail + "|" + password + "|" + companyName + "|" + 
               (companyAddress != null ? companyAddress : "") + "|" +
               (companyPhone != null ? companyPhone : "") + "|" +
               (industry != null ? industry : "") + "|" +
               (companySize != null ? companySize : "") + "|" +
               (description != null ? description : "");
    }
    
    public static Company fromString(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        
        String[] parts = line.split("\\|");
        if (parts.length < 3) return null;
        
        Company company = new Company();
        company.setCompanyEmail(parts[0]);
        company.setPassword(parts[1]);
        company.setCompanyName(parts[2]);
        if (parts.length > 3) company.setCompanyAddress(parts[3]);
        if (parts.length > 4) company.setCompanyPhone(parts[4]);
        if (parts.length > 5) company.setIndustry(parts[5]);
        if (parts.length > 6) company.setCompanySize(parts[6]);
        if (parts.length > 7) company.setDescription(parts[7]);
        return company;
    }
    
    // ===== COMPANY MENU =====
    public static void showMenu() {
        while (true) {
            System.out.println("\n--- EMPLOYER MENU ---");
            System.out.println("1. Register (JOB-07)");
            System.out.println("2. Login (JOB-08) - Coming Soon");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    companyRegistrationService.showRegistrationMenu();
                    break;
                case 2:
                    System.out.println("Employer login (JOB-08) will be implemented soon.");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please choose 0, 1, or 2.");
            }
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