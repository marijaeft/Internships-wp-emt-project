package ukim.finki.wpprojectinternships.service;

import org.springframework.web.multipart.MultipartFile;
import ukim.finki.wpprojectinternships.model.Application;

import java.util.List;

public interface ApplicationService {
    //find students id and list the oglasite that the student applied
    //create (advertisement.numAplicants++)
    //update za statusot ako sakame da go smenime
    //find oglas id and list students that applied
    //find by id
    List<Application> findAllApplicationsThatTheStudentAppliedTo(Long studentId);
    Application createApplication(Long studentId, Long advertisementId, MultipartFile CV);
    Application updateApplication(Long id,Long studentId, Long advertisementId);
    List<Application> findAllApplicationsToACertainAdvertisement(Long advertisementId);
    Application findApplicationById(Long id);
}
