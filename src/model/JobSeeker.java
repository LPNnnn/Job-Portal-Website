package model;

import java.util.Scanner;
import service.LoginService;
import service.RegistrationService;

public class JobSeeker {

    private static final RegistrationService registrationService =
            RegistrationService.getInstance();

    private static final LoginService loginService =
            LoginService.getInstance();

    private static final Scanner scanner =
            new Scanner(System.in);

    // =====================================================
    // DATA FIELDS
    // =====================================================

    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String qualifications;
    private String experience;
    private String skills;
    private String location;
    private String dateOfBirth;
    private String gender;

    // =====================================================
    // CONSTRUCTORS
    // =====================================================

    public JobSeeker() {
    }

    public JobSeeker(
            String email,
            String password,
            String fullName) {

        this.email = email;
        this.password = password;
        this.fullName = fullName;

        this.phoneNumber = "";
        this.qualifications = "";
        this.experience = "";
        this.skills = "";
        this.location = "";
        this.dateOfBirth = "";
        this.gender = "";
    }

    public JobSeeker(
            String email,
            String password,
            String fullName,
            String phoneNumber,
            String qualifications,
            String experience,
            String skills,
            String location,
            String dateOfBirth,
            String gender) {

        this.email = safeValue(email);
        this.password = safeValue(password);
        this.fullName = safeValue(fullName);
        this.phoneNumber = safeValue(phoneNumber);
        this.qualifications = safeValue(qualifications);
        this.experience = safeValue(experience);
        this.skills = safeValue(skills);
        this.location = safeValue(location);
        this.dateOfBirth = safeValue(dateOfBirth);
        this.gender = safeValue(gender);
    }

    // =====================================================
    // GETTERS
    // =====================================================

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getQualifications() {
        return qualifications;
    }

    public String getExperience() {
        return experience;
    }

    public String getSkills() {
        return skills;
    }

    public String getLocation() {
        return location;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    // =====================================================
    // SETTERS
    // =====================================================

    public void setEmail(String email) {
        this.email = safeValue(email);
    }

    public void setPassword(String password) {
        this.password = safeValue(password);
    }

    public void setFullName(String fullName) {
        this.fullName = safeValue(fullName);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = safeValue(phoneNumber);
    }

    public void setQualifications(String qualifications) {
        this.qualifications = safeValue(qualifications);
    }

    public void setExperience(String experience) {
        this.experience = safeValue(experience);
    }

    public void setSkills(String skills) {
        this.skills = safeValue(skills);
    }

    public void setLocation(String location) {
        this.location = safeValue(location);
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = safeValue(dateOfBirth);
    }

    public void setGender(String gender) {
        this.gender = safeValue(gender);
    }

    // FILE CONVERSION

    @Override
    public String toString() {
        return safeValue(email) + "|"
                + safeValue(password) + "|"
                + safeValue(fullName) + "|"
                + safeValue(phoneNumber) + "|"
                + safeValue(qualifications) + "|"
                + safeValue(experience) + "|"
                + safeValue(skills) + "|"
                + safeValue(location) + "|"
                + safeValue(dateOfBirth) + "|"
                + safeValue(gender);
    }

    public static JobSeeker fromString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        /*
         * The -1 keeps empty values at the end.
         *
         * Example:
         * email|password|name|||||||
         *
         * Without -1, Java removes the empty fields at the end.
         */
        String[] parts = line.split("\\|", -1);

        if (parts.length < 3) {
            return null;
        }

        /*
         * Older records may contain only:
         * email|password|fullName
         *
         * getPart() safely returns an empty string for
         * profile fields that do not yet exist.
         */
        return new JobSeeker(
                getPart(parts, 0),
                getPart(parts, 1),
                getPart(parts, 2),
                getPart(parts, 3),
                getPart(parts, 4),
                getPart(parts, 5),
                getPart(parts, 6),
                getPart(parts, 7),
                getPart(parts, 8),
                getPart(parts, 9)
        );
    }

    private static String getPart(
            String[] parts,
            int index) {

        if (parts == null
                || index < 0
                || index >= parts.length
                || parts[index] == null) {

            return "";
        }

        return parts[index].trim();
    }

    private static String safeValue(String value) {
        if (value == null) {
            return "";
        }

        /*
         * The pipe symbol is used as the file separator.
         * Remove it from user-entered profile information
         * to prevent corrupted records.
         */
        return value.trim().replace("|", "");
    }

    // =====================================================
    // JOB SEEKER MAIN MENU
    // =====================================================

    public static void showMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n=================================");
            System.out.println("          JOB SEEKER MENU");
            System.out.println("=================================");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Back to Main Menu");
            System.out.println("=================================");

            int choice = readMenuChoice();

            switch (choice) {
                case 1:
                    registrationService.showRegistrationMenu();
                    break;

                case 2:
                    loginService.showLoginMenu();
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please enter 0, 1, or 2."
                    );
            }
        }
    }

    private static int readMenuChoice() {
        while (true) {
            System.out.print("Choose an option: ");

            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                if (choice >= 0 && choice <= 2) {
                    return choice;
                }

            } catch (NumberFormatException exception) {
                // Display the common error message below.
            }

            System.out.println(
                    "Invalid input. Please enter 0, 1, or 2."
            );
        }
    }
}