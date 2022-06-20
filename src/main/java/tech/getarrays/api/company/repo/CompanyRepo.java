package tech.getarrays.api.company.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.getarrays.api.company.bean.Company;

public interface CompanyRepo extends JpaRepository<Company, Long> {
}
