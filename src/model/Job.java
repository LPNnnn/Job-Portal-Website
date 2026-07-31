package model;

public class Job {

    private String jobId;
    private String employerEmail;
    private String companyName;
    private String jobTitle;
    private String location;
    private String employmentType;
    private String salary;
    private String description;
    private String requirements;
    private String status;

    public Job(
            String jobId,
            String employerEmail,
            String companyName,
            String jobTitle,
            String location,
            String employmentType,
            String salary,
            String description,
            String requirements,
            String status) {

        this.jobId = safeValue(jobId);
        this.employerEmail =
                safeValue(employerEmail).toLowerCase();
        this.companyName = safeValue(companyName);
        this.jobTitle = safeValue(jobTitle);
        this.location = safeValue(location);
        this.employmentType = safeValue(employmentType);
        this.salary = safeValue(salary);
        this.description = safeValue(description);
        this.requirements = safeValue(requirements);
        this.status = safeValue(status);
    }

    public String getJobId() {
        return jobId;
    }

    public String getEmployerEmail() {
        return employerEmail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public String getSalary() {
        return salary;
    }

    public String getDescription() {
        return description;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return safeValue(jobId) + "|"
                + safeValue(employerEmail) + "|"
                + safeValue(companyName) + "|"
                + safeValue(jobTitle) + "|"
                + safeValue(location) + "|"
                + safeValue(employmentType) + "|"
                + safeValue(salary) + "|"
                + safeValue(description) + "|"
                + safeValue(requirements) + "|"
                + safeValue(status);
    }

    public static Job fromString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.split("\\|", -1);

        if (parts.length < 10) {
            return null;
        }

        return new Job(
                parts[0],
                parts[1],
                parts[2],
                parts[3],
                parts[4],
                parts[5],
                parts[6],
                parts[7],
                parts[8],
                parts[9]
        );
    }

    private static String safeValue(String value) {
        if (value == null) {
            return "";
        }

        return value.trim()
                .replace("|", " ")
                .replace("\r", " ")
                .replace("\n", " ");
    }
}
