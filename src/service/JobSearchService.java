package service;

import model.Job;
import repository.JobRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class JobSearchService {

    private static JobSearchService instance;

    private final JobRepository jobRepository;

    private JobSearchService() {
        jobRepository = JobRepository.getInstance();
    }

    public static JobSearchService getInstance() {
        if (instance == null) {
            instance = new JobSearchService();
        }

        return instance;
    }

    public void showSearchMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("\n========================================");
            System.out.println("              SEARCH JOBS");
            System.out.println("========================================");
            System.out.println("1. Search by Keyword and Location");
            System.out.println("2. View All Available Jobs");
            System.out.println("3. View Job Details");
            System.out.println("0. Back to Job Seeker Menu");
            System.out.println("========================================");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    searchJobs(scanner);
                    break;

                case "2":
                    displayAllPublishedJobs();
                    break;

                case "3":
                    viewJobDetails(scanner);
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please enter 0 to 3."
                    );
            }
        }
    }

    private void searchJobs(Scanner scanner) {
        System.out.println("\n========================================");
        System.out.println("          SEARCH AVAILABLE JOBS");
        System.out.println("========================================");
        System.out.println(
                "Leave one field empty when you only want "
                        + "to use the other field."
        );
        System.out.println("Enter 0 to cancel.");

        String keyword;

        while (true) {
            System.out.print(
                    "Keyword "
                            + "(job title, company, or interest): "
            );

            keyword = scanner.nextLine().trim();

            if (keyword.equals("0")) {
                System.out.println("Job search cancelled.");
                return;
            }

            if (keyword.length() > 100) {
                System.out.println(
                        "Keyword must not exceed 100 characters."
                );
                continue;
            }

            break;
        }

        String location;

        while (true) {
            System.out.print("Location: ");
            location = scanner.nextLine().trim();

            if (location.equals("0")) {
                System.out.println("Job search cancelled.");
                return;
            }

            if (location.length() > 100) {
                System.out.println(
                        "Location must not exceed 100 characters."
                );
                continue;
            }

            break;
        }

        if (keyword.isEmpty() && location.isEmpty()) {
            System.out.println(
                    "Please enter at least a keyword or location."
            );
            return;
        }

        jobRepository.reload();

        List<Job> results =
                jobRepository.searchPublishedJobs(
                        keyword,
                        location
                );

        displaySearchResults(results);
    }

    private void displayAllPublishedJobs() {
        jobRepository.reload();

        List<Job> jobs =
                jobRepository.findAllPublished();

        System.out.println("\n========================================");
        System.out.println("          ALL AVAILABLE JOBS");
        System.out.println("========================================");

        displayJobList(jobs);
    }

    private void displaySearchResults(
            List<Job> results) {

        System.out.println("\n========================================");
        System.out.println("            SEARCH RESULTS");
        System.out.println("========================================");

        displayJobList(results);
    }

    private void displayJobList(List<Job> jobs) {
        if (jobs == null || jobs.isEmpty()) {
            System.out.println(
                    "No matching published jobs were found."
            );
            return;
        }

        int number = 1;

        for (Job job : jobs) {
            System.out.println("Result No.      : " + number);
            System.out.println(
                    "Job ID          : " + job.getJobId()
            );
            System.out.println(
                    "Job Title       : " + job.getJobTitle()
            );
            System.out.println(
                    "Company         : " + job.getCompanyName()
            );
            System.out.println(
                    "Location        : " + job.getLocation()
            );
            System.out.println(
                    "Employment Type : "
                            + job.getEmploymentType()
            );
            System.out.println(
                    "Salary          : " + job.getSalary()
            );
            System.out.println(
                    "----------------------------------------"
            );

            number++;
        }

        System.out.println(
                "Total Matching Jobs: " + jobs.size()
        );
        System.out.println(
                "Use View Job Details and enter a Job ID "
                        + "to see the full posting."
        );
    }

    private void viewJobDetails(Scanner scanner) {
        jobRepository.reload();

        System.out.print(
                "Enter Job ID or 0 to cancel: "
        );

        String jobId = scanner.nextLine().trim();

        if (jobId.equals("0")) {
            System.out.println(
                    "View job details cancelled."
            );
            return;
        }

        if (jobId.isEmpty()) {
            System.out.println(
                    "Job ID cannot be empty."
            );
            return;
        }

        Optional<Job> jobRecord =
                jobRepository.findPublishedById(jobId);

        if (jobRecord.isEmpty()) {
            System.out.println(
                    "Published job was not found."
            );
            return;
        }

        displayJobDetails(jobRecord.get());
    }

    private void displayJobDetails(Job job) {
        System.out.println("\n========================================");
        System.out.println("              JOB DETAILS");
        System.out.println("========================================");
        System.out.println("Job ID          : " + job.getJobId());
        System.out.println(
                "Job Title       : " + job.getJobTitle()
        );
        System.out.println(
                "Company         : " + job.getCompanyName()
        );
        System.out.println(
                "Location        : " + job.getLocation()
        );
        System.out.println(
                "Employment Type : "
                        + job.getEmploymentType()
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
}
