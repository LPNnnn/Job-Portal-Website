package repository;

import java.util.ArrayList;
import java.util.List;
import model.JobSeeker;
import util.PasswordUtil;

public class JobSeekerRepository {
    private static List<JobSeeker> users = new ArrayList<>();

    // Add some sample users for testing
    static {
        // Sample user for testing (password: "password123")
        users.add(new JobSeeker("John Doe", "john@example.com", PasswordUtil.encrypt("password123")));
        users.add(new JobSeeker("Jane Smith", "jane@example.com", PasswordUtil.encrypt("password123")));
        users.add(new JobSeeker("Admin User", "admin@test.com", PasswordUtil.encrypt("admin1234")));
    }

    public void save(JobSeeker jobSeeker) {
        users.add(jobSeeker);
        System.out.println("User saved successfully!");
    }

    public JobSeeker login(String email, String password) {
        for (JobSeeker user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public JobSeeker findByEmail(String email) {
        for (JobSeeker user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    // For debugging - optional
    public void displayAllUsers() {
        System.out.println("\n=== All Registered Users ===");
        for (JobSeeker user : users) {
            System.out.println(user);
        }
    }
}