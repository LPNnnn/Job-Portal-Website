package util;

public class ValidationUtil {

    // Check empty
    public static boolean isEmpty(String input) {

        return input == null || input.trim().isEmpty();

    }

    // Email validation
    public static boolean isValidEmail(String email) {

        if (isEmpty(email)) {

            return false;

        }

        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    }

    // Password validation
    public static boolean isValidPassword(String password) {

        if (password == null) {

            return false;

        }

        return password.length() >= 8;

    }

}