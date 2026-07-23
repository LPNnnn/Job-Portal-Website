package util;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        email = email.trim().toLowerCase();

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

        String specialCharacters =
                "!@#$%^&*()_+-=[]{};':\"\\|,.<>/?";

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

    public static boolean isValidName(String name) {
        if (name == null) {
            return false;
        }

        name = name.trim();

        if (name.length() < 2 || name.length() > 50) {
            return false;
        }

        return name.matches("^[A-Za-z][A-Za-z\\s.'-]*$");
    }

    public static boolean isValidCompanyName(String companyName) {
        if (companyName == null) {
            return false;
        }

        companyName = companyName.trim();

        if (companyName.length() < 2
                || companyName.length() > 100) {
            return false;
        }

        return companyName.matches(
                "^[A-Za-z0-9&.,'()\\-\\s]+$"
        );
    }

    public static String getEmailRequirementMessage() {
        return "Email must use one of these domains: "
                + "gmail.com, yahoo.com, hotmail.com, "
                + "outlook.com, or icloud.com.";
    }

    public static String getPasswordRequirementMessage() {
        return "Password must contain at least "
                + PasswordUtil.MIN_PASSWORD_LENGTH
                + " characters, including uppercase, lowercase, "
                + "number, and special character.";
    }
}
// Updated 23/7/2026 
