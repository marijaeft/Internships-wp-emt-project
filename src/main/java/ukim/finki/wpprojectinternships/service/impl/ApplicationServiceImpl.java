package ukim.finki.wpprojectinternships.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ukim.finki.wpprojectinternships.model.Advertisement;
import ukim.finki.wpprojectinternships.model.Application;
import ukim.finki.wpprojectinternships.model.ApplicationStatus;
import ukim.finki.wpprojectinternships.model.Student;
import ukim.finki.wpprojectinternships.model.exceptions.InvalidApplicationIdException;
import ukim.finki.wpprojectinternships.repository.ApplicationRepository;
import ukim.finki.wpprojectinternships.service.AdvertisementService;
import ukim.finki.wpprojectinternships.service.ApplicationService;
import ukim.finki.wpprojectinternships.service.StudentService;

import java.io.IOException;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final StudentService studentService;
    private final AdvertisementService advertisementService;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, StudentService studentService, AdvertisementService advertisementService) {
        this.applicationRepository = applicationRepository;
        this.studentService = studentService;
        this.advertisementService = advertisementService;
    }

    @Override
    public List<Application> findAllApplicationsThatTheStudentAppliedTo(Long studentId) {
        Student student = studentService.findStudentById(studentId);
        return this.applicationRepository.findApplicationsByStudent(student);
    }

    @Override
    public Application createApplication(Long studentId, Long advertisementId, MultipartFile CV) {
        Advertisement advertisement = this.advertisementService.findAdvertisementById(advertisementId);
        advertisement.setNumberOfApplicants(advertisement.getNumberOfApplicants()+1);
        Student student = studentService.findStudentById(studentId);
        byte[] cv = new byte[0];
        if(CV!=null) {
            try {
                cv = CV.getBytes();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Application application1 = new Application(student, advertisement, cv);
        application1.setStatus(ApplicationStatus.SENT);
        return this.applicationRepository.save(application1);
    }

    @Override
    public Application updateApplication(Long id, Long studentId, Long advertisementId) {
        Application application1 = this.findApplicationById(id);
        application1.setStatus(ApplicationStatus.SCHEDULED);
        return this.applicationRepository.save(application1);
    }

    @Override
    public List<Application> findAllApplicationsToACertainAdvertisement(Long advertisementId) {
        Advertisement advertisement = this.advertisementService.findAdvertisementById(advertisementId);
        return applicationRepository.findApplicationByAdvertisement(advertisement);
    }

    @Override
    public Application findApplicationById(Long id) {
        return applicationRepository.findById(id).orElseThrow(InvalidApplicationIdException::new);
    }
}
