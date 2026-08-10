package repository;

import model.Job;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobRepository {

    private static JobRepository instance;

    private static final String DATA_FILE =
            "data/jobs.txt";

    private final List<Job> jobs;

    private JobRepository() {
        jobs = new ArrayList<>();
        reload();
    }

    public static JobRepository getInstance() {
        if (instance == null) {
            instance = new JobRepository();
        }

        return instance;
    }

    public void reload() {
        jobs.clear();

        List<String> lines =
                FileUtil.readAllLines(DATA_FILE);

        for (String line : lines) {
            Job job = Job.fromString(line);

            if (job != null) {
                jobs.add(job);
            }
        }
    }

    public boolean save(Job job) {
        if (job == null
                || job.getJobId() == null
                || job.getJobId().trim().isEmpty()
                || findById(job.getJobId()).isPresent()) {

            return false;
        }

        jobs.add(job);
        saveToFile();
        return true;
    }

    public Optional<Job> findById(String jobId) {
        if (jobId == null || jobId.trim().isEmpty()) {
            return Optional.empty();
        }

        for (Job job : jobs) {
            if (job.getJobId()
                    .equalsIgnoreCase(jobId.trim())) {

                return Optional.of(job);
            }
        }

        return Optional.empty();
    }

    public List<Job> findAll() {
        return new ArrayList<>(jobs);
    }

    public String generateNextJobId() {
        int maximumNumber = 0;

        for (Job job : jobs) {
            String jobId = job.getJobId();

            if (jobId == null
                    || !jobId.matches("JOB\\d+")) {
                continue;
            }

            try {
                int number = Integer.parseInt(
                        jobId.substring(3)
                );

                if (number > maximumNumber) {
                    maximumNumber = number;
                }
            } catch (NumberFormatException exception) {
                // Ignore malformed IDs.
            }
        }

        return String.format(
                "JOB%03d",
                maximumNumber + 1
        );
    }

    private void saveToFile() {
        List<String> lines = new ArrayList<>();

        for (Job job : jobs) {
            lines.add(job.toString());
        }

        FileUtil.writeAllLines(DATA_FILE, lines);
    }
}
