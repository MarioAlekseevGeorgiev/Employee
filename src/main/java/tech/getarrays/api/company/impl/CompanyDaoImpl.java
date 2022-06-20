package tech.getarrays.api.company.impl;

import dao.DaoUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tech.getarrays.api.company.bean.Company;
import tech.getarrays.api.company.repo.CompanyRepo;
import tech.getarrays.api.company.service.CompanyDao;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CompanyDaoImpl implements CompanyDao {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final CompanyRepo companyRepo;

    private NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    public CompanyDaoImpl(CompanyRepo companyRepo) {
        this.companyRepo = companyRepo;
    }

    @Autowired
    public void setDataSource(DataSource dataSource)
    {
        namedTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Company addCompany(Company company) {
        return companyRepo.save(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        String sql =
                " SELECT id                         " +
                "      , company                    " +
                "      , email                      " +
                "      , address                    " +
                "      , phone                      " +
                "      , vat_number                 " +
                "  FROM company                     ";


        DaoUtils.debugQuery(logger, sql, new MapSqlParameterSource());

        return namedTemplate.query(sql, new MapSqlParameterSource(), (resultSet, i) -> {

            Company res = new Company();
            res.setId(resultSet.getLong("id"));
            res.setCompany(resultSet.getString("company"));
            res.setEmail(resultSet.getString("email"));

            res.setAddress(resultSet.getString("address"));
            res.setPhone(resultSet.getString("name"));
            res.setPhone(resultSet.getString("phone"));
            res.setVatNumber(resultSet.getString("vat_number"));
            return res;
        });
    }
}
