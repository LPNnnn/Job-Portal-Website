package repository;

import model.JobSeeker;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobSeekerRepository {

    private static JobSeekerRepository instance;

    private final List<JobSeeker> jobSeekers;

    private static final String DATA_FILE =
            "data/jobseekers.txt";

    private JobSeekerRepository() {
        jobSeekers = new ArrayList<>();
        loadFromFile();
    }

    public static JobSeekerRepository getInstance() {
        if (instance == null) {
            instance = new JobSeekerRepository();
        }

        return instance;
    }

    // =====================================================
    // LOAD DATA
    // =====================================================

    private void loadFromFile() {
        jobSeekers.clear();

        List<String> lines =
                FileUtil.readAllLines(DATA_FILE);

        for (String line : lines) {
            if (line == null || line.trim().isEmpty()) {
                continue;
            }

            JobSeeker seeker =
                    JobSeeker.fromString(line);

            if (seeker != null) {
                jobSeekers.add(seeker);
            }
        }
    }

    // =====================================================
    // SAVE ALL DATA
    // =====================================================

    private void saveToFile() {
        List<String> lines = new ArrayList<>();

        for (JobSeeker seeker : jobSeekers) {
            if (seeker != null) {
                lines.add(seeker.toString());
            }
        }

        FileUtil.writeAllLines(DATA_FILE, lines);
    }

    // =====================================================
    // SAVE NEW OR EXISTING ACCOUNT
    // =====================================================

    public void save(JobSeeker seeker) {
        if (seeker == null
                || seeker.getEmail() == null
                || seeker.getEmail().trim().isEmpty()) {

            return;
        }

        Optional<JobSeeker> existing =
                findByEmail(seeker.getEmail());

        if (existing.isPresent()) {
            int index =
                    jobSeekers.indexOf(existing.get());

            if (index >= 0) {
                jobSeekers.set(index, seeker);
            }
        } else {
            jobSeekers.add(seeker);
        }

        saveToFile();
    }

    // =====================================================
    // UPDATE PROFILE FOR JOB-03
    // =====================================================

    public boolean updateProfile(
            String originalEmail,
            JobSeeker updatedProfile) {

        if (originalEmail == null
                || originalEmail.trim().isEmpty()
                || updatedProfile == null) {

            return false;
        }

        for (int i = 0; i < jobSeekers.size(); i++) {
            JobSeeker existing = jobSeekers.get(i);

            if (existing == null
                    || existing.getEmail() == null) {

                continue;
            }

            if (existing.getEmail()
                    .trim()
                    .equalsIgnoreCase(
                            originalEmail.trim())) {

                /*
                 * Protect the registered email.
                 */
                updatedProfile.setEmail(
                        existing.getEmail()
                );

                /*
                 * Protect the encrypted password.
                 */
                updatedProfile.setPassword(
                        existing.getPassword()
                );

                jobSeekers.set(i, updatedProfile);
                saveToFile();

                return true;
            }
        }

        return false;
    }

    // =====================================================
    // FIND BY EMAIL
    // =====================================================

    public Optional<JobSeeker> findByEmail(String email) {
        if (email == null
                || email.trim().isEmpty()) {

            return Optional.empty();
        }

        for (JobSeeker seeker : jobSeekers) {
            if (seeker != null
                    && seeker.getEmail() != null
                    && seeker.getEmail()
                    .trim()
                    .equalsIgnoreCase(email.trim())) {

                return Optional.of(seeker);
            }
        }

        return Optional.empty();
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    public List<JobSeeker> findAll() {
        return new ArrayList<>(jobSeekers);
    }

    public void reload() {
        loadFromFile();
    }
}