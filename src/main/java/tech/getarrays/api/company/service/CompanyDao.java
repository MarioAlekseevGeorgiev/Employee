package tech.getarrays.api.company.service;

import org.springframework.http.ResponseEntity;
import tech.getarrays.api.company.bean.Company;

import java.util.List;

public interface CompanyDao {

    Company addCompany(Company company);

    List<Company> getAllCompanies();
}
