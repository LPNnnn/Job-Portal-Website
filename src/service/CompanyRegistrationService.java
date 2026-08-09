package service;

import model.Company;
import repository.CompanyRepository;
import util.ValidationUtil;
import util.PasswordUtil;
import util.ConsoleInputUtil;
import java.util.Scanner;

public class CompanyRegistrationService {
    private static CompanyRegistrationService instance;
    private CompanyRepository repository;
    private Scanner scanner;
    
    private CompanyRegistrationService() {
        this.repository = CompanyRepository.getInstance();
        this.scanner = new Scanner(System.in);
    }
    
    public static CompanyRegistrationService getInstance() {
        if (instance == null) {
            instance = new CompanyRegistrationService();
        }
        return instance;
    }
    
    // ===== COMPANY REGISTRATION MENU WITH VALIDATION LOOPS =====
    public void showRegistrationMenu() {
        System.out.println("\n=== REGISTER AS EMPLOYER ===");
        System.out.println("(Enter '0' at any field to cancel registration)");
        System.out.println();
        
        // Step 1: Get Company Name (with loop)
        String companyName = "";
        boolean validName = false;
        while (!validName) {
            System.out.print("Company Name: ");
            companyName = scanner.nextLine();
            
            if (companyName.equals("0")) { 
                System.out.println("Registration cancelled."); 
                return; 
            }
            
            if (ValidationUtil.isValidCompanyName(companyName)) {
                validName = true;
            } else {
                System.out.println("Error: Company name must be at least 2 characters.");
                System.out.println("Please try again.\n");
            }
        }
        
        // Step 2: Get Company Email (with loop)
        String email = "";
        boolean validEmail = false;
        while (!validEmail) {
            System.out.print("Company Email: ");
            email = scanner.nextLine();
            
            if (email.equals("0")) { 
                System.out.println("Registration cancelled."); 
                return; 
            }
            
            if (!ValidationUtil.isValidEmail(email)) {
                System.out.println("Error: Invalid email format.");
                System.out.println("   - Must contain '@' symbol");
                System.out.println("   - Must have a valid domain (e.g., gmail.com, yahoo.com)");
                System.out.println("   - Must have at least 2 characters after the last dot (e.g., .com, .org)");
                System.out.println("   - Example: company@example.com");
                System.out.println("   - Your input: '" + email + "' is not valid.");
                System.out.println("Please try again.\n");
                continue;
            }
            
            if (repository.existsByEmail(email)) {
                System.out.println("Error: Company email already registered. Please use a different email.");
                System.out.println("Please try again.\n");
                continue;
            }
            
            validEmail = true;
        }
        
        // Step 3: Get Password (with loop)
        String password = "";
        boolean validPassword = false;
        while (!validPassword) {
            password = ConsoleInputUtil.readPassword(
                    scanner,
                    "Password (min "
                            + PasswordUtil.MIN_PASSWORD_LENGTH
                            + " characters with uppercase, lowercase, "
                            + "digit, special): "
            );
            
            if (password.equals("0")) { 
                System.out.println("Registration cancelled."); 
                return; 
            }
            
            if (ValidationUtil.isValidPassword(password)) {
                validPassword = true;
            } else {
                System.out.println("Error: Password must be at least " + PasswordUtil.MIN_PASSWORD_LENGTH + 
                                   " characters with uppercase, lowercase, digit, and special character (!@#$%^&*).");
                System.out.println("Please try again.\n");
            }
        }
        
        // All validations passed - proceed with registration
        String result = registerCompany(email, password, companyName);
        System.out.println(result);
    }
    
    // ===== COMPANY REGISTRATION FUNCTION =====
    public String registerCompany(String companyEmail, String password, String companyName) {
        if (!ValidationUtil.isValidCompanyName(companyName)) {
            return "Company name must be at least 2 characters.";
        }
        
        if (!ValidationUtil.isValidEmail(companyEmail)) {
            return "Invalid company email format. Email must be like company@domain.com";
        }
        
        if (!ValidationUtil.isValidPassword(password)) {
            return "Password must be at least " + PasswordUtil.MIN_PASSWORD_LENGTH + 
                   " characters with uppercase, lowercase, digit, and special character (!@#$%^&*).";
        }
        
        if (repository.existsByEmail(companyEmail)) {
            return "Company email already registered. Please use a different email.";
        }
        
        String encryptedPassword = PasswordUtil.encryptPassword(password);
        Company newCompany = new Company(companyEmail, encryptedPassword, companyName);
        repository.save(newCompany);
        
        return "Company registration successful! You can now login.";
    }
}