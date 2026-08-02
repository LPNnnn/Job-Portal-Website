package service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegisteredJobSeekerServiceTest {

    private RegisteredJobSeekerService service;
    private PrintStream originalOutput;
    private ByteArrayOutputStream capturedOutput;

    @Before
    public void setUp() {
        /*
         * A fresh output stream is created before every test so that
         * output from one test does not affect another test.
         */
        service = RegisteredJobSeekerService.getInstance();

        originalOutput = System.out;
        capturedOutput = new ByteArrayOutputStream();

        System.setOut(
                new PrintStream(capturedOutput)
        );
    }

    @After
    public void tearDown() {
        System.setOut(originalOutput);
    }

    @Test
    public void shouldDisplayRegisteredJobSeekersWithoutPasswords() {
        /*
         * Expected result:
         * The registered job seeker heading should be displayed.
         * Password information should not be displayed.
         */

        service.viewAllRegisteredJobSeekers();

        String actualOutput =
                capturedOutput.toString();

        assertTrue(
                "The registered job seeker heading should be displayed.",
                actualOutput.contains(
                        "REGISTERED JOB SEEKERS"
                )
        );

        assertFalse(
                "The password label must not be displayed.",
                actualOutput.toLowerCase()
                        .contains("password")
        );

        assertFalse(
                "The encrypted password must not be displayed.",
                actualOutput.contains(
                        "UvwfgpvB345"
                )
        );
    }
}
