package util;

public class PasswordUtil {

    public static final int MIN_PASSWORD_LENGTH = 8;

    public static String encryptPassword(String password) {
        if (password == null || password.isEmpty()) {
            return "";
        }

        StringBuilder encrypted = new StringBuilder();

        for (char character : password.toCharArray()) {
            encrypted.append((char) (character + 2));
        }

        return encrypted.toString();
    }

    public static String decryptPassword(String encrypted) {
        if (encrypted == null || encrypted.isEmpty()) {
            return "";
        }

        StringBuilder decrypted = new StringBuilder();

        for (char character : encrypted.toCharArray()) {
            decrypted.append((char) (character - 2));
        }

        return decrypted.toString();
    }
}
// Updated 23/7/2026 
