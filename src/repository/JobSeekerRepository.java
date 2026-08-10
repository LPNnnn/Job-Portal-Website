package repository;

import model.JobSeeker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobSeekerRepository {

    private static final String FILE_NAME = "data/jobseekers.txt";

    private final List<JobSeeker> jobSeekers = new ArrayList<>();

    public JobSeekerRepository() {
        createDataFile();
        reload();
    }

    private void createDataFile() {
        try {
            File file = new File(FILE_NAME);
            File parent = file.getParentFile();

            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException exception) {
            System.out.println(
                    "Unable to create job seeker data file: "
                            + exception.getMessage()
            );
        }
    }

    public void reload() {
        jobSeekers.clear();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {
                JobSeeker jobSeeker = JobSeeker.fromString(line);

                if (jobSeeker != null) {
                    jobSeekers.add(jobSeeker);
                }
            }
        } catch (IOException exception) {
            System.out.println(
                    "Unable to read job seeker data: "
                            + exception.getMessage()
            );
        }
    }

    public boolean save(JobSeeker jobSeeker) {
        if (jobSeeker == null
                || jobSeeker.getEmail() == null
                || jobSeeker.getEmail().trim().isEmpty()
                || emailExists(jobSeeker.getEmail())) {
            return false;
        }

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(FILE_NAME, true)
                     )) {

            writer.write(jobSeeker.toString());
            writer.newLine();
            jobSeekers.add(jobSeeker);
            return true;
        } catch (IOException exception) {
            System.out.println(
                    "Unable to save job seeker data: "
                            + exception.getMessage()
            );
            return false;
        }
    }

    public Optional<JobSeeker> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }

        for (JobSeeker jobSeeker : jobSeekers) {
            if (jobSeeker.getEmail().equalsIgnoreCase(email.trim())) {
                return Optional.of(jobSeeker);
            }
        }

        return Optional.empty();
    }

    public boolean emailExists(String email) {
        return findByEmail(email).isPresent();
    }
}
