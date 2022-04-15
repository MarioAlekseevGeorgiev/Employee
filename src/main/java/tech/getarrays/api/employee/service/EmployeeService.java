package tech.getarrays.api.employee.service;

import antlr.StringUtils;
import dao.DaoUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import tech.getarrays.api.employee.repo.EmployeeRepo;
import tech.getarrays.api.employee.bean.Employee;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private final Log logger = LogFactory.getLog(this.getClass());

    private final EmployeeRepo employeeRepo;

    private NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource)
    {
        namedTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Employee> getAllEmployees(Long userId) {
        String sql =
                        "   SELECT id                               " +
                        "        , email                            " +
                        "        , employee_code                    " +
                        "        , image_url                        " +
                        "        , job_title                        " +
                        "        , name                             " +
                        "        , phone                            " +
                        "        , user_id                          " +
                        "     FROM employee                         " +
                          getUserId(userId);

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("userId", userId);

        DaoUtils.debugQuery(logger, sql, sqlParameterSource);

        return namedTemplate.query(sql, sqlParameterSource, (resultSet, i) -> {

            Employee res = new Employee();
            res.setId(resultSet.getLong("id"));
            res.setEmail(resultSet.getString("email"));
            res.setEmployeeCode(resultSet.getString("employee_code"));
            res.setImageUrl(resultSet.getString("image_url"));
            res.setJobTitle(resultSet.getString("job_title"));
            res.setName(resultSet.getString("name"));
            res.setPhone(resultSet.getString("phone"));
            res.setUserId(resultSet.getLong("user_id"));
            return res;
        });
    }

    private String getUserId(Long userId) {
        return userId != null ? "WHERE user_id = :userId    " : "";
    }

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Employee addEmployee(Employee employee) {
        employee.setEmployeeCode(UUID.randomUUID().toString());
        return employeeRepo.save(employee);
    }

    public List<Employee> findAllEmployee(Long userId) {
        return getAllEmployees(userId);
      //  return employeeRepo.findAll();
    }

    public Employee updateEmployee(Employee employee) {
        Employee oldEmployee = findEmployeeById(employee.getId());
        employee.setEmployeeCode(oldEmployee.getEmployeeCode());
        return employeeRepo.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
    }

    public Employee findEmployeeById(Long id) {
        String sql =
                        "    SELECT id                                                " +
                        "         , email                                             " +
                        "         , employee_code                                     " +
                        "         , image_url                                         " +
                        "         , job_title                                         " +
                        "         , name                                              " +
                        "         , phone                                             " +
                        "         , user_id                                           " +
                        "      FROM employee                                          " +
                        "     WHERE id = :id                                          ";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", id);

        DaoUtils.debugQuery(logger, sql, sqlParameterSource);

        try {
            return namedTemplate.queryForObject(sql, sqlParameterSource, BeanPropertyRowMapper.newInstance(Employee.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    private String findEmployeesByUserIdSQL(String name, String email, String phone, String jobTitle) {
        return
                "    SELECT id                                                " +
                "         , email                                             " +
                "         , employee_code                                     " +
                "         , image_url                                         " +
                "         , job_title                                         " +
                "         , name                                              " +
                "         , phone                                             " +
                "         , user_id                                           " +
                "      FROM employee                                          " +
                "     WHERE user_id = :id                                     " +
                        addSqlWhereClause( name,  email,  phone,  jobTitle);
    }

    private void addSqlParameters(MapSqlParameterSource sqlParameterSource, String name, String email, String phone, String jobTitle) {
        if (DaoUtils.isNotBlank(name)) {
            sqlParameterSource.addValue("name", name);
        }
        if (DaoUtils.isNotBlank(email)) {
            sqlParameterSource.addValue("email", email);
        }
        if (DaoUtils.isNotBlank(name)) {
            sqlParameterSource.addValue("phone", phone);
        }
        if (DaoUtils.isNotBlank(name)) {
            sqlParameterSource.addValue("jobTitle", jobTitle);
        }
    }

    private String addSqlWhereClause(String name, String email, String phone, String jobTitle) {
        StringBuilder sql = new StringBuilder();
        if (DaoUtils.isNotBlank(name)) {
            sql.append(" AND name = :name ");
        }
        if (DaoUtils.isNotBlank(email)) {
            sql.append(" AND email = :email ");
        }
        if (DaoUtils.isNotBlank(phone)) {
            sql.append(" AND phone = :phone ");
        }
        if (DaoUtils.isNotBlank(jobTitle)) {
            sql.append(" AND jobTitle = :jobTitle ");
        }

        return sql.toString();
    }
    public Paginated<Employee> findEmployeesByUserId(Long id, String name, String email, String phone, String jobTitle, int pageSize, int pageNumber) {

        DaoUtils.PagingIndex pi = DaoUtils.pagingIdxForSlice(pageSize, pageNumber);

        String sql = ""
                + "   SELECT *  FROM (                                                                "
                + "         SELECT foo.*                                                              "
                + "        FROM (SELECT boo.*, COUNT(1) OVER() out_of, ROW_NUMBER() OVER() row_num    "
                + "               FROM (                                                              "
                + findEmployeesByUserIdSQL(name, email, phone, jobTitle)
                + "   ) boo WHERE 1 = 1) foo                                                          "
                + "           WHERE 1=1                                                               "
                + "         AND foo.row_num <= :endIdx) goo                                           "
                + "   WHERE 1 = 1                                                                     "
                + "     AND goo.row_num >= :startIdx                                                  ";

        final int[] totalResults = {0};

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("startIdx", pi.getStartIdx())
                .addValue("endIdx", pi.getEndIdx());
        addSqlParameters(sqlParameterSource, name, email, phone, jobTitle);

        DaoUtils.debugQuery(logger, sql, sqlParameterSource);

        List<Employee> result = namedTemplate.query(sql, sqlParameterSource, (resultSet, i) -> {

            Employee res = new Employee();
            res.setId(resultSet.getLong("id"));
            res.setEmail(resultSet.getString("email"));
            res.setEmployeeCode(resultSet.getString("employee_code"));
            res.setImageUrl(resultSet.getString("image_url"));
            res.setJobTitle(resultSet.getString("job_title"));
            res.setName(resultSet.getString("name"));
            res.setPhone(resultSet.getString("phone"));
            res.setUserId(resultSet.getLong("user_id"));
            totalResults[0] = resultSet.getInt("out_of");

            return res;
        });

        Paginated<Employee> paginated = new Paginated<>();
        paginated.setResult(result);
        paginated.setPageNumber(pageNumber);
        paginated.setPageSize(pageSize);
        paginated.setTotalResults(totalResults[0]);
        return paginated;
//
//        return Paginated.builder()
//                .result(result)
//                .pageNumber(pageNumber)
//                .pageSize(pageSize)
//                .totalResults(totalResults[0]).build();
    }

}
