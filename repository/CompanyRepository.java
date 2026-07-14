package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import model.Company;

public class CompanyRepository {

    private static final String FILE_NAME = "data/companies.txt";

    public CompanyRepository() {

        try {

            File file = new File(FILE_NAME);

            if (!file.exists()) {

                file.getParentFile().mkdirs();
                file.createNewFile();

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    // Save Company
    public void save(Company company) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            bw.write(company.toString());
            bw.newLine();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    // Check Company Email Exists
    public boolean emailExists(String email) {

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                if (data[1].equalsIgnoreCase(email)) {

                    return true;

                }

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

        return false;

    }

    // Company Login (Future Use)
    public Company login(String email, String password) {

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                if (data[1].equalsIgnoreCase(email)
                        && data[2].equals(password)) {

                    return new Company(
                            data[0],
                            data[1],
                            data[2]);

                }

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

        return null;

    }

}