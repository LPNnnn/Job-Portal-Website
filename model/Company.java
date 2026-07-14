package model;

public class Company {

    private String companyName;
    private String companyEmail;
    private String password;

    // Constructor
    public Company(String companyName, String companyEmail, String password) {
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.password = password;
    }

    // Getter
    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public String getPassword() {
        return password;
    }

    // Setter
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return companyName + "," + companyEmail + "," + password;
    }
}