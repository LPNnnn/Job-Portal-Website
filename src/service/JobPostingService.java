package service;

import model.Company;
import model.Job;
import repository.JobRepository;

import java.util.Scanner;

public class JobPostingService {



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

  
}
