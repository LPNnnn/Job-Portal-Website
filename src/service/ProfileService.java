package service;

import model.JobSeeker;
import repository.JobSeekerRepository;
import util.ValidationUtil;

import java.util.Scanner;

public class ProfileService {

    private static ProfileService instance;

    private final JobSeekerRepository repository;
    private final Scanner scanner;

    private JobSeeker currentLoggedInSeeker;

    private ProfileService() {
        repository = JobSeekerRepository.getInstance();
        scanner = new Scanner(System.in);
    }

    public static ProfileService getInstance() {
        if (instance == null) {
            instance = new ProfileService();
        }

        return instance;
    }

    // =====================================================
    // CURRENT LOGGED-IN USER
    // =====================================================

    public void setCurrentUser(JobSeeker seeker) {
        currentLoggedInSeeker = seeker;
    }

    public JobSeeker getCurrentUser() {
        return currentLoggedInSeeker;
    }

    public boolean isLoggedIn() {
        return currentLoggedInSeeker != null;
    }

    public void logout() {
        if (!isLoggedIn()) {
            System.out.println("No job seeker is currently logged in.");
            return;
        }

        currentLoggedInSeeker = null;
        System.out.println("Logged out successfully.");
    }

    // =====================================================
    // PROFILE MENU
    // =====================================================

