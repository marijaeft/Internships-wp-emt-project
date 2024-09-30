package ukim.finki.wpprojectinternships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ukim.finki.wpprojectinternships.model.Advertisement;

import java.util.List;

public interface AdvertisementRepository extends JpaSpecificationRepository<Advertisement,Long> {

    List<Advertisement> findAllByOrderByActiveDesc();
    List<Advertisement> findAllByCompanyId(Long companyId);
}
