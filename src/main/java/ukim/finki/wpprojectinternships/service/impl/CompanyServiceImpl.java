package ukim.finki.wpprojectinternships.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ukim.finki.wpprojectinternships.model.*;
import ukim.finki.wpprojectinternships.model.exceptions.InvalidCompanyIdException;
import ukim.finki.wpprojectinternships.repository.AdvertisementRepository;
import ukim.finki.wpprojectinternships.repository.CompanyRepository;
import ukim.finki.wpprojectinternships.repository.UserRepository;
import ukim.finki.wpprojectinternships.service.CompanyService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CompanyServiceImpl(CompanyRepository companyRepository, PasswordEncoder passwordEncoder,AdvertisementRepository advertisementRepository,UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.advertisementRepository = advertisementRepository;
        this.userRepository=userRepository;
    }

    @Override
    public Company registerCompany(String name, String email, String phone, String description, String address, MultipartFile image) {
        Company company = new Company();
        company.setName(name);
        company.setEmail(email);
        company.setPhone(phone);
        company.setDescription(description);
        company.setAddress(address);
        company.setRole(UserRole.COMPANY);
        if (image != null) {
            try {
                byte[] imageData = image.getBytes();
                company.setImage(imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        User user = new User();
        user.setId(name);
        user.setEmail(email);
        user.setName(name);
        user.setRole(UserRole.COMPANY);
        userRepository.save(user);
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(Long id,String name, String email, String phone, String description, String address, MultipartFile image) {
        Company company = this.findCompanyById(id);
        company.setName(name);
        company.setEmail(email);
        company.setPhone(phone);
        company.setDescription(description);
        company.setAddress(address);
        company.setRole(UserRole.COMPANY);
        if(image!=null) {
            try {
                byte[] imageData = image.getBytes();
                company.setImage(imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return companyRepository.save(company);
    }

    @Override
    public Company findCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(InvalidCompanyIdException::new);
    }

    @Override
    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }



    @Override
    public Advertisement addAdvertisement(Long companyId,String description, Timestamp dateExpired, AdvertisementType type) {
        {
            Optional<Company> company = companyRepository.findById(companyId);

            Advertisement advertisement = new Advertisement();
            advertisement.setDescription(description);
            advertisement.setDateExpired(dateExpired);
            advertisement.setType(type);
            advertisement.setCompany(company.get());

                advertisement.setActive(true);

            return advertisementRepository.save(advertisement);
        }

    }
}
