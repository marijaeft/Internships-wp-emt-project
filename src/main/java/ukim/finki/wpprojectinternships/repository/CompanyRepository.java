package ukim.finki.wpprojectinternships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ukim.finki.wpprojectinternships.model.Company;

public interface CompanyRepository extends JpaSpecificationRepository<Company,Long> {
 Company findCompanyByEmail(String email);
}
