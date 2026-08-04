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

    public List<Job> findAllPublished() {
        List<Job> publishedJobs = new ArrayList<>();

        for (Job job : jobs) {
            if (job.isPublished()) {
                publishedJobs.add(job);
            }
        }

        return publishedJobs;
    }

    public List<Job> searchPublishedJobs(
            String keyword,
            String location) {

        String normalisedKeyword =
                normalise(keyword);

        String normalisedLocation =
                normalise(location);

        List<Job> results = new ArrayList<>();

        for (Job job : jobs) {
            if (!job.isPublished()) {
                continue;
            }

            boolean keywordMatches =
                    normalisedKeyword.isEmpty()
                            || contains(job.getJobTitle(),
                            normalisedKeyword)
                            || contains(job.getCompanyName(),
                            normalisedKeyword)
                            || contains(job.getEmploymentType(),
                            normalisedKeyword)
                            || contains(job.getDescription(),
                            normalisedKeyword)
                            || contains(job.getRequirements(),
                            normalisedKeyword);

            boolean locationMatches =
                    normalisedLocation.isEmpty()
                            || contains(job.getLocation(),
                            normalisedLocation);

            if (keywordMatches && locationMatches) {
                results.add(job);
            }
        }

        return results;
    }

    public Optional<Job> findPublishedById(
            String jobId) {

        if (jobId == null || jobId.trim().isEmpty()) {
            return Optional.empty();
        }

        for (Job job : jobs) {
            if (job.isPublished()
                    && job.getJobId()
                    .equalsIgnoreCase(jobId.trim())) {

                return Optional.of(job);
            }
        }

        return Optional.empty();
    }

    private boolean contains(
            String source,
            String searchValue) {

        return normalise(source).contains(searchValue);
    }

    private String normalise(String value) {
        if (value == null) {
            return "";
        }

        return value.trim().toLowerCase();
    }
}
