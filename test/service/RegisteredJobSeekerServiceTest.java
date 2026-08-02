package service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisteredJobSeekerServiceTest {

    private PrintStream originalOutput;
    private ByteArrayOutputStream capturedOutput;

    @BeforeEach
    void setUp() {
        originalOutput = System.out;
        capturedOutput = new ByteArrayOutputStream();

        System.setOut(new PrintStream(capturedOutput));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOutput);
    }

    @Test
    void shouldDisplayJobSeekersWithoutPasswords() {
        RegisteredJobSeekerService service =
                RegisteredJobSeekerService.getInstance();

        service.viewAllRegisteredJobSeekers();

        String output = capturedOutput.toString();

        assertTrue(
                output.contains("REGISTERED JOB SEEKERS"),
                "The registered job seeker heading should be displayed."
        );

        assertFalse(
                output.toLowerCase().contains("password"),
                "The password label must not be displayed."
        );

        assertFalse(
                output.contains("UvwfgpvB345"),
                "The encrypted password must not be displayed."
        );
    }
}