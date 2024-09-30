package ukim.finki.wpprojectinternships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ukim.finki.wpprojectinternships.model.Advertisement;
import ukim.finki.wpprojectinternships.model.Application;
import ukim.finki.wpprojectinternships.model.Student;

import java.util.List;

public interface ApplicationRepository extends JpaSpecificationRepository<Application,Long> {
   List<Application> findApplicationsByStudent(Student student);
   List<Application> findApplicationByAdvertisement(Advertisement advertisement);
}
