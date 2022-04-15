package tech.getarrays.api.employee.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.getarrays.api.employee.bean.Employee;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    Optional<Employee> findEmployeeById(Long id);
}
