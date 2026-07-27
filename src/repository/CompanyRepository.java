package repository;

import model.Company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CompanyRepository {

    private static final String FILE_NAME = "data/companies.txt";

    private final List<Company> companies = new ArrayList<>();

    public CompanyRepository() {
        createDataFile();
        reload();
    public CompanyRepository() {
        createDataFile();
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
                    "Unable to create company data file: "
                            + exception.getMessage()
            );
        }
    }

    public void reload() {
        companies.clear();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {
                Company company = Company.fromString(line);

                if (company != null) {
                    companies.add(company);
                }
            }
        } catch (IOException exception) {
            System.out.println(
                    "Unable to read company data: "
                            + exception.getMessage()
            );
        }
    }

    public boolean save(Company company) {
        if (company == null
                || company.getCompanyEmail() == null
                || company.getCompanyEmail().trim().isEmpty()
                || emailExists(company.getCompanyEmail())) {
    public boolean save(Company company) {
        if (company == null) {
            return false;
        }

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(FILE_NAME, true)
                     )) {

            writer.write(company.toString());
            writer.newLine();
            companies.add(company);
            return true;
        } catch (IOException exception) {
            System.out.println(
                    "Unable to save company data: "
                            + exception.getMessage()
            );
            return false;
        }
    }

    public Optional<Company> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }

        for (Company company : companies) {
            if (company.getCompanyEmail()
                    .equalsIgnoreCase(email.trim())) {
                return Optional.of(company);
            }
        }

        return Optional.empty();
    }

    public boolean emailExists(String email) {
        return findByEmail(email).isPresent();
    }
}
    public boolean emailExists(String email) {
        java.io.File file = new java.io.File(FILE_NAME);

        try (java.io.BufferedReader reader =
                     new java.io.BufferedReader(
                             new java.io.FileReader(file)
                     )) {

            String line;

            while ((line = reader.readLine()) != null) {
                Company company = Company.fromString(line);

                if (company != null
                        && company.getCompanyEmail()
                        .equalsIgnoreCase(email.trim())) {
                    return true;
                }
            }
        } catch (IOException exception) {
            System.out.println(
                    "Unable to read company data: "
                            + exception.getMessage()
            );
        }

        return false;
    }
}
// Re-committed on 16/7/2026 
// Re-committed on 17/7/2026 
