package repository;

import model.Company;
import util.FileUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyRepository {
    private static CompanyRepository instance;
    private List<Company> companies;
    private final String DATA_FILE = "data/companies.txt";
    
    private CompanyRepository() {
        this.companies = new ArrayList<>();
        loadFromFile();
    }
    
    public static CompanyRepository getInstance() {
        if (instance == null) {
            instance = new CompanyRepository();
        }
        return instance;
    }
    
    public void reload() {
        loadFromFile();
    }

    private void loadFromFile() {
        companies.clear();
        List<String> lines = FileUtil.readAllLines(DATA_FILE);
        for (String line : lines) {
            Company company = Company.fromString(line);
            if (company != null) {
                companies.add(company);
            }
        }
    }
    
    private void saveToFile() {
        List<String> lines = new ArrayList<>();
        for (Company company : companies) {
            lines.add(company.toString());
        }
        FileUtil.writeAllLines(DATA_FILE, lines);
    }
    
    public void save(Company company) {
        if (company == null) return;
        
        Optional<Company> existing = findByEmail(company.getCompanyEmail());
        if (existing.isPresent()) {
            int index = companies.indexOf(existing.get());
            companies.set(index, company);
        } else {
            companies.add(company);
        }
        saveToFile();
    }
    
    public Optional<Company> findByEmail(String email) {
        if (email == null) return Optional.empty();
        for (Company company : companies) {
            if (company.getCompanyEmail() != null && company.getCompanyEmail().equalsIgnoreCase(email)) {
                return Optional.of(company);
            }
        }
        return Optional.empty();
    }
    
    public List<Company> findAll() {
        return new ArrayList<>(companies);
    }
    
    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }
}