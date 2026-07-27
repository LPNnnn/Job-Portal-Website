package model;

public class Company {

    private String companyName;
    private String companyEmail;
    private String password;

    public Company(String companyName, String companyEmail, String password) {
        this.companyName = safeValue(companyName);
        this.companyEmail = safeValue(companyEmail).toLowerCase();
        this.password = safeValue(password);
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public String getEmail() {
        return companyEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setCompanyName(String companyName) {
        this.companyName = safeValue(companyName);
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = safeValue(companyEmail).toLowerCase();
    }

    public void setPassword(String password) {
        this.password = safeValue(password);
    }

    @Override
    public String toString() {
        return safeValue(companyName) + "|"
                + safeValue(companyEmail) + "|"
                + safeValue(password);
    }

    public static Company fromString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] data = line.split("\\|", -1);

        if (data.length < 3) {
            return null;
        }

        return new Company(data[0], data[1], data[2]);
    }

    private static String safeValue(String value) {
        if (value == null) {
            return "";
        }

        return value.trim().replace("|", " ");
    }
}
