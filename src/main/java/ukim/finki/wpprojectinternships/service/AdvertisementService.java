package ukim.finki.wpprojectinternships.service;

import org.springframework.web.multipart.MultipartFile;
import ukim.finki.wpprojectinternships.model.Advertisement;

import java.sql.Timestamp;

import java.util.List;

public interface AdvertisementService {
    //find all
    //find by company
    //create oglas od kompanijata
    //update oglas od kompanijata
    //delete (hide ili expired da pisuva)
    public void saveAdvertisement(Advertisement advertisement);
    List<Advertisement> findAllAdvertisements();
    Advertisement findAdvertisementById(Long id);
    Advertisement createAdvertisement(String description,Timestamp dateExpired, String type, Long company);
    Advertisement updateAdvertisement(Long id,String description,Timestamp dateExpired, String type,Boolean active);
    void deleteAdvertisement(Long id);
    List<Advertisement> findAllAdvertisementsByCompanyId(Long companyId);

}
