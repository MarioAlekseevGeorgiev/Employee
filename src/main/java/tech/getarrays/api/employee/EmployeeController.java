package tech.getarrays.api.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.getarrays.api.employee.bean.Employee;
import tech.getarrays.api.employee.service.EmployeeService;
import tech.getarrays.api.employee.service.Paginated;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all/{companyId}")
    public ResponseEntity<List<Employee>> getAllEmployees(@PathVariable("companyId") Long companyId) {
        List<Employee> employees = employeeService.findAllEmployee(companyId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee newEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        Employee updateEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(name = "searchTerm") String searchTerm
                                                        , @RequestParam(name = "companyId") Long companyId) {
        List<Employee> employeesAll = employeeService.findAllEmployee(companyId);
        employeesAll = employeesAll
                .stream()
                .filter(e -> e.getName().contains(searchTerm))
                .collect(collectingAndThen(toList(),
                        Collections::unmodifiableList));

        return new ResponseEntity<>(employeesAll, HttpStatus.OK);
    }

    @GetMapping("/searchPageable")
    public Paginated<Employee> getAllEmployeesPageable(@RequestParam(name = "userId") Long userId
                                                     , @RequestParam(name = "name", required = false) String name
                                                     , @RequestParam(name = "email", required = false) String email
                                                     , @RequestParam(name = "phone", required = false) String phone
                                                     , @RequestParam(name = "jobTitle", required = false) String jobTitle
                                                     , @RequestParam(name = "pageSize")   int pageSize
                                                     , @RequestParam(name = "pageNumber") int pageNumber) {
            return employeeService.findEmployeesByUserId(userId, name, email, phone, jobTitle, pageSize, pageNumber);
    }
}