    public void showProfileMenu() {
        if (!isLoggedIn()) {
            System.out.println("Please log in first.");
            return;
        }

        boolean running = true;

        while (running && isLoggedIn()) {
            System.out.println("\n=================================");
            System.out.println("       PROFILE MANAGEMENT");
            System.out.println("=================================");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. Check Profile Completion");
            System.out.println("0. Back");
            System.out.println("=================================");

            int choice = readMenuChoice(
                    "Choose an option: ",
                    0,
                    3
            );

            switch (choice) {
                case 1:
                    viewProfile();
                    break;

                case 2:
                    editProfile();
                    break;

                case 3:
                    displayProfileCompletion();
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // =====================================================
    // VIEW PROFILE
    // =====================================================

    public void viewProfile() {
        if (!isLoggedIn()) {
            System.out.println(
                    "Error: No job seeker is currently logged in."
            );
            return;
        }

        JobSeeker seeker = currentLoggedInSeeker;

        System.out.println("\n=================================");
        System.out.println("          JOB SEEKER PROFILE");
        System.out.println("=================================");
        System.out.println(
                "Email          : " + displayValue(seeker.getEmail())
        );
        System.out.println(
                "Full Name      : " + displayValue(seeker.getFullName())
        );
        System.out.println(
                "Phone Number   : " + displayValue(seeker.getPhoneNumber())
        );
        System.out.println(
                "Qualifications : " + displayValue(seeker.getQualifications())
        );
        System.out.println(
                "Experience     : " + displayValue(seeker.getExperience())
        );
        System.out.println(
                "Skills         : " + displayValue(seeker.getSkills())
        );
        System.out.println(
                "Location       : " + displayValue(seeker.getLocation())
        );
        System.out.println(
                "Date of Birth  : " + displayValue(seeker.getDateOfBirth())
        );
        System.out.println(
                "Gender         : " + displayValue(seeker.getGender())
        );
        System.out.println("=================================");

        int completion = calculateProfileCompletion(seeker);

        System.out.println(
                "Profile Completion: " + completion + "%"
        );
    }

    // =====================================================
    // EDIT PROFILE
    // =====================================================

    public void editProfile() {
        if (!isLoggedIn()) {
            System.out.println(
                    "Error: No job seeker is currently logged in."
            );
            return;
        }

        JobSeeker current = currentLoggedInSeeker;

        System.out.println("\n=================================");
        System.out.println("             EDIT PROFILE");
        System.out.println("=================================");
        System.out.println(
                "Press Enter to keep the current value."
        );
        System.out.println(
                "Enter 0 at any field to cancel."
        );
        System.out.println(
                "Email and password cannot be changed here."
        );
        System.out.println("=================================");

        String fullName = readRequiredValidatedValue(
                "Full Name",
                current.getFullName(),
                ValidationUtil::isValidName,
                "Full name must contain at least 2 valid characters."
        );

        if (fullName == null) {
            cancelEdit();
            return;
        }

        String phoneNumber = readRequiredValidatedValue(
                "Phone Number",
                current.getPhoneNumber(),
                ValidationUtil::isValidPhoneNumber,
                ValidationUtil.getPhoneRequirementMessage()
        );

        if (phoneNumber == null) {
            cancelEdit();
            return;
        }

        String qualifications = readOptionalValue(
                "Qualifications",
                current.getQualifications(),
                200
        );

        if (qualifications == null) {
            cancelEdit();
            return;
        }

        String experience = readOptionalValue(
                "Experience",
                current.getExperience(),
                300
        );

        if (experience == null) {
            cancelEdit();
            return;
        }

        String skills = readOptionalValue(
                "Skills",
                current.getSkills(),
                200
        );

        if (skills == null) {
            cancelEdit();
            return;
        }

        String location = readOptionalValue(
                "Location",
                current.getLocation(),
                100
        );

        if (location == null) {
            cancelEdit();
            return;
        }

        String dateOfBirth = readOptionalValidatedValue(
                "Date of Birth (YYYY-MM-DD)",
                current.getDateOfBirth(),
                ValidationUtil::isValidDateOfBirth,
                "Date of birth must use YYYY-MM-DD format "
                        + "and cannot be in the future."
        );

        if (dateOfBirth == null) {
            cancelEdit();
            return;
        }

        String gender = readOptionalValidatedValue(
                "Gender (Male/Female/Other)",
                current.getGender(),
                ValidationUtil::isValidGender,
                "Gender must be Male, Female, or Other."
        );

        if (gender == null) {
            cancelEdit();
            return;
        }

        gender = normalizeGender(gender);

        JobSeeker updatedProfile = new JobSeeker(
                current.getEmail(),
                current.getPassword(),
                fullName,
                phoneNumber,
                qualifications,
                experience,
                skills,
                location,
                dateOfBirth,
                gender
        );

        if (!isProfileModified(current, updatedProfile)) {
            System.out.println(
                    "No changes were made. "
                            + "The profile remains unchanged."
            );
            return;
        }

        displayProfileChanges(current, updatedProfile);

        if (!confirmUpdate()) {
            System.out.println(
                    "Profile update cancelled. No changes were saved."
            );
            return;
        }

        String result = updateProfile(updatedProfile);
        System.out.println(result);
    }

    // =====================================================
    // UPDATE PROFILE
    // =====================================================

    public String updateProfile(JobSeeker updatedProfile) {
        if (!isLoggedIn()) {
            return "Error: No job seeker is currently logged in.";
        }

        if (updatedProfile == null) {
            return "Error: Updated profile information is missing.";
        }

        /*
         * The email must remain the same because it identifies
         * the account used during login.
         */
        if (!safeEqualsIgnoreCase(
                currentLoggedInSeeker.getEmail(),
                updatedProfile.getEmail())) {

            return "Error: Registered email cannot be changed.";
        }

        /*
         * Keep the original encrypted password.
         */
        updatedProfile.setPassword(
                currentLoggedInSeeker.getPassword()
        );

        String validationErrors =
                ValidationUtil.validateProfile(updatedProfile);

        if (!validationErrors.isBlank()) {
            return "Profile update failed:\n"
                    + validationErrors;
        }

        if (!isProfileModified(
                currentLoggedInSeeker,
                updatedProfile)) {

            return "No changes were made. "
                    + "The profile remains unchanged.";
        }

        boolean updated = repository.updateProfile(
                currentLoggedInSeeker.getEmail(),
                updatedProfile
        );

        if (!updated) {
            return "Error: Unable to update the profile.";
        }

        currentLoggedInSeeker = updatedProfile;

        return "Profile updated successfully.";
    }

    // =====================================================
    // CHECK PROFILE CHANGES
    // =====================================================

    public boolean isProfileModified(
            JobSeeker original,
            JobSeeker updated) {

        if (original == null || updated == null) {
            return false;
        }

        return !safeEquals(
                    original.getFullName(),
                    updated.getFullName())

                || !safeEquals(
                    original.getPhoneNumber(),
                    updated.getPhoneNumber())

                || !safeEquals(
                    original.getQualifications(),
                    updated.getQualifications())

                || !safeEquals(
                    original.getExperience(),
                    updated.getExperience())

                || !safeEquals(
                    original.getSkills(),
                    updated.getSkills())

                || !safeEquals(
                    original.getLocation(),
                    updated.getLocation())

                || !safeEquals(
                    original.getDateOfBirth(),
                    updated.getDateOfBirth())

                || !safeEqualsIgnoreCase(
                    original.getGender(),
                    updated.getGender());
    }

    private void displayProfileChanges(
            JobSeeker original,
            JobSeeker updated) {

        System.out.println("\n=================================");
        System.out.println("          PROFILE CHANGES");
        System.out.println("=================================");

        printChangedField(
                "Full Name",
                original.getFullName(),
                updated.getFullName()
        );

        printChangedField(
                "Phone Number",
                original.getPhoneNumber(),
                updated.getPhoneNumber()
        );

        printChangedField(
                "Qualifications",
                original.getQualifications(),
                updated.getQualifications()
        );

        printChangedField(
                "Experience",
                original.getExperience(),
                updated.getExperience()
        );

        printChangedField(
                "Skills",
                original.getSkills(),
                updated.getSkills()
        );

        printChangedField(
                "Location",
                original.getLocation(),
                updated.getLocation()
        );

        printChangedField(
                "Date of Birth",
                original.getDateOfBirth(),
                updated.getDateOfBirth()
        );

        printChangedField(
                "Gender",
                original.getGender(),
                updated.getGender()
        );

        System.out.println("=================================");
    }

    private void printChangedField(
            String fieldName,
            String oldValue,
            String newValue) {

        if (!safeEquals(oldValue, newValue)) {
            System.out.println(fieldName + ":");
            System.out.println(
                    "  Old: " + displayValue(oldValue)
            );
            System.out.println(
                    "  New: " + displayValue(newValue)
            );
        }
    }

    // =====================================================
    // PROFILE COMPLETION
    // =====================================================

    public void displayProfileCompletion() {
        if (!isLoggedIn()) {
            System.out.println(
                    "No job seeker is currently logged in."
            );
            return;
        }

        int percentage = calculateProfileCompletion(
                currentLoggedInSeeker
        );

        System.out.println("\n=================================");
        System.out.println("       PROFILE COMPLETION");
        System.out.println("=================================");
        System.out.println(
                createProgressBar(percentage)
        );

        if (percentage == 100) {
            System.out.println("Your profile is complete.");
        } else {
            System.out.println("Missing profile fields:");

            printMissingField(
                    "Phone Number",
                    currentLoggedInSeeker.getPhoneNumber()
            );

            printMissingField(
                    "Qualifications",
                    currentLoggedInSeeker.getQualifications()
            );

            printMissingField(
                    "Experience",
                    currentLoggedInSeeker.getExperience()
            );

            printMissingField(
                    "Skills",
                    currentLoggedInSeeker.getSkills()
            );

            printMissingField(
                    "Location",
                    currentLoggedInSeeker.getLocation()
            );

            printMissingField(
                    "Date of Birth",
                    currentLoggedInSeeker.getDateOfBirth()
            );

            printMissingField(
                    "Gender",
                    currentLoggedInSeeker.getGender()
            );
        }

        System.out.println("=================================");
    }

    public int calculateProfileCompletion(JobSeeker seeker) {
        if (seeker == null) {
            return 0;
        }

        int completed = 0;
        int total = 9;

        if (hasValue(seeker.getEmail())) {
            completed++;
        }

        if (hasValue(seeker.getFullName())) {
            completed++;
        }

        if (hasValue(seeker.getPhoneNumber())) {
            completed++;
        }

        if (hasValue(seeker.getQualifications())) {
            completed++;
        }

        if (hasValue(seeker.getExperience())) {
            completed++;
        }

        if (hasValue(seeker.getSkills())) {
            completed++;
        }

        if (hasValue(seeker.getLocation())) {
            completed++;
        }

        if (hasValue(seeker.getDateOfBirth())) {
            completed++;
        }

        if (hasValue(seeker.getGender())) {
            completed++;
        }

        return (completed * 100) / total;
    }

    private String createProgressBar(int percentage) {
        int totalBlocks = 20;
        int completedBlocks =
                percentage * totalBlocks / 100;

        StringBuilder bar = new StringBuilder("[");

        for (int i = 0; i < totalBlocks; i++) {
            if (i < completedBlocks) {
                bar.append("#");
            } else {
                bar.append("-");
            }
        }

        bar.append("] ")
           .append(percentage)
           .append("%");

        return bar.toString();
    }

    private void printMissingField(
            String fieldName,
            String value) {

        if (!hasValue(value)) {
            System.out.println("- " + fieldName);
        }
    }

    // =====================================================
    // INPUT METHODS
    // =====================================================

    private String readRequiredValidatedValue(
            String fieldName,
            String currentValue,
            StringValidator validator,
            String errorMessage) {

        while (true) {
            System.out.print(
                    fieldName
                            + " ["
                            + displayValue(currentValue)
                            + "]: "
            );

            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                return null;
            }

            if (input.isEmpty()) {
                if (!hasValue(currentValue)) {
                    System.out.println(
                            "Error: " + fieldName
                                    + " is required."
                    );
                    continue;
                }

                return currentValue;
            }

            if (validator.isValid(input)) {
                return input;
            }

            System.out.println(
                    "Error: " + errorMessage
            );
        }
    }

    private String readOptionalValidatedValue(
            String fieldName,
            String currentValue,
            StringValidator validator,
            String errorMessage) {

        while (true) {
            System.out.print(
                    fieldName
                            + " ["
                            + displayValue(currentValue)
                            + "]: "
            );

            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                return null;
            }

            if (input.isEmpty()) {
                return normalize(currentValue);
            }

            if (validator.isValid(input)) {
                return input;
            }

            System.out.println(
                    "Error: " + errorMessage
            );
        }
    }

    private String readOptionalValue(
            String fieldName,
            String currentValue,
            int maximumLength) {

        while (true) {
            System.out.print(
                    fieldName
                            + " ["
                            + displayValue(currentValue)
                            + "]: "
            );

            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                return null;
            }

            if (input.isEmpty()) {
                return normalize(currentValue);
            }

            if (input.length() > maximumLength) {
                System.out.println(
                        "Error: " + fieldName
                                + " cannot exceed "
                                + maximumLength
                                + " characters."
                );
                continue;
            }

            return input;
        }
    }

