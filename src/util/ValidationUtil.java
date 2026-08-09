package util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import model.Company;
import model.JobSeeker;

public class ValidationUtil {

    // ===== EMAIL VALIDATION =====
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        email = email.trim().toLowerCase();

        // Basic email format validation
        if (!email.matches(
                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$")) {
            return false;
        }

        String domain = email.substring(email.indexOf("@") + 1);

        String[] allowedDomains = {
            "gmail.com",
            "yahoo.com",
            "hotmail.com",
            "outlook.com",
            "icloud.com"
        };

        for (String allowedDomain : allowedDomains) {
            if (domain.equals(allowedDomain)) {
                return true;
            }
        }

        return false;
    }

    // ===== PHONE NUMBER VALIDATION =====
    public static boolean isValidPhoneNumber(String phone) {
        if (phone == null) {
            return false;
        }

        phone = phone.trim();

        if (phone.isEmpty()) {
            return false;
        }

        /*
         * Accepts Malaysian mobile phone formats such as:
         * 0123456789
         * 012-3456789
         * 01112345678
         * 011-12345678
         */
        return phone.matches("^01\\d-?\\d{7,8}$");
    }

    // ===== PASSWORD VALIDATION =====
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }

        if (password.length() < PasswordUtil.MIN_PASSWORD_LENGTH) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialCharacter = false;

        String specialCharacters = "!@#$%^&*()_+-=[]{};':\"\\|,.<>/?";

        for (char character : password.toCharArray()) {
            if (Character.isUpperCase(character)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(character)) {
                hasLowercase = true;
            } else if (Character.isDigit(character)) {
                hasDigit = true;
            }

            if (specialCharacters.indexOf(character) >= 0) {
                hasSpecialCharacter = true;
            }
        }

        return hasUppercase
                && hasLowercase
                && hasDigit
                && hasSpecialCharacter;
    }

    // ===== NAME VALIDATION =====
    public static boolean isValidName(String name) {
        if (name == null) {
            return false;
        }

        name = name.trim();

        if (name.length() < 2 || name.length() > 50) {
            return false;
        }

        /*
         * Accepts:
         * John Tan
         * Mary-Jane
         * O'Connor
         * Ahmad A. Rahman
         */
        return name.matches("^[A-Za-z][A-Za-z\\s.'-]*$");
    }

    // ===== COMPANY NAME VALIDATION =====
    public static boolean isValidCompanyName(String companyName) {
        if (companyName == null) {
            return false;
        }

        companyName = companyName.trim();

        if (companyName.length() < 2 || companyName.length() > 100) {
            return false;
        }

        /*
         * Accepts:
         * ABC Sdn Bhd
         * Tesla Inc.
         * M&M Trading
         * Company (Malaysia) Sdn. Bhd.
         */
        return companyName.matches("^[A-Za-z0-9&.,'()\\-\\s]+$");
    }

    // ===== DATE OF BIRTH VALIDATION =====
    public static boolean isValidDateOfBirth(String dateOfBirth) {
        if (dateOfBirth == null) {
            return false;
        }

        dateOfBirth = dateOfBirth.trim();

        if (dateOfBirth.isEmpty()) {
            return false;
        }

        try {
            LocalDate birthDate = LocalDate.parse(dateOfBirth);
            LocalDate currentDate = LocalDate.now();

            // Date of birth cannot be in the future
            if (birthDate.isAfter(currentDate)) {
                return false;
            }

            // Prevent unrealistic birth dates
            if (birthDate.getYear() < 1900) {
                return false;
            }

            return true;

        } catch (DateTimeParseException exception) {
            return false;
        }
    }

    // ===== GENDER VALIDATION =====
    public static boolean isValidGender(String gender) {
        if (gender == null) {
            return false;
        }

        gender = gender.trim();

        if (gender.isEmpty()) {
            return false;
        }

        return gender.equalsIgnoreCase("Male")
                || gender.equalsIgnoreCase("Female")
                || gender.equalsIgnoreCase("Other")
                || gender.equalsIgnoreCase("M")
                || gender.equalsIgnoreCase("F")
                || gender.equalsIgnoreCase("O");
    }

    // ===== PROFILE VALIDATION =====
    public static String validateProfile(JobSeeker seeker) {
        StringBuilder errors = new StringBuilder();

        if (seeker == null) {
            return "Job seeker information is required.\n";
        }

        if (!isValidName(seeker.getFullName())) {
            errors.append(
                    "Full name must contain 2 to 50 characters and use letters only.\n"
            );
        }

        if (!isValidEmail(seeker.getEmail())) {
            errors.append(
                    "Email must use a valid format, such as user@example.com.\n"
            );
        }

        if (!isValidPhoneNumber(seeker.getPhoneNumber())) {
            errors.append(
                    "Phone number must use a valid Malaysian mobile format, "
                            + "such as 0123456789 or 012-3456789.\n"
            );
        }

        String dateOfBirth = seeker.getDateOfBirth();

        if (dateOfBirth != null && !dateOfBirth.trim().isEmpty()) {
            if (!isValidDateOfBirth(dateOfBirth)) {
                errors.append(
                        "Date of birth must use YYYY-MM-DD format and cannot be in the future.\n"
                );
            }
        }

        String gender = seeker.getGender();

        if (gender != null && !gender.trim().isEmpty()) {
            if (!isValidGender(gender)) {
                errors.append(
                        "Gender must be Male, Female, or Other.\n"
                );
            }
        }

        return errors.toString();
    }

    // ===== COMPANY VALIDATION =====
    public static String validateCompany(Company company) {
        StringBuilder errors = new StringBuilder();

        if (company == null) {
            return "Company information is required.\n";
        }

        if (!isValidCompanyName(company.getCompanyName())) {
            errors.append(
                    "Company name must contain 2 to 100 valid characters.\n"
            );
        }

        if (!isValidEmail(company.getCompanyEmail())) {
            errors.append(
                    "Company email must use a valid format, such as company@example.com.\n"
            );
        }

        if (!isValidPassword(company.getPassword())) {
            errors.append(
                    "Password must contain at least "
                            + PasswordUtil.MIN_PASSWORD_LENGTH
                            + " characters, including an uppercase letter, "
                            + "a lowercase letter, a number, and a special character.\n"
            );
        }

        return errors.toString();
    }

    // ===== EMAIL REQUIREMENT MESSAGE =====
    public static String getEmailRequirementMessage() {
        return "Email must use the format username@domain.extension, "
                + "such as john@example.com.";
    }

    // ===== PASSWORD REQUIREMENT MESSAGE =====
    public static String getPasswordRequirementMessage() {
        return "Password must contain at least "
                + PasswordUtil.MIN_PASSWORD_LENGTH
                + " characters, including uppercase, lowercase, "
                + "number, and special character.";
    }

    // ===== PHONE REQUIREMENT MESSAGE =====
    public static String getPhoneRequirementMessage() {
        return "Phone number must use a Malaysian mobile format, "
                + "such as 0123456789 or 012-3456789.";
    }
}