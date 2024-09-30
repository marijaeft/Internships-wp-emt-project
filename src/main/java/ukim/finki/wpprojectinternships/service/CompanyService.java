package ukim.finki.wpprojectinternships.service;

import org.springframework.web.multipart.MultipartFile;
import ukim.finki.wpprojectinternships.model.Advertisement;
import ukim.finki.wpprojectinternships.model.AdvertisementType;
import ukim.finki.wpprojectinternships.model.Company;

import java.sql.Timestamp;
import java.util.List;

public interface CompanyService {
    //registracija createCompany
    //logiranje so email i pass
    //find by id
    //find all
    Company registerCompany(String name, String email, String phone, String description, String address, MultipartFile image);
    //Company loginCompany(LoginDto loginDto);
    Company updateCompany(Long id, String name, String email, String phone, String description,String address,MultipartFile image);
    Company findCompanyById(Long id);
    List<Company> findAllCompanies();
    Advertisement addAdvertisement(Long companyId,String description,Timestamp dateExpired, AdvertisementType type);

}
