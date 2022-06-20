package tech.getarrays.api.company.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.getarrays.api.company.bean.Company;
import tech.getarrays.api.company.service.CompanyDao;
import tech.getarrays.api.company.service.CompanyService;

import java.util.List;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

    private final CompanyDao companyDao;

    @Autowired
    public CompanyServiceImpl(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Override
    public ResponseEntity<?> addCompany(Company company) {
        Company newCompany = companyDao.addCompany(company);
        return new ResponseEntity<>(newCompany, HttpStatus.CREATED);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyDao.getAllCompanies();
    }
}
