package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import model.Job;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JobPostingTest {

    private static final Path JOB_FILE =
            Path.of("data", "jobs.txt");

    private JobRepository jobRepository;
    private String originalFileContent;

    @Before
    public void setUp() throws IOException {
        /*
         * The original job data is saved before each test.
         * A controlled record is inserted so that the next Job ID
         * can be predicted.
         */
        Files.createDirectories(JOB_FILE.getParent());

        if (Files.exists(JOB_FILE)) {
            originalFileContent =
                    Files.readString(JOB_FILE);
        } else {
            originalFileContent = "";
        }

        String testData =
                "JOB900|existing@gmail.com|Existing Company|"
                + "Existing Job|Selangor|Full-Time|RM3000|"
                + "Existing job description.|"
                + "Existing job requirements.|Published";

        Files.writeString(JOB_FILE, testData);

        jobRepository = JobRepository.getInstance();
        jobRepository.reload();
    }

    @After
    public void tearDown() throws IOException {
        Files.writeString(
                JOB_FILE,
                originalFileContent
        );

        jobRepository.reload();
    }

    @Test
    public void shouldSaveJobPostingWithPublishedStatus() {
        /*
         * Expected result:
         * The next Job ID should be JOB901 and the confirmed job
         * should be saved with Published status.
         */
        String generatedJobId =
                jobRepository.generateNextJobId();

        assertEquals(
                "The next generated Job ID should be JOB901.",
                "JOB901",
                generatedJobId
        );

        Job publishedJob = new Job(
                generatedJobId,
                "hr@techvision.com",
                "TechVision Solutions",
                "Junior Software Developer",
                "Kuala Lumpur",
                "Full-Time",
                "RM3000-RM4000",
                "Develop and maintain software applications.",
                "Diploma or degree in Information Technology.",
                "Published"
        );

        boolean saved =
                jobRepository.save(publishedJob);

        assertTrue(
                "The confirmed job should be saved.",
                saved
        );

        Optional<Job> savedRecord =
                jobRepository.findById(
                        generatedJobId
                );

        assertTrue(
                "The saved job should be found.",
                savedRecord.isPresent()
        );

        assertEquals(
                "Published",
                savedRecord.get().getStatus()
        );

        assertEquals(
                "hr@techvision.com",
                savedRecord.get().getEmployerEmail()
        );

        assertEquals(
                "TechVision Solutions",
                savedRecord.get().getCompanyName()
        );
    }
}
