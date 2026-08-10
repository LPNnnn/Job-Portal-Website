package service;

import model.JobSeeker;
import repository.JobSeekerRepository;

import java.util.List;

public class RegisteredJobSeekerService {

    private static RegisteredJobSeekerService instance;

    private final JobSeekerRepository repository;

    private RegisteredJobSeekerService() {
        repository = JobSeekerRepository.getInstance();
    }

    public static RegisteredJobSeekerService getInstance() {
        if (instance == null) {
            instance = new RegisteredJobSeekerService();
        }

        return instance;
    }

    public void viewAllRegisteredJobSeekers() {
        repository.reload();

        List<JobSeeker> jobSeekers =
                repository.findAll();

        System.out.println("\n========================================");
        System.out.println("      REGISTERED JOB SEEKERS");
        System.out.println("========================================");

        if (jobSeekers.isEmpty()) {
            System.out.println(
                    "No registered job seekers were found."
            );
            System.out.println("========================================");
            return;
        }

        int displayedCount = 0;

        for (JobSeeker jobSeeker : jobSeekers) {
            if (!isValidRecord(jobSeeker)) {
                continue;
            }

            displayedCount++;

            System.out.println(
                    "No.            : " + displayedCount
            );
            System.out.println(
                    "Full Name      : "
                            + displayValue(
                            jobSeeker.getFullName())
            );
            System.out.println(
                    "Email          : "
                            + displayValue(
                            jobSeeker.getEmail())
            );
            System.out.println(
                    "Phone Number   : "
                            + displayValue(
                            jobSeeker.getPhoneNumber())
            );
            System.out.println(
                    "Qualifications : "
                            + displayValue(
                            jobSeeker.getQualifications())
            );
            System.out.println(
                    "Experience     : "
                            + displayValue(
                            jobSeeker.getExperience())
            );
            System.out.println(
                    "Skills         : "
                            + displayValue(
                            jobSeeker.getSkills())
            );
            System.out.println(
                    "Location       : "
                            + displayValue(
                            jobSeeker.getLocation())
            );
            System.out.println(
                    "----------------------------------------"
            );
        }

        if (displayedCount == 0) {
            System.out.println(
                    "No valid registered job seeker records "
                            + "were found."
            );
        } else {
            System.out.println(
                    "Total Registered Job Seekers: "
                            + displayedCount
            );
        }

        System.out.println("========================================");
    }

    private boolean isValidRecord(JobSeeker jobSeeker) {
        return jobSeeker != null
                && jobSeeker.getEmail() != null
                && !jobSeeker.getEmail().trim().isEmpty()
                && jobSeeker.getFullName() != null
                && !jobSeeker.getFullName().trim().isEmpty();
    }

    private String displayValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "Not provided";
        }

        return value.trim();
    }
}

// 10/08/2026
