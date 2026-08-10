package service;

import model.Company;
import model.Job;
import repository.JobRepository;

import java.util.Scanner;

public class JobPostingService {

    private static JobPostingService instance;

    private final JobRepository jobRepository;

    private JobPostingService() {
        jobRepository = JobRepository.getInstance();
    }

    public static JobPostingService getInstance() {
        if (instance == null) {
            instance = new JobPostingService();
        }

        return instance;
    }

    public void createAndPublishJob(
            Scanner scanner,
            Company loggedInCompany) {

        if (loggedInCompany == null) {
            System.out.println(
                    "Employer login is required before "
                            + "creating a job posting."
            );
            return;
        }

        System.out.println("\n========================================");
        System.out.println("      CREATE AND PUBLISH JOB POSTING");
        System.out.println("========================================");
        System.out.println("Enter 0 at any field to cancel.");

        jobRepository.reload();

        String jobTitle = readRequiredText(
                scanner,
                "Job Title : ",
                2,
                100,
                "Job title must contain 2 to 100 characters."
        );

        if (jobTitle == null) {
            cancel();
            return;
        }

        String location = readRequiredText(
                scanner,
                "Job Location : ",
                2,
                100,
                "Location must contain 2 to 100 characters."
        );

        if (location == null) {
            cancel();
            return;
        }

        String employmentType =
                readEmploymentType(scanner);

        if (employmentType == null) {
            cancel();
            return;
        }

        String salary = readSalary(scanner);

        if (salary == null) {
            cancel();
            return;
        }

        String description = readRequiredText(
                scanner,
                "Job Description : ",
                10,
                500,
                "Description must contain 10 to 500 characters."
        );

        if (description == null) {
            cancel();
            return;
        }

        String requirements = readRequiredText(
                scanner,
                "Job Requirements : ",
                5,
                500,
                "Requirements must contain 5 to 500 characters."
        );

        if (requirements == null) {
            cancel();
            return;
        }

        String jobId =
                jobRepository.generateNextJobId();

        Job job = new Job(
                jobId,
                loggedInCompany.getCompanyEmail(),
                loggedInCompany.getCompanyName(),
                jobTitle,
                location,
                employmentType,
                salary,
                description,
                requirements,
                "Published"
        );

        displayPreview(job);

        if (!confirmPublication(scanner)) {
            System.out.println(
                    "Job posting was not published."
            );
            return;
        }

        if (jobRepository.save(job)) {
            System.out.println(
                    "\nJob posting published successfully."
            );
            System.out.println(
                    "Job ID: " + job.getJobId()
            );
        } else {
            System.out.println(
                    "\nUnable to publish the job posting."
            );
        }
    }

    private String readEmploymentType(
            Scanner scanner) {

        while (true) {
            System.out.println("\nEmployment Type");
            System.out.println("1. Full-Time");
            System.out.println("2. Part-Time");
            System.out.println("3. Contract");
            System.out.println("4. Internship");
            System.out.println("0. Cancel");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    return "Full-Time";

                case "2":
                    return "Part-Time";

                case "3":
                    return "Contract";

                case "4":
                    return "Internship";

                case "0":
                    return null;

                default:
                    System.out.println(
                            "Invalid option. Please enter 0 to 4."
                    );
            }
        }
    }

    private String readSalary(Scanner scanner) {
        while (true) {
            System.out.print(
                    "Salary / Salary Range "
                            + "(example RM3000 or RM3000-RM5000): "
            );

            String salary = scanner.nextLine().trim();

            if (salary.equals("0")) {
                return null;
            }

            if (salary.isEmpty()) {
                System.out.println(
                        "Salary information cannot be empty."
                );
                continue;
            }

            if (salary.length() > 50) {
                System.out.println(
                        "Salary information must not exceed "
                                + "50 characters."
                );
                continue;
            }

            return salary;
        }
    }

    private String readRequiredText(
            Scanner scanner,
            String prompt,
            int minimumLength,
            int maximumLength,
            String errorMessage) {

        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            if (value.equals("0")) {
                return null;
            }

            if (value.length() < minimumLength
                    || value.length() > maximumLength) {

                System.out.println(errorMessage);
                continue;
            }

            return value;
        }
    }

    private void displayPreview(Job job) {
        System.out.println("\n========================================");
        System.out.println("           JOB POSTING PREVIEW");
        System.out.println("========================================");
        System.out.println("Job ID          : " + job.getJobId());
        System.out.println(
                "Company         : " + job.getCompanyName()
        );
        System.out.println(
                "Employer Email  : " + job.getEmployerEmail()
        );
        System.out.println(
                "Job Title       : " + job.getJobTitle()
        );
        System.out.println(
                "Location        : " + job.getLocation()
        );
        System.out.println(
                "Employment Type : " + job.getEmploymentType()
        );
        System.out.println(
                "Salary          : " + job.getSalary()
        );
        System.out.println(
                "Description     : " + job.getDescription()
        );
        System.out.println(
                "Requirements    : " + job.getRequirements()
        );
        System.out.println(
                "Status          : " + job.getStatus()
        );
        System.out.println("========================================");
    }

    private boolean confirmPublication(Scanner scanner) {
        while (true) {
            System.out.print(
                    "Publish this job posting? (Y/N): "
            );

            String input =
                    scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Y")) {
                return true;
            }

            if (input.equalsIgnoreCase("N")
                    || input.equals("0")) {

                return false;
            }

            System.out.println(
                    "Invalid input. Please enter Y or N."
            );
        }
    }

    private void cancel() {
        System.out.println(
                "Job posting creation cancelled."
        );
    }
}
