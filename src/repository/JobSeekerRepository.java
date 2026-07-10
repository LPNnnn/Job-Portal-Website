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

    // save() method is DELETED for this commit

}