package model;

public class Administrator {

    private String administratorName;
    private String username;
    private String password;

    public Administrator(
            String administratorName,
            String username,
            String password) {

        this.administratorName = safeValue(administratorName);
        this.username = safeValue(username).toLowerCase();
        this.password = safeValue(password);
    }

    public String getAdministratorName() {
        return administratorName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setAdministratorName(String administratorName) {
        this.administratorName = safeValue(administratorName);
    }

    public void setUsername(String username) {
        this.username = safeValue(username).toLowerCase();
    }

    public void setPassword(String password) {
        this.password = safeValue(password);
    }

    @Override
    public String toString() {
        return safeValue(administratorName) + "|"
                + safeValue(username) + "|"
                + safeValue(password);
    }

    public static Administrator fromString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] data = line.split("\\|", -1);

        if (data.length < 3) {
            return null;
        }

        return new Administrator(data[0], data[1], data[2]);
    }

    private static String safeValue(String value) {
        if (value == null) {
            return "";
        }

        return value.trim().replace("|", " ");
    }
}
