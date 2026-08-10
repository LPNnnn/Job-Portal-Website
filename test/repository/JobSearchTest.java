package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import model.Job;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JobSearchTest {

    private static final Path JOB_FILE =
            Path.of("data", "jobs.txt");

    private JobRepository jobRepository;
    private String originalFileContent;

    @Before
    public void setUp() throws IOException {
        /*
         * The original job data is saved before each test.
         * Controlled data is used so that the test result is predictable.
         */
        Files.createDirectories(JOB_FILE.getParent());

        if (Files.exists(JOB_FILE)) {
            originalFileContent =
                    Files.readString(JOB_FILE);
        } else {
            originalFileContent = "";
        }

        String testData =
                "JOB901|hr@techvision.com|TechVision Solutions|"
                + "Junior Java Developer|Kuala Lumpur|Full-Time|"
                + "RM3000-RM4200|Develop Java applications.|"
                + "Java programming knowledge required.|Published"
                + System.lineSeparator()
                + "JOB902|hr@sample.com|Sample Company|"
                + "Sales Assistant|Johor Bahru|Full-Time|"
                + "RM2200-RM2800|Assist customers in the store.|"
                + "Good communication skills.|Published";

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
    public void shouldFindPublishedJobByKeywordAndLocation() {
        /*
         * Expected result:
         * Exactly one published Java job located in Kuala Lumpur
         * should be returned.
         */
        List<Job> results =
                jobRepository.searchPublishedJobs(
                        "Java",
                        "Kuala Lumpur"
                );

        assertFalse(
                "A matching job should be returned.",
                results.isEmpty()
        );

        assertEquals(
                "Only one job should match both conditions.",
                1,
                results.size()
        );

        Job result = results.get(0);

        assertEquals(
                "JOB901",
                result.getJobId()
        );

        assertTrue(
                "The returned job must be published.",
                result.isPublished()
        );

        assertTrue(
                "The job title should contain Java.",
                result.getJobTitle()
                        .toLowerCase()
                        .contains("java")
        );

        assertEquals(
                "Kuala Lumpur",
                result.getLocation()
        );
    }
}
