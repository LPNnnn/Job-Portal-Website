package repository;

import model.Administrator;
import util.PasswordUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdministratorRepository {

    private static final String FILE_NAME =
            "data/administrators.txt";

    private final List<Administrator> administrators =
            new ArrayList<>();

    public AdministratorRepository() {
        createDataFile();
        createDefaultAdministrator();
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
                    "Unable to create administrator data file: "
                            + exception.getMessage()
            );
        }
    }

    private void createDefaultAdministrator() {
        File file = new File(FILE_NAME);

        if (file.length() > 0) {
            return;
        }

        Administrator defaultAdministrator =
                new Administrator(
                        "System Administrator",
                        "admin",
                        PasswordUtil.encryptPassword("Admin@123")
                );

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(FILE_NAME, true)
                     )) {

            writer.write(defaultAdministrator.toString());
            writer.newLine();
        } catch (IOException exception) {
            System.out.println(
                    "Unable to create default administrator: "
                            + exception.getMessage()
            );
        }
    }

    public void reload() {
        administrators.clear();

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(FILE_NAME)
                     )) {

            String line;

            while ((line = reader.readLine()) != null) {
                Administrator administrator =
                        Administrator.fromString(line);

                if (administrator != null) {
                    administrators.add(administrator);
                }
            }
        } catch (IOException exception) {
            System.out.println(
                    "Unable to read administrator data: "
                            + exception.getMessage()
            );
        }
    }

    public Optional<Administrator> findByUsername(
            String username) {

        if (username == null || username.trim().isEmpty()) {
            return Optional.empty();
        }

        for (Administrator administrator : administrators) {
            if (administrator.getUsername()
                    .equalsIgnoreCase(username.trim())) {

                return Optional.of(administrator);
            }
        }

        return Optional.empty();
    }
}
// Re-committed on 16/7/2026 
