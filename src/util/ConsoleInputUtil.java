package util;

import java.io.Console;
import java.util.Scanner;

public final class ConsoleInputUtil {

    private ConsoleInputUtil() {
    }

    /**
     * Reads a password without displaying the typed characters when the
     * application is running in a normal system terminal.
     *
     * VS Code's Debug Console may not provide a Java Console. In that case,
     * input falls back to Scanner so the program can still run. Launch the
     * program in the integrated terminal or Command Prompt for hidden input.
     */
    public static String readPassword(
            Scanner scanner,
            String prompt) {

        Console console = System.console();

        if (console != null) {
            char[] passwordCharacters =
                    console.readPassword("%s", prompt);

            if (passwordCharacters == null) {
                return null;
            }

            String password = new String(passwordCharacters);

            java.util.Arrays.fill(
                    passwordCharacters,
                    '\0'
            );

            return password;
        }

        System.out.print(prompt);
        return scanner.nextLine();
    }
}
