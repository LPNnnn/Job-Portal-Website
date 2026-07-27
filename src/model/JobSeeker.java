package model;

public class JobSeeker {

    private String fullName;
    private String email;
    private String password;

    public JobSeeker(String fullName, String email, String password) {
        this.fullName = safeValue(fullName);
        this.email = safeValue(email).toLowerCase();
        this.password = safeValue(password);
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setFullName(String fullName) {
        this.fullName = safeValue(fullName);
    }

    public void setEmail(String email) {
        this.email = safeValue(email).toLowerCase();
    }

    public void setPassword(String password) {
        this.password = safeValue(password);
    }

    @Override
    public String toString() {
        return safeValue(fullName) + "|"
                + safeValue(email) + "|"
                + safeValue(password);
    }

    public static JobSeeker fromString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] data = line.split("\\|", -1);

        if (data.length < 3) {
            return null;
        }

        return new JobSeeker(data[0], data[1], data[2]);
    }

    private static String safeValue(String value) {
        if (value == null) {
            return "";
        }

        return value.trim().replace("|", " ");
    }
}
