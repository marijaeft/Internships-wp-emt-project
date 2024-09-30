package ukim.finki.wpprojectinternships.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ukim.finki.wpprojectinternships.model.Advertisement;
import ukim.finki.wpprojectinternships.model.AdvertisementType;
import ukim.finki.wpprojectinternships.model.Company;
import ukim.finki.wpprojectinternships.model.exceptions.InvalidAdvertisementIdException;
import ukim.finki.wpprojectinternships.model.exceptions.InvalidCompanyIdException;
import ukim.finki.wpprojectinternships.repository.AdvertisementRepository;
import ukim.finki.wpprojectinternships.repository.CompanyRepository;
import ukim.finki.wpprojectinternships.service.AdvertisementService;
import java.sql.Timestamp;

import java.util.List;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final CompanyRepository companyRepository;

    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository, CompanyRepository companyRepository) {
        this.advertisementRepository = advertisementRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Advertisement> findAllAdvertisements() {
        return advertisementRepository.findAllByOrderByActiveDesc();
    }

    @Override
    public Advertisement findAdvertisementById(Long id) {
        return advertisementRepository.findById(id).orElseThrow(InvalidAdvertisementIdException::new);
    }

    @Override
    public Advertisement createAdvertisement(String description,Timestamp dateExpired, String type1, Long company1) {
        Company company = this.companyRepository.findById(company1).orElseThrow(InvalidCompanyIdException::new);
        AdvertisementType type = AdvertisementType.valueOf(type1);
        Advertisement advertisementNew = new Advertisement(dateExpired,description, type, 0, company, true);
        return this.advertisementRepository.save(advertisementNew);
    }

    @Override
    public Advertisement updateAdvertisement(Long id,String description,Timestamp dateExpired, String type1,Boolean active) {
        Advertisement advertisement1=this.findAdvertisementById(id);
        advertisement1.setDescription(description);
        advertisement1.setDateExpired(dateExpired);
        advertisement1.setType(AdvertisementType.valueOf(type1));
        advertisement1.setActive(active);
        return this.advertisementRepository.save(advertisement1);
    }

    @Override
    public void deleteAdvertisement(Long id) {
        Advertisement advertisement=this.findAdvertisementById(id);
        this.advertisementRepository.delete(advertisement);
    }

    @Override
    public void saveAdvertisement(Advertisement advertisement) {

        advertisementRepository.save(advertisement);
    }
    @Override
    public List<Advertisement> findAllAdvertisementsByCompanyId(Long companyId)
    {
        return this.advertisementRepository.findAllByCompanyId(companyId);
    }
}
