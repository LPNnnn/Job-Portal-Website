package repository;

import model.Company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CompanyRepository {

    private static final String FILE_NAME = "data/companies.txt";

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
            return true;
        } catch (IOException exception) {
            System.out.println(
                    "Unable to save company data: "
                            + exception.getMessage()
            );
            return false;
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