    private int readMenuChoice(
            String message,
            int minimum,
            int maximum) {

        while (true) {
            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                if (choice >= minimum
                        && choice <= maximum) {

                    return choice;
                }

            } catch (NumberFormatException exception) {
                // Display common error below.
            }

            System.out.println(
                    "Invalid option. Enter a number from "
                            + minimum
                            + " to "
                            + maximum
                            + "."
            );
        }
    }

    private boolean confirmUpdate() {
        while (true) {
            System.out.print(
                    "Confirm profile update? (Y/N): "
            );

            String answer =
                    scanner.nextLine().trim();

            if (answer.equalsIgnoreCase("Y")
                    || answer.equalsIgnoreCase("Yes")) {

                return true;
            }

            if (answer.equalsIgnoreCase("N")
                    || answer.equalsIgnoreCase("No")) {

                return false;
            }

            System.out.println(
                    "Invalid input. Please enter Y or N."
            );
        }
    }

    // =====================================================
    // HELPER METHODS
    // =====================================================

    private boolean hasValue(String value) {
        return value != null
                && !value.trim().isEmpty();
    }

    private String displayValue(String value) {
        if (!hasValue(value)) {
            return "Not provided";
        }

        return value.trim();
    }

    private String normalize(String value) {
        if (value == null) {
            return "";
        }

        return value.trim();
    }

    private boolean safeEquals(
            String first,
            String second) {

        return normalize(first)
                .equals(normalize(second));
    }

    private boolean safeEqualsIgnoreCase(
            String first,
            String second) {

        return normalize(first)
                .equalsIgnoreCase(normalize(second));
    }

    private String normalizeGender(String gender) {
        if (!hasValue(gender)) {
            return "";
        }

        String value = gender.trim().toLowerCase();

        switch (value) {
            case "m":
            case "male":
                return "Male";

            case "f":
            case "female":
                return "Female";

            case "o":
            case "other":
                return "Other";

            default:
                return gender.trim();
        }
    }

    private void cancelEdit() {
        System.out.println(
                "Profile editing cancelled. "
                        + "No changes were saved."
        );
    }

    @FunctionalInterface
    private interface StringValidator {
        boolean isValid(String value);
    }
}