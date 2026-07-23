package service;

import model.JobSeeker;
import repository.JobSeekerRepository;
import util.ValidationUtil;
import util.PasswordUtil;
import java.util.Scanner;

public class RegistrationService {
    private static RegistrationService instance;
    private JobSeekerRepository repository;
    private Scanner scanner;
    
    private RegistrationService() {
        this.repository = JobSeekerRepository.getInstance();
        this.scanner = new Scanner(System.in);
    }
    
    public static RegistrationService getInstance() {
        if (instance == null) {
            instance = new RegistrationService();
        }
        return instance;
    }
    
    // ===== REGISTRATION MENU WITH VALIDATION LOOPS =====
    public void showRegistrationMenu() {
        System.out.println("\n=== REGISTER AS JOB SEEKER ===");
        System.out.println("(Enter '0' at any field to cancel registration)");
        System.out.println();
        
        // Step 1: Get Full Name (with loop)
        String fullName = "";
        boolean validName = false;
        while (!validName) {
            System.out.print("Full Name: ");
            fullName = scanner.nextLine();
            
            if (fullName.equals("0")) { 
                System.out.println("Registration cancelled."); 
                return; 
            }
            
            if (ValidationUtil.isValidName(fullName)) {
                validName = true;
            } else {
                System.out.println("Error: Full name must be at least 2 characters and contain only letters, spaces, dots, or hyphens.");
                System.out.println("Please try again.\n");
            }
        }
        
        // Step 2: Get Email (with loop)
        String email = "";
        boolean validEmail = false;
        while (!validEmail) {
            System.out.print("Email: ");
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
                System.out.println("   - Example: john@example.com");
                System.out.println("   - Your input: '" + email + "' is not valid.");
                System.out.println("Please try again.\n");
                continue;
            }
            
            if (repository.existsByEmail(email)) {
                System.out.println("Error: Email already registered. Please use a different email.");
                System.out.println("Please try again.\n");
                continue;
            }
            
            validEmail = true;
        }
        
        // Step 3: Get Password (with loop)
        String password = "";
        boolean validPassword = false;
        while (!validPassword) {
            System.out.print("Password (min " + PasswordUtil.MIN_PASSWORD_LENGTH + " characters with uppercase, lowercase, digit, special): ");
            password = scanner.nextLine();
            
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
        String result = registerJobSeeker(email, password, fullName);
        System.out.println(result);
    }
    
    // ===== REGISTRATION FUNCTION =====
    public String registerJobSeeker(String email, String password, String fullName) {
        // Validate full name
        if (!ValidationUtil.isValidName(fullName)) {
            return "Full name must be at least 2 characters and contain only letters, spaces, dots, or hyphens.";
        }
        
        // Validate email
        if (!ValidationUtil.isValidEmail(email)) {
            return "Invalid email format. Email must be like user@domain.com";
        }
        
        // Validate password
        if (!ValidationUtil.isValidPassword(password)) {
            return "Password must be at least " + PasswordUtil.MIN_PASSWORD_LENGTH + 
                   " characters with uppercase, lowercase, digit, and special character (!@#$%^&*).";
        }
        
        if (repository.existsByEmail(email)) {
            return "Email already registered. Please use a different email.";
        }
        
        String encryptedPassword = PasswordUtil.encryptPassword(password);
        JobSeeker newSeeker = new JobSeeker(email, encryptedPassword, fullName);
        repository.save(newSeeker);
        
        return "Registration successful! You can now login.";
    }
}