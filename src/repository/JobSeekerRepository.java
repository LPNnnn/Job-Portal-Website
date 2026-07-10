package repository;

import java.io.*;
import model.JobSeeker;

public class JobSeekerRepository {

    private final String FILE_NAME = "data/jobseekers.txt";

    public boolean emailExists(String email) {

        try {

            File file = new File(FILE_NAME);

            if (!file.exists()) {
                return false;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                if (data[1].equalsIgnoreCase(email)) {

                    br.close();
                    return true;

                }

            }

            br.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }

    public void save(JobSeeker user) {

        try {

            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(FILE_NAME, true));

            bw.write(user.getFullName() + ","
                    + user.getEmail() + ","
                    + user.getPassword());

            bw.newLine();

            bw.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}